package com.example.organizzebylanga.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.example.organizzebylanga.adapter.AdapterMovimentacao;
import com.example.organizzebylanga.config.FirebaseConfig;
import com.example.organizzebylanga.helper.Base64Custom;
import com.example.organizzebylanga.model.Movimentos;
import com.example.organizzebylanga.model.Utilizador;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import com.example.organizzebylanga.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.OnMonthChangedListener;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class PrincipalActivity extends AppCompatActivity {

    //DECLARAÇÃO DOS CAMPOS DE ENTRADA
    private MaterialCalendarView calendarView;
    private TextView editOla, editSaldo;
    private RecyclerView recyclerMovimentos;

    //CONEXÃO COM O AUTENTICADOR DO FIREBASE
    private FirebaseAuth auth= FirebaseConfig.getFirebaseAuth();

    //CONEXÃO COM O REFERÊNCIAS DO FIREBASE DATABASE
    private DatabaseReference databaseReference = FirebaseConfig.getFirebaseDataBase();
    private DatabaseReference utilizadorRef;
    private DatabaseReference movimentacoesRef;

    //CRIAÇÃO DE OBJETOS PARA EVITAR TRAFEGO DE DADOS QUANDO A APLICAÇÃO ESTÁ EM MODO DE STOP
    private ValueEventListener valueEventListenerUtilizador;
    private ValueEventListener valueEventListenerMovimentacoes;

    //CONEXÃO COM O ADAPTER DO RECYCLERVIEW
    private AdapterMovimentacao adapterMovimentacao;

    //CRIAÇÃO DA LISTA PARA OS MOVIMENTOS
    private List <Movimentos> movimentos = new ArrayList<>();

    //CONEXÃO COM O MODEL
    private Movimentos movimento;

    //VARIÁVEIS
    private String mesAnoSelecionado;
    private Double despesaTotal = 0.00;
    private Double receitaTotal = 0.00;
    private Double saldoTotal = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        //ALTERA O NOME NA TOOLBAR
        toolbar.setTitle("Organizze by Langa");
        setSupportActionBar(toolbar);
        //SYNC DOS CAMPOS DE ENTRADA
        editOla = findViewById(R.id.editOla);
        editSaldo =findViewById(R.id.editSaldo);
        calendarView = findViewById(R.id.calendarView);
        recyclerMovimentos = findViewById(R.id.recyclerMovimentos);

        //CHAMA O MÉTODO PARA CONFIGURAR O CALENDAR
        conifgCalendarView();

        //CHAMA O MÉTODO PARA EM CASO DE SWIPE NO RECYLCERVIEW
        swipe();

        //DEFINE O ADAPTER E AS CONFIGURAÇÕES DE LAYOUT PARA O RECYCLERVIEW
        adapterMovimentacao = new AdapterMovimentacao(movimentos, this);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerMovimentos.setLayoutManager(layoutManager);
        recyclerMovimentos.setHasFixedSize(true);
        recyclerMovimentos.setAdapter(adapterMovimentacao);

    }

    //MÉTODO SWIPE PARA EXCLUSÃO DOS MOVIMENTOS
    public void swipe(){
        ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                //PEGA O SWIPE EM PARA QUALQUER DIREÇÃO (INÍCIO DO ITEM OU FINAL DO ITEM)
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags );
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                //CHAMA O METODOS PARA ECLUIR E PASSA O VIEWHOLDER QUE FOI SELECIONADO
                excluirMovimento(viewHolder);
            }
        };

        //DEFINE EM QUAL RECYLCERVIEW O ITEMTOUCH VAI SER UTILIZADO
        new ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerMovimentos);
    }

    //MÉTODO PARA EXCLUIR O ITEM SELECIONADO
    public void excluirMovimento(RecyclerView.ViewHolder viewHolder){

        //CRIA UM ALERTDIALOG PARA CONFIRMAR OU NÃO
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        //DEFINE O TEXTO QUE SERÁ EXIBIDO
        alertDialog.setTitle("Excluir Movimento da Conta");
        alertDialog.setMessage("Tem certeza que deseja excluir o movimento da sua conta?");
        alertDialog.setCancelable(false);

        //DEFINE O QUE SERÁ FEITO EM CASO DE CONFIRMAR
        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

                //PEGA A POSIÇÃO DO ITEM QUE FOI FEITO SWIPE
                int position = viewHolder.getAdapterPosition();

                //BUSCA NA LISTA O ITEM COM A POSIÇÃO ANTERIOR
                movimento = movimentos.get(position);

                //FAZ A CONEXÃO COM A BASE DE DADOS VERIFICANDO O ID DO UTILIZADOR
                String emailUtilizador = auth.getCurrentUser().getEmail();
                String idUtiliziador = Base64Custom.codifica(emailUtilizador);
                movimentacoesRef = databaseReference.child("movimentos")
                        .child(idUtiliziador)
                        .child(mesAnoSelecionado);

                //REMOVE DA BASE DE DADOS O ITEM COM ID SELECIONADO
                movimentacoesRef.child(movimento.getId()).removeValue();

                //NOTIFICA O ADAPATER QUE UM ITEM FOI REMOVIDO PARA QUE POSSA ATUALIZAR E OCUPAR O ESPAÇO VAZIO
                adapterMovimentacao.notifyItemRemoved(position);

                //CHAMA O METODO PARA ATUALIZAR O SALDO
                atualizaSaldo();


            }
        });

        //DEFINE O QUE SERÁ FEITO EM CASO DE NÃO CONFIRMAÇÃO
        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(PrincipalActivity.this, "O movimento não foi excluído.", Toast.LENGTH_SHORT).show();

                //NOTIFICA O ADAPTER PARA VOLTAR A APRESENTAR O ITEM QUE FOI FEITO SWIPE
                adapterMovimentacao.notifyDataSetChanged();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }


    //MÉTODO PARA ATUALIZAÇÃO DO SALDO
    public void atualizaSaldo(){

        //BUSCA AS REFERENCIAS DA BASE DE DADOS E DO UTILIZADOR
        String emailUtilizador = auth.getCurrentUser().getEmail();
        String idUtiliziador = Base64Custom.codifica(emailUtilizador);
        utilizadorRef = databaseReference.child("users").child(idUtiliziador);

        //VERIFICA SE O ITEM REMOVIDO FOI RECEITA E ATUALIZADO O SALDO
        if(movimento.getTipo().equals("r")){
            receitaTotal = receitaTotal - movimento.getValor();
            utilizadorRef.child("receitaTotal").setValue(receitaTotal);
        }

        //VERIFICA SE O ITEM REMOVIDO FOI DESPESA E ATUALIZADO O SALDO
        if(movimento.getTipo().equals("d")){
            despesaTotal = despesaTotal - movimento.getValor();
            utilizadorRef.child("despesaTotal").setValue(despesaTotal);
        }
    }


    //MÉTODO PARA BUSCAR TODOS OS MOVIMENTOS
    public void getMovimentacoes(){
        //BUSCA AS REFERENCIAS DA BASE DE DADOS E DO UTILIZADOR
        String emailUtilizador = auth.getCurrentUser().getEmail();
        String idUtiliziador = Base64Custom.codifica(emailUtilizador);
        movimentacoesRef = databaseReference.child("movimentos")
                                            .child(idUtiliziador)
                                            .child(mesAnoSelecionado);

        //ATUALIZA OS VALORES EM CASO DE MODIFICAÇÃO
        valueEventListenerMovimentacoes = movimentacoesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //LIMPA A LISTA
                movimentos.clear();

                //ADICIONA OS DADOS DOS MOVIMENTOS DENTRO DA LISTA
                for(DataSnapshot dados: snapshot.getChildren()){
                    Movimentos movimento = dados.getValue(Movimentos.class);
                    movimento.setId(dados.getKey());
                    movimentos.add(movimento);
                }

                //NOTIFICA O ADAPATER DE QUE HOUVE MUDANÇA DOS DADOS
                adapterMovimentacao.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

    //MÉTODO PARA BUSCAR OS DADOS DAS DESPESAS, RECEITAS TOTAIS E NOME
    //APRESENTA ESSES DADOS NOS CAMPOS NA VIEW
    public void getDados(){
        String emailUtilizador = auth.getCurrentUser().getEmail();
        String idUtiliziador = Base64Custom.codifica(emailUtilizador);
        utilizadorRef = databaseReference.child("users").child(idUtiliziador);

        valueEventListenerUtilizador = utilizadorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Utilizador utilizador = snapshot.getValue(Utilizador.class);
                despesaTotal = utilizador.getDespesaTotal();
                receitaTotal = utilizador.getReceitaTotal();
                saldoTotal = receitaTotal-despesaTotal;

                DecimalFormat decimalFormat = new DecimalFormat("0.00");
                String saldoFormatado = decimalFormat.format(saldoTotal);

                editOla.setText("Olá, " + utilizador.getNome());
                editSaldo.setText(saldoFormatado + "€");
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_principal, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menuSair:
                auth.signOut();
                startActivity(new Intent(this, MainActivity.class));
                finish();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void conifgCalendarView(){
        CharSequence meses[] = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
        calendarView.setTitleMonths(meses);

        CalendarDay dataAtual = calendarView.getCurrentDate();
        String mesSelecionado = String.format("%02d", dataAtual.getMonth());
        mesAnoSelecionado = String.valueOf(mesSelecionado+""+dataAtual.getYear());
        calendarView.setOnMonthChangedListener(new OnMonthChangedListener() {
            @Override
            public void onMonthChanged(MaterialCalendarView widget, CalendarDay date) {
                String mesSelecionado = String.format("%02d", date.getMonth());
                mesAnoSelecionado = String.valueOf(mesSelecionado+""+date.getYear());
                movimentacoesRef.removeEventListener(valueEventListenerMovimentacoes);
                getMovimentacoes();
            }
        });
    }


    public void adicionarDespesa(View view){
        startActivity(new Intent(this, DespesasActivity.class));
    }

    public void adicionarReceita(View view){
        startActivity(new Intent(this, ReceitasActivity.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        getDados();
        getMovimentacoes();
    }

    @Override
    protected void onStop() {
        utilizadorRef.removeEventListener(valueEventListenerUtilizador);
        movimentacoesRef.removeEventListener(valueEventListenerMovimentacoes);
        super.onStop();
    }
}
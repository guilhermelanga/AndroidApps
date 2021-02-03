package com.example.organizzebylanga.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;

import com.example.organizzebylanga.adapter.AdapterMovimentacao;
import com.example.organizzebylanga.config.FirebaseConfig;
import com.example.organizzebylanga.helper.Base64Custom;
import com.example.organizzebylanga.model.Movimentos;
import com.example.organizzebylanga.model.Utilizador;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Switch;
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

    private MaterialCalendarView calendarView;
    private TextView editOla, editSaldo;
    private FirebaseAuth auth= FirebaseConfig.getFirebaseAuth();
    private DatabaseReference databaseReference = FirebaseConfig.getFirebaseDataBase();
    private DatabaseReference utilizadorRef;
    private ValueEventListener valueEventListenerUtilizador;
    private ValueEventListener valueEventListenerMovimentacoes;
    private RecyclerView recyclerMovimentos;
    private AdapterMovimentacao adapterMovimentacao;
    private List <Movimentos> movimentos = new ArrayList<>();
    private Movimentos movimento;
    private DatabaseReference movimentacoesRef;
    private String mesAnoSelecionado;
    private Double despesaTotal = 0.00;
    private Double receitaTotal = 0.00;
    private Double saldoTotal = 0.00;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Organizze by Langa");
        setSupportActionBar(toolbar);
        editOla = findViewById(R.id.editOla);
        editSaldo =findViewById(R.id.editSaldo);
        calendarView = findViewById(R.id.calendarView);
        recyclerMovimentos = findViewById(R.id.recyclerMovimentos);

        conifgCalendarView();
        swipe();

        adapterMovimentacao = new AdapterMovimentacao(movimentos, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerMovimentos.setLayoutManager(layoutManager);
        recyclerMovimentos.setHasFixedSize(true);
        recyclerMovimentos.setAdapter(adapterMovimentacao);

    }

    public void swipe(){
        ItemTouchHelper.Callback itemTouch = new ItemTouchHelper.Callback() {
            @Override
            public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
                int dragFlags = ItemTouchHelper.ACTION_STATE_IDLE;
                int swipeFlags = ItemTouchHelper.START | ItemTouchHelper.END;
                return makeMovementFlags(dragFlags, swipeFlags );
            }

            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                excluirMovimento(viewHolder);
            }
        };

        new ItemTouchHelper(itemTouch).attachToRecyclerView(recyclerMovimentos);
    }

    public void excluirMovimento(RecyclerView.ViewHolder viewHolder){
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);

        alertDialog.setTitle("Excluir Movimento da Conta");
        alertDialog.setMessage("Tem certeza que deseja excluir o movimento da sua conta?");
        alertDialog.setCancelable(false);

        alertDialog.setPositiveButton("Confirmar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                int position = viewHolder.getAdapterPosition();
                movimento = movimentos.get(position);

                String emailUtilizador = auth.getCurrentUser().getEmail();
                String idUtiliziador = Base64Custom.codifica(emailUtilizador);
                movimentacoesRef = databaseReference.child("movimentos")
                        .child(idUtiliziador)
                        .child(mesAnoSelecionado);

                movimentacoesRef.child(movimento.getId()).removeValue();

                adapterMovimentacao.notifyItemRemoved(position);

                atualizaSaldo();


            }
        });

        alertDialog.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Toast.makeText(PrincipalActivity.this, "O movimento não foi excluído.", Toast.LENGTH_SHORT).show();
                adapterMovimentacao.notifyDataSetChanged();
            }
        });
        AlertDialog alert = alertDialog.create();
        alert.show();
    }

    public void atualizaSaldo(){
        String emailUtilizador = auth.getCurrentUser().getEmail();
        String idUtiliziador = Base64Custom.codifica(emailUtilizador);
        utilizadorRef = databaseReference.child("users").child(idUtiliziador);

        if(movimento.getTipo().equals("r")){
            receitaTotal = receitaTotal - movimento.getValor();
            utilizadorRef.child("receitaTotal").setValue(receitaTotal);
        }
        if(movimento.getTipo().equals("d")){
            despesaTotal = despesaTotal - movimento.getValor();
            utilizadorRef.child("despesaTotal").setValue(despesaTotal);
        }
    }

    public void getMovimentacoes(){
        String emailUtilizador = auth.getCurrentUser().getEmail();
        String idUtiliziador = Base64Custom.codifica(emailUtilizador);
        movimentacoesRef = databaseReference.child("movimentos")
                                            .child(idUtiliziador)
                                            .child(mesAnoSelecionado);

        valueEventListenerMovimentacoes = movimentacoesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                movimentos.clear();
                for(DataSnapshot dados: snapshot.getChildren()){
                    Movimentos movimento = dados.getValue(Movimentos.class);
                    movimento.setId(dados.getKey());
                    movimentos.add(movimento);
                }
                adapterMovimentacao.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


    }

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
        //movimentacoesRef.removeEventListener(valueEventListenerMovimentacoes);
        super.onStop();
    }
}
package com.example.organizzebylanga.activity;

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
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CalendarView;
import android.widget.Switch;
import android.widget.TextView;

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
        toolbar.setTitle("Organizze");
        setSupportActionBar(toolbar);
        editOla = findViewById(R.id.editOla);
        editSaldo =findViewById(R.id.editSaldo);
        calendarView = findViewById(R.id.calendarView);
        recyclerMovimentos = findViewById(R.id.recyclerMovimentos);

        conifgCalendarView();

        adapterMovimentacao = new AdapterMovimentacao(movimentos, this);

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerMovimentos.setLayoutManager(layoutManager);
        recyclerMovimentos.setHasFixedSize(true);
        recyclerMovimentos.setAdapter(adapterMovimentacao);



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
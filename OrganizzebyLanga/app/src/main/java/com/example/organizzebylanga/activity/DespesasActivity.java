package com.example.organizzebylanga.activity;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.example.organizzebylanga.R;
import com.example.organizzebylanga.config.FirebaseConfig;
import com.example.organizzebylanga.helper.Base64Custom;
import com.example.organizzebylanga.helper.DataAtual;
import com.example.organizzebylanga.model.Movimentos;
import com.example.organizzebylanga.model.Utilizador;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

public class DespesasActivity extends AppCompatActivity {

    //DECLARAÇÃO DOS CAMPOS DE ENTRADA
    private TextInputEditText editData, editCategoria, editDescricao;
    private EditText editValor;

    //ACESSO AO MODEL MOVIMENTOS
    private Movimentos movimentos;

    //GET FIREBASE UTILIZADOR E REFERÊNCIA DO DATABASE
    private DatabaseReference databaseReference = FirebaseConfig.getFirebaseDataBase();
    private FirebaseAuth auth = FirebaseConfig.getFirebaseAuth();

    //CONTROLE DAS DESPESAS
    private Double despesaTotal, despesaAtualizada;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_despesas);

        //SYNC DOS CAMPOS DE ENTRADA
        editData=findViewById(R.id.editData);
        editCategoria=findViewById(R.id.editCategoria);
        editDescricao=findViewById(R.id.editDescricao);
        editValor=findViewById(R.id.editValor);

        //MÉTODO PARA BUSCAR O TOTAL DE DESPESAS NO FIREBASE
        getDespesaTotal();

        //INICIAR SEMPRE COM A DATA ATUAL NO CAMPO DATA
        editData.setText(DataAtual.dataAtual());



    }

    //MÉTODO PARA GUARDAR A DESPESA QUANDO FOR CLICADO NO BOTÃO ADICIONAR
    public void guardarDespesa(View view){
        if (validarCampos()){
            String data = editData.getText().toString();
            Double valorDespesa = Double.parseDouble(editValor.getText().toString());
            movimentos = new Movimentos();
            movimentos.setValor(valorDespesa);
            movimentos.setCategoria(editCategoria.getText().toString());
            movimentos.setDescricao(editDescricao.getText().toString());
            movimentos.setData(data);
            movimentos.setTipo("d");

            despesaAtualizada = despesaTotal + valorDespesa;

            //ATUALIZAR O VALOR DAS DESPESAS NO FIREBASE
            atualizarDespesa(despesaAtualizada);

            //CHAMA O MODEL MOVIMENTOS, MÉTODO GUARDAR, PASSANDO DATA QUE FOI INTRODUZIDA NO CAMPO DATA
            //E GUARDA O MOVIMENTO NO MESANO SELECIONADOS
            movimentos.guardar(data);

            //ENCERRA A ACTIVITY E VOLTA A PRINCIPAL
            finish();
        }


    }

    //MÉTODO ARA VERIFICAR SE TODOS OS CAMPOS FORAM PREENCHIDOS
    public Boolean validarCampos(){

        String valor = editValor.getText().toString();
        String data = editData.getText().toString();
        String categoria = editCategoria.getText().toString();
        String descricao = editDescricao.getText().toString();


        if(!valor.isEmpty()){
            if(!data.isEmpty()){
                if(!categoria.isEmpty()){
                    if(!descricao.isEmpty()){
                        return true;
                    }else {
                        Toast.makeText(this, "Descrição não foi preenchida", Toast.LENGTH_SHORT).show();
                        return false;
                    }

                }else {
                    Toast.makeText(this, "Categoria não foi preenchida", Toast.LENGTH_SHORT).show();
                    return false;
                }

            }else {
                Toast.makeText(this, "Data não foi preenchida", Toast.LENGTH_SHORT).show();
                return false;
            }

        }else {
            Toast.makeText(this, "Valor não foi preenchido", Toast.LENGTH_SHORT).show();
            return false;
        }

    }

    //MÉTODO PARA BUSCAR O TOTAL DE DESPESAS NO FIREBASE
    public void getDespesaTotal(){
        String emailUtilizador = auth.getCurrentUser().getEmail();
        String idUtiliziador = Base64Custom.codifica(emailUtilizador);
        DatabaseReference utilizadorRef = databaseReference.child("users").child(idUtiliziador);

        utilizadorRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                Utilizador utilizador = snapshot.getValue(Utilizador.class);
                despesaTotal = utilizador.getDespesaTotal();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    //MÉTODO PARA ATUALIZAR O VALOR DAS DESPESAS NO FIREBASE
    public void atualizarDespesa(Double despesa){
        String emailUtilizador = auth.getCurrentUser().getEmail();
        String idUtiliziador = Base64Custom.codifica(emailUtilizador);
        DatabaseReference utilizadorRef = databaseReference.child("users").child(idUtiliziador);

        utilizadorRef.child("despesaTotal").setValue(despesa);

    }
}
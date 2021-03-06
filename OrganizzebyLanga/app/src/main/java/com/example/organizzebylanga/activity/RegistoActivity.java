package com.example.organizzebylanga.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.organizzebylanga.R;
import com.example.organizzebylanga.config.FirebaseConfig;
import com.example.organizzebylanga.helper.Base64Custom;
import com.example.organizzebylanga.model.Utilizador;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

public class RegistoActivity extends AppCompatActivity {

    //DECLARAÇÃO DOS CAMPOS DE ENTRADA
    private EditText editNome, editPalavraPasse, editEmail;
    private Button criarCOnta;

    //CONTROLE DOS DADOS
    private boolean dadosVerificados;

    //CONEXÃO COM O AUTENTICADOR DO FIREBASE
    private FirebaseAuth auth;

    //CONEXÃO COM O MODEL UTILIZADOR
    private Utilizador utilizador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registo);
        //SYNC DOS CAMPOS DE ENTRADA
        editNome = findViewById(R.id.editNome);
        editEmail = findViewById(R.id.editEmail);
        editPalavraPasse = findViewById(R.id.editPalavraPasse);
        criarCOnta = findViewById(R.id.btnCriarConta);

        //ALTERA NOME QUE APARECE NA TOOLBAR
        getSupportActionBar().setTitle("Registo");

        //DEFINE O QUE ACONTECE AO CLICAR NO BOTÃO CRIARCONTA
        criarCOnta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //PEGAR DADOS NOS CAMPOS DE TEXTO
                String getNome = editNome.getText().toString();
                String getEmail = editEmail.getText().toString();
                String getPalavraPasse = editPalavraPasse.getText().toString();

                //VERIFICA OS DADOS INSERIDOS
                verificarDados(getNome, getEmail, getPalavraPasse);

                if(dadosVerificados){
                    //ENVIA OS DADOS AO MODEL
                    utilizador = new Utilizador();
                    utilizador.setNome(getNome);
                    utilizador.setEmail(getEmail);
                    utilizador.setPalavraPasse(getPalavraPasse);

                    //CHAMA O MÉTODO PARA CRIAR O UTILIZADOR
                    criarUtilizador();
                }

            }
        });
    }

    //  VERIFICAR SE TODOS OS DADOS FORAM PREENCHIDOS
    public boolean verificarDados(String nome, String email, String palavraPasse){

        if (!nome.isEmpty()) {
            if (!email.isEmpty()) {
                if (!palavraPasse.isEmpty()) {
                    dadosVerificados = true;
                } else {
                    Toast.makeText(RegistoActivity.this, "Preencha a palavra-passe.", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(RegistoActivity.this, "Preencha o e-mail.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(RegistoActivity.this, "Preencha o nome.", Toast.LENGTH_SHORT).show();
        }
        return dadosVerificados;
    }

    //MÉTODO PARA CRIAR UTILIZADOR
    public void criarUtilizador(){

        auth = FirebaseConfig.getFirebaseAuth();

        auth.createUserWithEmailAndPassword(
                utilizador.getEmail(),
                utilizador.getPalavraPasse()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    //CODIFICA O EMAIL DO UTILIZADOR PARA SER A CHAVE DO USER
                    String idUtilizador = Base64Custom.codifica(utilizador.getEmail());
                    utilizador.setIdUtilizador(idUtilizador);
                    utilizador.guardar();
                    finish();

                }else{

                    String excecao = "";
                    //LISTA DAS EXCEÇÕES QUE PODEM OCORRER EM CASO DE FALHA NO CRIAR A CONTA
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthWeakPasswordException e){
                        excecao = "Palavra-passe deve ser mais complexa";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Por favor insira um e-mail válido";
                    }catch (FirebaseAuthUserCollisionException e){
                        excecao = "A conta já existe";
                    }catch (Exception e){
                        excecao = "Erro ao criar utilizador" + e.getMessage();
                        e.printStackTrace();
                    }

                    Toast.makeText(RegistoActivity.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
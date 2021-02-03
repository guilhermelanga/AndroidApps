package com.example.organizzebylanga.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.organizzebylanga.R;
import com.example.organizzebylanga.config.FirebaseConfig;
import com.example.organizzebylanga.model.Utilizador;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthInvalidUserException;

public class LoginActivity extends AppCompatActivity {

    //DECLARAÇÃO DOS CAMPOS DE ENTRADA
    private TextView editEmail, editPalavraPasse;
    private Button btnEntrar;

    //CONEXÃO COM O AUTENTICADOR DO FIREBASE
    private FirebaseAuth auth;

    //CONEXÃO COM O MODEL UTILIZADOR
    private Utilizador utilizador;

    //CONTROLE DOS DADOS
    private boolean dadosVerificados;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //SYNC DOS CAMPOS DE ENTRADA
        editEmail = findViewById(R.id.editEmail);
        editPalavraPasse = findViewById(R.id.editPalavraPasse);
        btnEntrar = findViewById(R.id.btnEntrar);

        //ALTERA NOME QUE APARECE NA TOOLBAR
        getSupportActionBar().setTitle("Entrar");

        //DEFINE O QUE ACONTECE AO CLICAR NO BOTÃO ENTRAR
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String getEmail =  editEmail.getText().toString();
                String getPalavraPasse = editPalavraPasse.getText().toString();

                //VERIFICA OS DADOS INSERIDOS
                verificarDados(getEmail, getPalavraPasse);


                if(dadosVerificados){

                    //ENVIA OS DADOS AO MODEL
                    utilizador = new Utilizador();
                    utilizador.setEmail(getEmail);
                    utilizador.setPalavraPasse(getPalavraPasse);

                    //CHAMA O MÉTODO PARA FAZER O LOGIN
                    validarLogin();
                }
            }
        });
    }

    //MÉTODO PARA VERIFICAR SE TODOS OS CAMPOS FORAM PREENCHIDOS
    public boolean verificarDados(String email, String palavraPasse){

        if (!email.isEmpty()) {
            if (!palavraPasse.isEmpty()) {
                dadosVerificados = true;
            } else {
                Toast.makeText(LoginActivity.this, "Preencha a palavra-passe.", Toast.LENGTH_SHORT).show();
            }
        } else {
            Toast.makeText(LoginActivity.this, "Preencha o e-mail.", Toast.LENGTH_SHORT).show();
        }
        return dadosVerificados;
    }

    //MÉTODO PARA VALIDAR E FETUAR O LOGIN
    public void validarLogin(){

        auth = FirebaseConfig.getFirebaseAuth();

        auth.signInWithEmailAndPassword(
                utilizador.getEmail(),
                utilizador.getPalavraPasse()
        ).addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    abrirPrincipalActivity();
                }else{

                    String excecao = "";

                    //LISTA DAS EXCEÇÕES QUE PODEM OCORRER EM CASO DE FALHA NO LOGIN
                    try {
                        throw task.getException();
                    }catch (FirebaseAuthInvalidUserException e){
                        excecao = "O e-mail não possui conta";
                    }catch (FirebaseAuthInvalidCredentialsException e){
                        excecao = "Palavra-passe incorreta";
                    }catch (Exception e){
                        excecao = "Erro ao criar utilizador" + e.getMessage();
                        e.printStackTrace();
                    }
                    Toast.makeText(LoginActivity.this, excecao, Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    //MÉTODO PARA ABRIR A ACTIVITY EM CASO DE SUCESSO NO LOGIN
    public void abrirPrincipalActivity(){
        startActivity(new Intent(this, PrincipalActivity.class));
        finish();
    }
}
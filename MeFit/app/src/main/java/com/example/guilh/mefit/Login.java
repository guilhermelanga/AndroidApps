package com.example.guilh.mefit;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private Button newAccount, login;
    private TextView emailLogin, passwordLogin;

    //PARA LIGAR AO FIREBASE
    private FirebaseAuth auth;

    //BARRA DE PROGRESSÃO PARA AUTENTICAÇÃO
    private ProgressBar progressBar;


    @Override
    protected void onStart() {
        super.onStart();

        //VERIFICAR SE O UTILIZAR ESTÁ LOGADO
        FirebaseUser currentUser = auth.getCurrentUser();

        if(currentUser != null){
            Intent intent = new Intent(Login.this, homepage.class);
            startActivity(intent);
        }else {
            Toast.makeText(this, getResources().getString(R.string.signin), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //CRIAR A BARRA
        progressBar = new ProgressBar(this);
        progressBar.setMin(0);
        progressBar.setMax(100);

        //LINK COM O LAYOUT
        newAccount = findViewById(R.id.newAccount);
        login = findViewById(R.id.login);
        emailLogin = findViewById(R.id.emailLogin);
        passwordLogin = findViewById(R.id.passwordLogin);

        //LIGAÇÃO A BASE DE DADOS
        auth = FirebaseAuth.getInstance();



        //CRIAR UMA CONTA NOVA
        newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,register.class));

                passwordLogin.setText(null);
                emailLogin.setText(null);
            }
        });

        /*newAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, homepage.class));
            }
        });*/

        //FAZER O LOGIN
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //OS CAMPOS DE USERNAME E PASSWORD DEVEM CONTER VALORES
                if(emailLogin.getText().toString().isEmpty()){
                    Toast.makeText(Login.this, getResources().getString(R.string.insertusername), Toast.LENGTH_SHORT).show();
                }
                if(passwordLogin.getText().toString().isEmpty()){
                    Toast.makeText(Login.this, getResources().getString(R.string.insertpassword), Toast.LENGTH_SHORT).show();
                }

                String password = passwordLogin.getText().toString();
                String username = emailLogin.getText().toString();

                //
                try{

                    auth.signInWithEmailAndPassword(username, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                FirebaseUser currentUser = auth.getCurrentUser();
                                startActivity(new Intent(Login.this, homepage.class));

                            } else {
                                Toast.makeText(Login.this, getResources().getString(R.string.wrongpassword), Toast.LENGTH_SHORT).show();

                            }
                        }
                    });



                }catch (Exception e){
                    e.printStackTrace();
                }




            }

            /*private void validate (String usernameLogin, String passwordLogin){
                if ((usernameLogin.equals("1"))&& (passwordLogin.equals("1"))){
                    Intent intent = new Intent(Login.this, homepage.class);
                    startActivity(intent);
                }else{
                    Toast.makeText(Login.this, "essa merda não funciona", Toast.LENGTH_SHORT).show();
                }
            }*/
        });

    }
}

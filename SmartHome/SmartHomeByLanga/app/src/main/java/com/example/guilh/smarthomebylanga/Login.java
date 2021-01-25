package com.example.guilh.smarthomebylanga;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class Login extends AppCompatActivity {

    private TextView EmailLogin;
    private TextView PasswordLogin;
    private ImageButton ButtonLogin;
    private ImageButton ButtonNewAccount;


    //PARA LIGAR AO FIREBASE
    private FirebaseAuth auth;

    @Override
    protected void onStart() {
        super.onStart();

        //VERIFICAR SE O UTILIZAR ESTÁ LOGADO
        FirebaseUser currentUser = auth.getCurrentUser();

        if(currentUser != null){
            Intent intent = new Intent(Login.this, Homepage.class);
            startActivity(intent);
        }else {
            //Toast.makeText(this, getResources().getString(R.string.SignIn), Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        //LIGAÇÃO COM O LAYUOT
        EmailLogin = findViewById(R.id.EmailLogin);
        PasswordLogin = findViewById(R.id.PasswordLogin);
        ButtonLogin = findViewById(R.id.ButtonLogin);
        ButtonNewAccount = findViewById(R.id.ButtonNewAccount);

        //LIGAÇÃO A BASE DE DADOS
        auth = FirebaseAuth.getInstance();


        //CRIAR UMA CONTA NOVA
        ButtonNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, CreateAccount.class ));

                EmailLogin.setText(null);
                PasswordLogin.setText(null);
            }
        });

        //FAZER LOGIN
        ButtonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PASSAR DADOS PARA STRING
                String email, password;
                email = EmailLogin.getText().toString();
                password = PasswordLogin.getText().toString();


                if (email.isEmpty()){
                    Toast.makeText(Login.this, getResources().getString(R.string.InsertEmail), Toast.LENGTH_SHORT).show();
                }else if(password.isEmpty()){
                    Toast.makeText(Login.this, getResources().getString(R.string.InsertPassword), Toast.LENGTH_SHORT).show();
                }else{
                    try{
                        auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(Login.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()){
                                    FirebaseUser currentUser = auth.getCurrentUser();
                                    startActivity(new Intent(Login.this, Homepage.class));
                                }else{
                                    Toast.makeText(Login.this, getResources().getString(R.string.WrongPassword), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }
        });
    }
}

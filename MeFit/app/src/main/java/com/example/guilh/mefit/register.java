package com.example.guilh.mefit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Calendar;

public class register extends AppCompatActivity {


    private Button createAccount, gotoLogin;
    private TextView registerPassword, registerEmail, registerPassword2;



    //BASE DE DADOS
    private FirebaseAuth auth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);


        createAccount = findViewById(R.id.createAccount);
        gotoLogin = findViewById(R.id.gotoLogin);
        registerPassword = findViewById(R.id.registerPassword);
        registerPassword2 = findViewById(R.id.registerPassword2);
        registerEmail = findViewById(R.id.registerEmail);


        //LIGAR A BASE DE DADOS
        auth = FirebaseAuth.getInstance();




        //CRIAR CONTA
        createAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {



                //CONVERTER EM STRING
                String password1, password2;
                password1 = registerPassword.getText().toString();
                password2 = registerPassword2.getText().toString();
                String email = registerEmail.getText().toString();

                //VERIFICAR SE HA CAMPOS VAZIOS
                if(password1.isEmpty()||password2.isEmpty()||email.isEmpty()){
                    Toast.makeText(register.this, getResources().getString(R.string.insertpersonaldata), Toast.LENGTH_SHORT).show();
                }else

                //VERIFICAR PASSWORD IGUAIS
                if(password1.equals(password2)){

                    auth.createUserWithEmailAndPassword(email, password1).addOnCompleteListener(register.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if(task.isSuccessful()){
                                Toast.makeText(register.this, getResources().getString(R.string.registersuccessful), Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(register.this, firstdataregistration.class));
                            }else{
                                Toast.makeText(register.this, getResources().getString(R.string.errorregistering), Toast.LENGTH_SHORT).show();
                            }

                        }
                    });



                }else{
                    Toast.makeText(register.this, getResources().getString(R.string.passwordnotmatch), Toast.LENGTH_SHORT).show();
                }




            }
        });

        gotoLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(register.this, Login.class));

                registerPassword.setText(null);
                registerPassword2.setText(null);
                registerEmail.setText(null);

            }
        });


    }
}

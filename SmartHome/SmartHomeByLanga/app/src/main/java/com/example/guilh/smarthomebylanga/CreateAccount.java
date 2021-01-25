package com.example.guilh.smarthomebylanga;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class CreateAccount extends AppCompatActivity {

    private TextView Email, Password, ConfirmPassword;
    private ImageButton ButtonBackToLogin, ButtonNewAccount;

    //firebase
    private FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_account);

        Email = findViewById(R.id.Email);
        Password = findViewById(R.id.Password);
        ConfirmPassword = findViewById(R.id.ConfirmPassword);
        ButtonBackToLogin = findViewById(R.id.ButtonBackToLogin);
        ButtonNewAccount = findViewById(R.id.ButtonNewAccount);

        //ligar ao firebase
        auth = FirebaseAuth.getInstance();

        ButtonBackToLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(CreateAccount.this, Login.class));

                Email.setText(null);
                Password.setText(null);
                ConfirmPassword.setText(null);
            }
        });

        ButtonNewAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //converter os dados em string
                String password = Password.getText().toString();
                String confirmPassword = ConfirmPassword.getText().toString();
                final String email = Email.getText().toString();

                //verificar se os compos foram preenchidos, verificar se as palavras-passe são igauis e criar a conta
                if(password.isEmpty()||confirmPassword.isEmpty()||email.isEmpty()){
                    Toast.makeText(CreateAccount.this,getResources().getString(R.string.FillTheFields), Toast.LENGTH_LONG).show();
                }else if (password.equals(confirmPassword)){
                    auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(CreateAccount.this, new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                       if(task.isSuccessful()){
                           Toast.makeText(CreateAccount.this, getResources().getString(R.string.AccoutCreated), Toast.LENGTH_SHORT).show();

                           Intent homepage = new Intent(CreateAccount.this, Homepage.class);
                           homepage.putExtra("Email", email);
                           startActivity(homepage);
                       }else{
                           Toast.makeText(CreateAccount.this, getResources().getString(R.string.AccountNotCreated), Toast.LENGTH_SHORT).show();
                           Email.setText(null);
                           Password.setText(null);
                           ConfirmPassword.setText(null);
                       }
                        }
                    });

                }else{
                    Toast.makeText(CreateAccount.this, getResources().getString(R.string.PasswordDontMatch), Toast.LENGTH_LONG).show();
                }

            }
        });




    }
}

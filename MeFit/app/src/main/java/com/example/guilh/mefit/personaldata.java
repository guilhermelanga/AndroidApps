package com.example.guilh.mefit;

import android.media.MediaPlayer;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class personaldata extends AppCompatActivity {

    private TextView newPassword;
    private TextView confirmPassword;
    private Button savePassword;

    //BASE DE DADOS
    private FirebaseAuth auth;

    //PEGAR A REFERENCIA DA BASE DE DADOS
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personaldata);

        newPassword = findViewById(R.id.newPassword);
        confirmPassword = findViewById(R.id.confirmPassword);
        savePassword = findViewById(R.id.savePassword);



        //LIGAR A BASE DE DADOS
        auth = FirebaseAuth.getInstance();

        //PEGAR O UTILIZADOR LOGADO
        final FirebaseUser currentUser = auth.getCurrentUser();
        //final String user = currentUser.getUid();

        savePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String password1, password2;
                password1 = newPassword.getText().toString();
                password2 = confirmPassword.getText().toString();

                if(password1.isEmpty()||password2.isEmpty()){

                    Toast.makeText(personaldata.this, getResources().getString(R.string.insertpersonaldata), Toast.LENGTH_SHORT).show();

                }else if(password1.equals(password2)){
                    currentUser.updatePassword(password1).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()){
                                Toast.makeText(personaldata.this, getResources().getString(R.string.passwordhaschanged), Toast.LENGTH_SHORT).show();
                            }else {
                                Toast.makeText(personaldata.this, getResources().getString(R.string.passworderror), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }else {
                    Toast.makeText(personaldata.this, getResources().getString(R.string.passwordnotmatch), Toast.LENGTH_SHORT).show();
                }

                newPassword.setText(null);
                confirmPassword.setText(null);


            }
        });



    }
}

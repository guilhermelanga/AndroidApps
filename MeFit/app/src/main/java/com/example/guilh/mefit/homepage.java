package com.example.guilh.mefit;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class homepage extends AppCompatActivity {

    private Button previousCalculation;
    private Button newCalculation;
    private Button changePassword;
    private Button personalData;
    private Button signOut;
    private FirebaseAuth auth;

    protected void onStart() {
        super.onStart();

        //VERIFICAR SE O UTILIZAR ESTÁ LOGADO
        FirebaseUser currentUser = auth.getCurrentUser();

        if(currentUser == null){
            Intent intent = new Intent(homepage.this, Login.class);
            startActivity(intent);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        previousCalculation = findViewById(R.id.previousCalculation);
        newCalculation = findViewById(R.id.newCalculation);
        changePassword = findViewById(R.id.changePassword);
        personalData = findViewById(R.id.personalData);
        signOut = findViewById(R.id.signOut);

        auth = FirebaseAuth.getInstance();

        previousCalculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homepage.this, imchistoric.class));
            }
        });

        newCalculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homepage.this, newcalculation.class));
            }
        });

        changePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homepage.this, personaldata.class));
            }
        });

        personalData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homepage.this, registerpersonaldata.class));
            }
        });

        signOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(homepage.this, Login.class));
            }
        });


    }
}

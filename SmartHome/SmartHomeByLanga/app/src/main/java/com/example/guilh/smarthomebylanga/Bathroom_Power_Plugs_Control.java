package com.example.guilh.smarthomebylanga;

import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Bathroom_Power_Plugs_Control extends AppCompatActivity {

    private ImageButton shaverBtn, hairDryerBtn, onAllPowerPlugsBtn, offAllPowerPlugsBtn;

    private ImageView imageShaver, imageHairDryer;

    private TextView shaverTxt, hairDryerTxt;

    private View barraShaver, barraHairDryer;

    private String shaverStr, hairDryerStr, totalStr;

    private int shaver, hairDryer, total;

    private FirebaseAuth auth;

    //REFERÊNCIA DA BASE DE DADOS
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bathroom__power__plugs__control);

        //BOTÕES
        shaverBtn = findViewById(R.id.shaverBtn);
        hairDryerBtn = findViewById(R.id.hairDryerBtn);
        onAllPowerPlugsBtn = findViewById(R.id.onAllPowerPlugsBtn);
        offAllPowerPlugsBtn = findViewById(R.id.offAllPowerPlugsBtn);

        //IMAGENS
        imageShaver = findViewById(R.id.imageShaver);
        imageHairDryer = findViewById(R.id.imageHairDryer);

        //TEXTOS
        shaverTxt = findViewById(R.id.shaverTxt);
        hairDryerTxt = findViewById(R.id.hairDryerTxt);

        //BARRAS
        barraShaver = findViewById(R.id.barraShaver);
        barraHairDryer = findViewById(R.id.barraHairDryer);

        //LIGAR A BASE DE DADOS
        auth = FirebaseAuth.getInstance();

        //PEGAR O UTILIZADOR LOGADO
        FirebaseUser currenteUser = auth.getCurrentUser();
        final String user = currenteUser.getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try{
                    //BUSCAR A SITUAÇÃO ATUAL 1=ON 0=OFF
                    shaverStr = dataSnapshot.child("Home").child("PowerPlug").child("Shaver").getValue().toString();
                    hairDryerStr = dataSnapshot.child("Home").child("PowerPlug").child("HairDryer").getValue().toString();
                    totalStr = dataSnapshot.child("Home").child("PowerPlug").child("TotalBathroom").getValue().toString();

                    //CONVERTER OS VALORES PARA INT
                    shaver = Integer.parseInt(shaverStr);
                    hairDryer = Integer.parseInt(hairDryerStr);
                    total = Integer.parseInt(totalStr);

                    //ATUALIZAR BARRA DE STATUS, ÍCONES E TEXTO
                    if(shaver==1){
                        shaverOn();
                    }else{
                        shaverOff();
                    }

                    if(hairDryer==1){
                        hairDryerOn();
                    }else{
                        hairDryerOff();
                    }

                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        shaverBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(shaver==0){
                    shaver=1;
                    total++;
                    databaseReference.child("Home").child("PowerPlug").child("Shaver").setValue(1);
                    databaseReference.child("Home").child("PowerPlug").child("TotalBathroom").setValue(total);
                }else{
                    shaver=0;
                    total--;
                    databaseReference.child("Home").child("PowerPlug").child("Shaver").setValue(0);
                    databaseReference.child("Home").child("PowerPlug").child("TotalBathroom").setValue(total);
                }
            }
        });

        hairDryerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(hairDryer==0){
                    hairDryer=1;
                    total++;
                    databaseReference.child("Home").child("PowerPlug").child("HairDryer").setValue(1);
                    databaseReference.child("Home").child("PowerPlug").child("TotalBathroom").setValue(total);
                }else{
                    hairDryer=0;
                    total--;
                    databaseReference.child("Home").child("PowerPlug").child("HairDryer").setValue(0);
                    databaseReference.child("Home").child("PowerPlug").child("TotalBathroom").setValue(total);
                }
            }
        });

        onAllPowerPlugsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Bathroom_Power_Plugs_Control.this)
                        .setTitle(getString(R.string.OnAll))
                        .setMessage(getString(R.string.QuestionPlugs))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                allBathroomOn();
                            }
                        })

                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }
        });

        offAllPowerPlugsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Bathroom_Power_Plugs_Control.this)
                        .setTitle(getString(R.string.OffAll))
                        .setMessage(getString(R.string.QuestionPlugs2))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                allBathroomOff();

                            }
                        })

                        .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        })
                        .setIcon(android.R.drawable.ic_dialog_info)
                        .show();
            }
        });

    }

    public void shaverOn(){
        imageShaver.setImageResource(R.drawable.shaveron);
        barraShaver.setBackgroundResource(R.drawable.barraon);
        shaverTxt.setText(getString(R.string.TurnOff));
    }

    public void shaverOff(){
        imageShaver.setImageResource(R.drawable.shaveroff);
        barraShaver.setBackgroundResource(R.drawable.barraoff);
        shaverTxt.setText(getString(R.string.TurnOn));
    }

    public void hairDryerOn(){
        imageHairDryer.setImageResource(R.drawable.hairdryeron);
        barraHairDryer.setBackgroundResource(R.drawable.barraon);
        shaverTxt.setText(getString(R.string.TurnOff));
    }

    public void hairDryerOff(){
        imageHairDryer.setImageResource(R.drawable.hairdryeroff);
        barraHairDryer.setBackgroundResource(R.drawable.barraoff);
        hairDryerTxt.setText(getString(R.string.TurnOn));
    }

    public void allBathroomOn(){
        databaseReference.child("Home").child("PowerPlug").child("Shaver").setValue(1);
        databaseReference.child("Home").child("PowerPlug").child("HairDryer").setValue(1);
        databaseReference.child("Home").child("PowerPlug").child("TotalBathroom").setValue(2);
    }

    public void allBathroomOff(){
        databaseReference.child("Home").child("PowerPlug").child("Shaver").setValue(0);
        databaseReference.child("Home").child("PowerPlug").child("HairDryer").setValue(0);
        databaseReference.child("Home").child("PowerPlug").child("TotalBathroom").setValue(0);
    }
}

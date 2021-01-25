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

public class LivingRoom_Power_Plugs_Control extends AppCompatActivity {

    private ImageButton modemBtn, televisionBtn, tvBoxBtn, consoleBtn, telephoneBtn, onAllPowerPlugsBtn, offAllPowerPlugsBtn;

    private ImageView imageModem, imageTelevision, imageTvBox, imageConsole, imageTelephone;

    private TextView modemTxt, televisionTxt, tvBoxTxt, consoleTxt, telephoneTxt;

    private View barraModem, barraTelevision, barraTvBox, barraConsole, barraTelephone;

    private String modemStr, televisionStr, tvBoxStr, consoleStr, telephoneStr, totalStr;

    private int modem, television, tvBox, console, telephone, total;

    private FirebaseAuth auth;

    //REFERÊNCIA DA BASE DE DADOS
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_living_room__power__plugs__control);

        //BOTÕES
        modemBtn = findViewById(R.id.modemBtn);
        televisionBtn = findViewById(R.id.televisionBtn);
        tvBoxBtn = findViewById(R.id.tvBoxBtn);
        consoleBtn = findViewById(R.id.consoleBtn);
        telephoneBtn = findViewById(R.id.telephoneBtn);
        onAllPowerPlugsBtn = findViewById(R.id.onAllPowerPlugsBtn);
        offAllPowerPlugsBtn = findViewById(R.id.offAllPowerPlugsBtn);

        //IMAGENS
        imageModem = findViewById(R.id.imageModem);
        imageTelevision = findViewById(R.id.imageTelevision);
        imageTvBox = findViewById(R.id.imageTvBox);
        imageConsole = findViewById(R.id.imageConsole);
        imageTelephone = findViewById(R.id.imageTelephone);

        //TEXTVIEW
        modemTxt = findViewById(R.id.modemTxt);
        televisionTxt = findViewById(R.id.televisionTxt);
        tvBoxTxt = findViewById(R.id.tvBoxTxt);
        consoleTxt = findViewById(R.id.consoleTxt);
        telephoneTxt = findViewById(R.id.telephoneTxt);

        //BARRAS
        barraModem = findViewById(R.id.barraModem);
        barraTelevision = findViewById(R.id.barraTelevision);
        barraTvBox = findViewById(R.id.barraTvBox);
        barraConsole = findViewById(R.id.barraConsole);
        barraTelephone = findViewById(R.id.barraTelephone);

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
                    modemStr = dataSnapshot.child("Home").child("PowerPlug").child("Modem").getValue().toString();
                    televisionStr = dataSnapshot.child("Home").child("PowerPlug").child("Television").getValue().toString();
                    tvBoxStr = dataSnapshot.child("Home").child("PowerPlug").child("TvBox").getValue().toString();
                    consoleStr = dataSnapshot.child("Home").child("PowerPlug").child("Console").getValue().toString();
                    telephoneStr = dataSnapshot.child("Home").child("PowerPlug").child("Telephone").getValue().toString();
                    totalStr = dataSnapshot.child("Home").child("PowerPlug").child("TotalLivingRoom").getValue().toString();

                    //CONVERTER OS VALORES PARA INT
                    modem = Integer.parseInt(modemStr);
                    television = Integer.parseInt(televisionStr);
                    tvBox = Integer.parseInt(tvBoxStr);
                    console = Integer.parseInt(consoleStr);
                    telephone = Integer.parseInt(telephoneStr);
                    total = Integer.parseInt(totalStr);

                    //ATUALIZAR BARRA DE STATUS, ÍCONES E TEXTO
                    if(modem==1){
                        modemOn();
                    }else{
                        modemOff();
                    }
                    if(television==1){
                        televisionOn();
                    }else{
                        televisionOff();
                    }
                    if(tvBox==1){
                        tvBoxOn();
                    }else{
                        tvBoxOff();
                    }
                    if(console==1){
                        consoleOn();
                    }else{
                        consoleOff();
                    }
                    if(telephone==1){
                        telephoneOn();
                    }else{
                        telephoneOff();
                    }


                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        modemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(modem==0){
                    modem=1;
                    total++;
                    databaseReference.child("Home").child("PowerPlug").child("Modem").setValue(1);
                    databaseReference.child("Home").child("PowerPlug").child("TotalLivingRoom").setValue(total);
                }else {
                    modem=0;
                    total--;
                    databaseReference.child("Home").child("PowerPlug").child("Modem").setValue(0);
                    databaseReference.child("Home").child("PowerPlug").child("TotalLivingRoom").setValue(total);
                }
            }
        });

        televisionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(television==0){
                    television=1;
                    total++;
                    databaseReference.child("Home").child("PowerPlug").child("Television").setValue(1);
                    databaseReference.child("Home").child("PowerPlug").child("TotalLivingRoom").setValue(total);
                }else {
                    television=0;
                    total--;
                    databaseReference.child("Home").child("PowerPlug").child("Television").setValue(0);
                    databaseReference.child("Home").child("PowerPlug").child("TotalLivingRoom").setValue(total);
                }
            }
        });

        tvBoxBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(tvBox==0){
                    tvBox=1;
                    total++;
                    databaseReference.child("Home").child("PowerPlug").child("TvBox").setValue(1);
                    databaseReference.child("Home").child("PowerPlug").child("TotalLivingRoom").setValue(total);
                }else {
                    tvBox=0;
                    total--;
                    databaseReference.child("Home").child("PowerPlug").child("TvBox").setValue(0);
                    databaseReference.child("Home").child("PowerPlug").child("TotalLivingRoom").setValue(total);
                }
            }
        });

        consoleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(console==0){
                    tvBox=1;
                    total++;
                    databaseReference.child("Home").child("PowerPlug").child("Console").setValue(1);
                    databaseReference.child("Home").child("PowerPlug").child("TotalLivingRoom").setValue(total);
                }else {
                    console=0;
                    total--;
                    databaseReference.child("Home").child("PowerPlug").child("Console").setValue(0);
                    databaseReference.child("Home").child("PowerPlug").child("TotalLivingRoom").setValue(total);
                }
            }
        });

        telephoneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(telephone==0){
                    telephone=1;
                    total++;
                    databaseReference.child("Home").child("PowerPlug").child("Telephone").setValue(1);
                    databaseReference.child("Home").child("PowerPlug").child("TotalLivingRoom").setValue(total);
                }else {
                    telephone=0;
                    total--;
                    databaseReference.child("Home").child("PowerPlug").child("Telephone").setValue(0);
                    databaseReference.child("Home").child("PowerPlug").child("TotalLivingRoom").setValue(total);
                }
            }
        });

        onAllPowerPlugsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(LivingRoom_Power_Plugs_Control.this)
                        .setTitle(getString(R.string.OnAll))
                        .setMessage(getString(R.string.QuestionPlugs))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                allLivingRoomOn();
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

                new AlertDialog.Builder(LivingRoom_Power_Plugs_Control.this)
                        .setTitle(getString(R.string.OffAll))
                        .setMessage(getString(R.string.QuestionPlugs2))
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                allLivingRoomOff();
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

    public void modemOn(){
        imageModem.setImageResource(R.drawable.modemon);
        barraModem.setBackgroundResource(R.drawable.barraon);
        modemTxt.setText(getString(R.string.TurnOff));
    }

    public void modemOff(){
        imageModem.setImageResource(R.drawable.modem);
        barraModem.setBackgroundResource(R.drawable.barraoff);
        modemTxt.setText(getString(R.string.TurnOn));
    }

    public void televisionOn(){
        imageTelevision.setImageResource(R.drawable.televisionon);
        barraTelevision.setBackgroundResource(R.drawable.barraon);
        televisionTxt.setText(getString(R.string.TurnOff));
    }

    public void televisionOff(){
        imageTelevision.setImageResource(R.drawable.television);
        barraTelevision.setBackgroundResource(R.drawable.barraoff);
        televisionTxt.setText(getString(R.string.TurnOn));
    }

    public void tvBoxOn(){
        imageTvBox.setImageResource(R.drawable.tvboxon);
        barraTvBox.setBackgroundResource(R.drawable.barraon);
        tvBoxTxt.setText(getString(R.string.TurnOff));
    }

    public void tvBoxOff(){
        imageTvBox.setImageResource(R.drawable.tvbox);
        barraTvBox.setBackgroundResource(R.drawable.barraoff);
        tvBoxTxt.setText(getString(R.string.TurnOn));
    }

    public void consoleOn(){
        imageConsole.setImageResource(R.drawable.arcadegameon);
        barraConsole.setBackgroundResource(R.drawable.barraon);
        consoleTxt.setText(getString(R.string.TurnOff));
    }

    public void consoleOff(){
        imageConsole.setImageResource(R.drawable.arcadegame);
        barraConsole.setBackgroundResource(R.drawable.barraoff);
        consoleTxt.setText(getString(R.string.TurnOn));
    }

    public void telephoneOn(){
        imageTelephone.setImageResource(R.drawable.telephoneon);
        barraTelephone.setBackgroundResource(R.drawable.barraon);
        telephoneTxt.setText(getString(R.string.TurnOff));
    }

    public void telephoneOff(){
        imageTelephone.setImageResource(R.drawable.telephone);
        barraTelephone.setBackgroundResource(R.drawable.barraoff);
        telephoneTxt.setText(getString(R.string.TurnOn));
    }

    public void allLivingRoomOn(){
        databaseReference.child("Home").child("PowerPlug").child("Modem").setValue(1);
        databaseReference.child("Home").child("PowerPlug").child("Television").setValue(1);
        databaseReference.child("Home").child("PowerPlug").child("TvBox").setValue(1);
        databaseReference.child("Home").child("PowerPlug").child("Console").setValue(1);
        databaseReference.child("Home").child("PowerPlug").child("Telephone").setValue(1);
        databaseReference.child("Home").child("PowerPlug").child("TotalLivingRoom").setValue(5);
    }

    public void allLivingRoomOff(){
        databaseReference.child("Home").child("PowerPlug").child("Modem").setValue(0);
        databaseReference.child("Home").child("PowerPlug").child("Television").setValue(0);
        databaseReference.child("Home").child("PowerPlug").child("TvBox").setValue(0);
        databaseReference.child("Home").child("PowerPlug").child("Console").setValue(0);
        databaseReference.child("Home").child("PowerPlug").child("Telephone").setValue(0);
        databaseReference.child("Home").child("PowerPlug").child("TotalLivingRoom").setValue(0);
    }
}

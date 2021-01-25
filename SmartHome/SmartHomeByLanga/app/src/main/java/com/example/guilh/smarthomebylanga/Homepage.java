package com.example.guilh.smarthomebylanga;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class Homepage extends AppCompatActivity {

    private TextView humidityTxt, temperatureTxt, lightsOnTxt, plugsOnTxt;

    private ImageButton lightsBtn, powerPlugBtn, logoutBtn;

    private ImageView imageLights, imagePowerPlugs;

    private View barraLights, barraPowerPlugs;

    private String totalLivinRoomStr,totalBathroomStr, totalKitchenStr, totalLightsStr;

    private int totalLights, totalKitchen, totalLivinRoom, totalBathroom, totalPlugs;

    String temperature, humidity, dataBaseName;

    private FirebaseAuth auth;

    //REFERÊNCIA DA BASE DE DADOS
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        humidityTxt = findViewById(R.id.humidityTxt);
        temperatureTxt = findViewById(R.id.temperatureTxt);
        lightsOnTxt = findViewById(R.id.lightsOnTxt);
        lightsBtn = findViewById(R.id.lightsBtn);
        powerPlugBtn = findViewById(R.id.powerPlugBtn);
        barraLights = findViewById(R.id.barraLights);
        imageLights = findViewById(R.id.imageLights);
        plugsOnTxt = findViewById(R.id.plugsOnTxt);
        imagePowerPlugs = findViewById(R.id.imagePowerPlugs);
        barraPowerPlugs = findViewById(R.id.barraPowerPlugs);
        logoutBtn = findViewById(R.id.logoutBtn);

        //LIGAR A BASE DE DADOS
        auth = FirebaseAuth.getInstance();

        //PEGAR O UTILIZADOR LOGADO
        FirebaseUser currenteUser = auth.getCurrentUser();
        final String user = currenteUser.getUid();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {


                    //BUSCAR A TEMPERATURA
                    temperature = dataSnapshot.child("Home").child("Sensor").child("Temperature").getValue().toString();
                    temperatureTxt.setText(temperature);

                    //BUSCAR A HUMIDADE
                    humidity = dataSnapshot.child("Home").child("Sensor").child("Humidity").getValue().toString();
                    humidityTxt.setText(humidity);

                    //BUSCAR LUZES E TOMADAS LIGADAS
                    totalLightsStr = dataSnapshot.child("Home").child("Light").child("Total").getValue().toString();
                    totalKitchenStr = dataSnapshot.child("Home").child("PowerPlug").child("TotalKitchen").getValue().toString();
                    totalLivinRoomStr = dataSnapshot.child("Home").child("PowerPlug").child("TotalLivingRoom").getValue().toString();
                    totalBathroomStr = dataSnapshot.child("Home").child("PowerPlug").child("TotalBathroom").getValue().toString();

                    //CONVERTER OS VALORES PARA INT
                    totalLights = Integer.parseInt(totalLightsStr);
                    totalKitchen = Integer.parseInt(totalKitchenStr);
                    totalLivinRoom = Integer.parseInt(totalLivinRoomStr);
                    totalBathroom = Integer.parseInt(totalBathroomStr);

                    //MOSTRAR NÚMERO TOTAL DE LUZES LIGADAS
                    lightsOnTxt.setText(totalLightsStr);
                    totalPlugs = totalKitchen+totalLivinRoom+totalBathroom;
                    String totalPlugsStr = String.valueOf(totalPlugs);
                    plugsOnTxt.setText(totalPlugsStr);

                    //ATUALIZAR BARRA DE STATUS, ÍCONES E TEXTO
                    if (totalLights==0){
                        lightsOff();
                    }else{
                        lightsOn();
                    }

                    if (totalPlugs==0){
                        plugsOff();
                    }else{
                        plugsOn();
                    }



                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        lightsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homepage.this, Lights_Control.class));
            }
        });

        powerPlugBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Homepage.this, Power_Plugs_Control.class));
            }
        });

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
                startActivity(new Intent(Homepage.this, Login.class));
            }
        });

        lightsBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(totalLights==0){
                    new AlertDialog.Builder(Homepage.this)
                            .setTitle(getString(R.string.OnAll))
                            .setMessage(getString(R.string.QuestionLights))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    allLightsOn();
                                }
                            })

                            .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            })
                            .setIcon(android.R.drawable.ic_dialog_info)
                            .show();

                }else{
                    new AlertDialog.Builder(Homepage.this)
                            .setTitle(getString(R.string.OffAll))
                            .setMessage(getString(R.string.QuestionLights2))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    allLightsOff();
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
                return false;
            }
        });
    }

    public void lightsOn(){
        imageLights.setImageResource(R.drawable.lamp);
        barraLights.setBackgroundResource(R.drawable.barraon);
    }

    public void lightsOff(){
        imageLights.setImageResource(R.drawable.lampoff);
        barraLights.setBackgroundResource(R.drawable.barraoff);
    }

    public void plugsOn(){
        imagePowerPlugs.setImageResource(R.drawable.plug);
        barraPowerPlugs.setBackgroundResource(R.drawable.barraon);
    }

    public void plugsOff(){
        imagePowerPlugs.setImageResource(R.drawable.plugoff);
        barraPowerPlugs.setBackgroundResource(R.drawable.barraoff);
    }

    public void allLightsOn(){
        databaseReference.child("Home").child("Light").child("Kitchen").setValue(1);
        databaseReference.child("Home").child("Light").child("LivingRoom").setValue(1);
        databaseReference.child("Home").child("Light").child("Bathroom").setValue(1);
        databaseReference.child("Home").child("Light").child("CoupleBed").setValue(1);
        databaseReference.child("Home").child("Light").child("SingleBed").setValue(1);
        databaseReference.child("Home").child("Light").child("Laundry").setValue(1);
        databaseReference.child("Home").child("Light").child("Garage").setValue(1);
        databaseReference.child("Home").child("Light").child("Total").setValue(7);
    }

    public void allLightsOff(){
        databaseReference.child("Home").child("Light").child("Kitchen").setValue(0);
        databaseReference.child("Home").child("Light").child("LivingRoom").setValue(0);
        databaseReference.child("Home").child("Light").child("Bathroom").setValue(0);
        databaseReference.child("Home").child("Light").child("CoupleBed").setValue(0);
        databaseReference.child("Home").child("Light").child("SingleBed").setValue(0);
        databaseReference.child("Home").child("Light").child("Laundry").setValue(0);
        databaseReference.child("Home").child("Light").child("Garage").setValue(0);
        databaseReference.child("Home").child("Light").child("Total").setValue(0);
    }
}

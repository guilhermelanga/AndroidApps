package com.example.guilh.smarthomebylanga;


import android.content.DialogInterface;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
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

public class Power_Plugs_Control extends AppCompatActivity {

    private ImageButton kitchenBtn,livingRoomBtn, bathroomBtn, coupleBedBtn, singleBedBtn, laundryBtn, garageBtn, onAllPowerPlugsBtn, offAllPowerPlugsBtn;

    private TextView plugsOnKitchenTxt, plugsOnLivingRoomTxt, plugsOnBathroomTxt;

    private ImageView imageKitchen, imageLivingRoom, imageBathroom;

    private View barraKitchen, barraLivingRoom, barraBathroom;

    private String totalKitchenStr, totalLivingRoomStr, totalBathroomStr;

    private int totalKitchen, totalLivingRoom, totalBathroom;

    private FirebaseAuth auth;

    //REFERÊNCIA DA BASE DE DADOS
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_power__plugs__control);

        //BOTÕES
        kitchenBtn = findViewById(R.id.kitchenBtn);
        livingRoomBtn = findViewById(R.id.livingRoomBtn);
        bathroomBtn = findViewById(R.id.bathroomBtn);
        coupleBedBtn = findViewById(R.id.coupleBedBtn);
        singleBedBtn = findViewById(R.id.singleBedBtn);
        laundryBtn = findViewById(R.id.laundryBtn);
        garageBtn = findViewById(R.id.garageBtn);
        onAllPowerPlugsBtn = findViewById(R.id.onAllPowerPlugsBtn);
        offAllPowerPlugsBtn = findViewById(R.id.offAllPowerPlugsBtn);

        //TEXTVIEW
        plugsOnKitchenTxt = findViewById(R.id.plugsOnKitchenTxt);
        plugsOnLivingRoomTxt = findViewById(R.id.plugsOnLivingRoomTxt);
        plugsOnBathroomTxt = findViewById(R.id.plugsOnBathroomTxt);

        //IMAGEVIEW
        imageKitchen = findViewById(R.id.imageKitchen);
        imageLivingRoom = findViewById(R.id.imageLivingRoom);
        imageBathroom = findViewById(R.id.imageBathroom);

        //VIEW
        barraKitchen = findViewById(R.id.barraKitchen);
        barraLivingRoom = findViewById(R.id.barraLivingRoom);
        barraBathroom = findViewById(R.id.barraBathroom);

        //LIGAR A BASE DE DADOS
        auth = FirebaseAuth.getInstance();

        //PEGAR O UTILIZADOR LOGADO
        FirebaseUser currenteUser = auth.getCurrentUser();
        final String user = currenteUser.getUid();


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                totalKitchenStr = dataSnapshot.child("Home").child("PowerPlug").child("TotalKitchen").getValue().toString();
                totalLivingRoomStr = dataSnapshot.child("Home").child("PowerPlug").child("TotalLivingRoom").getValue().toString();
                totalBathroomStr = dataSnapshot.child("Home").child("PowerPlug").child("TotalBathroom").getValue().toString();

                //MOSTRAR TOTAL LIGADO EM CADA COMODO
                plugsOnKitchenTxt.setText(totalKitchenStr);
                plugsOnLivingRoomTxt.setText(totalLivingRoomStr);
                plugsOnBathroomTxt.setText(totalBathroomStr);

                //CONVERTER OS VALORES PARA INT
                totalKitchen = Integer.parseInt(totalKitchenStr);
                totalLivingRoom = Integer.parseInt(totalLivingRoomStr);
                totalBathroom = Integer.parseInt(totalBathroomStr);

                //ALTERAR BARRA E IMAGENS
                if(totalKitchen==0){
                    kitchenOff();
                }else{
                    kitchenOn();
                }
                if(totalLivingRoom==0){
                    livingRoomOff();
                }else{
                    livingRoomOn();
                }

                if(totalBathroom==0){
                    bathroomOff();
                }else{
                    bathroomOn();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        kitchenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Power_Plugs_Control.this, Kitchen_Power_Plugs_Control.class));
            }
        });

        livingRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Power_Plugs_Control.this, LivingRoom_Power_Plugs_Control.class));
            }
        });

        bathroomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Power_Plugs_Control.this, Bathroom_Power_Plugs_Control.class));

            }
        });

        coupleBedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        singleBedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        laundryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        garageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        kitchenBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if(totalKitchen==0){
                    new AlertDialog.Builder(Power_Plugs_Control.this)
                            .setTitle(getString(R.string.OnAll))
                            .setMessage(getString(R.string.QuestionPlugs))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    allKitchenOn();
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
                    new AlertDialog.Builder(Power_Plugs_Control.this)
                            .setTitle(getString(R.string.OffAll))
                            .setMessage(getString(R.string.QuestionPlugs2))
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    allKitchenOff();
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

        livingRoomBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {

                if(totalLivingRoom==0){
                    new AlertDialog.Builder(Power_Plugs_Control.this)
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

                }else{
                    new AlertDialog.Builder(Power_Plugs_Control.this)
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
                return false;
            }
        });

        bathroomBtn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if(totalBathroom==0){
                    new AlertDialog.Builder(Power_Plugs_Control.this)
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

                }else{
                    new AlertDialog.Builder(Power_Plugs_Control.this)
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
                return false;
            }
        });

    }

    public void kitchenOn(){
        imageKitchen.setImageResource(R.drawable.kitchenon);
        barraKitchen.setBackgroundResource(R.drawable.barraon);
    }

    public void kitchenOff(){
        imageKitchen.setImageResource(R.drawable.kitchenoff);
        barraKitchen.setBackgroundResource(R.drawable.barraoff);
    }

    public void livingRoomOn(){
        imageLivingRoom.setImageResource(R.drawable.livingroomon);
        barraLivingRoom.setBackgroundResource(R.drawable.barraon);
    }

    public void livingRoomOff(){
        imageLivingRoom.setImageResource(R.drawable.livingroomoff);
        barraLivingRoom.setBackgroundResource(R.drawable.barraoff);
    }

    public void bathroomOn(){
        imageBathroom.setImageResource(R.drawable.bathroomon);
        barraBathroom.setBackgroundResource(R.drawable.barraon);
    }

    public void bathroomOff(){
        imageBathroom.setImageResource(R.drawable.bathroomoff);
        barraBathroom.setBackgroundResource(R.drawable.barraoff);
    }

    public void allKitchenOn(){
        databaseReference.child("Home").child("PowerPlug").child("Fridge").setValue(1);
        databaseReference.child("Home").child("PowerPlug").child("CoffeeMachine").setValue(1);
        databaseReference.child("Home").child("PowerPlug").child("Stove").setValue(1);
        databaseReference.child("Home").child("PowerPlug").child("Microwave").setValue(1);
        databaseReference.child("Home").child("PowerPlug").child("WaterHeater").setValue(1);
        databaseReference.child("Home").child("PowerPlug").child("KitchenHood").setValue(1);
        databaseReference.child("Home").child("PowerPlug").child("TotalKitchen").setValue(6);
    }

    public void allKitchenOff(){
        databaseReference.child("Home").child("PowerPlug").child("Fridge").setValue(0);
        databaseReference.child("Home").child("PowerPlug").child("CoffeeMachine").setValue(0);
        databaseReference.child("Home").child("PowerPlug").child("Stove").setValue(0);
        databaseReference.child("Home").child("PowerPlug").child("Microwave").setValue(0);
        databaseReference.child("Home").child("PowerPlug").child("WaterHeater").setValue(0);
        databaseReference.child("Home").child("PowerPlug").child("KitchenHood").setValue(0);
        databaseReference.child("Home").child("PowerPlug").child("TotalKitchen").setValue(0);
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


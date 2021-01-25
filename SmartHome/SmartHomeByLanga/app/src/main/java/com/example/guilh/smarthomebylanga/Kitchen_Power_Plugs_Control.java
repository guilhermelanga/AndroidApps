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

public class Kitchen_Power_Plugs_Control extends AppCompatActivity {

    private ImageButton fridgeBtn, coffeeMachineBtn, stoveBtn, microwaveBtn, waterHeaterBtn, kitchenHoodBtn, onAllPowerPlugsBtn, offAllPowerPlugsBtn;

    private ImageView imageFridge, imageCoffeeMachine, imageStove, imageMicrowave, imageWaterHeater, imageKitchenHood;

    private TextView fridgeTxt, coffeeMachineTxt, stoveTxt, microwaveTxt, waterHeaterTxt, kitchenHoodTxt;

    private View barraFridge, barraCoffeeMachine, barraStove, barraMicrowave, barraWaterHeater, barraKitchenHood;

    private String fridgeStr, coffeeMachineStr, stoveStr, microwaveStr, waterHeaterStr, kitchenHoodStr, totalPowerPlugStr;

    private int fridge, coffeeMachine, stove, microwave, waterHeater, kitchenHood, totalPowerPlug;

    private FirebaseAuth auth;

    //REFERÊNCIA DA BASE DE DADOS
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kitchen__power__plugs__control);

        //BOTÕES
        fridgeBtn = findViewById(R.id.fridgeBtn);
        coffeeMachineBtn = findViewById(R.id.coffeeMachineBtn);
        stoveBtn = findViewById(R.id.stoveBtn);
        microwaveBtn = findViewById(R.id.microwaveBtn);
        waterHeaterBtn = findViewById(R.id.waterHeaterBtn);
        kitchenHoodBtn = findViewById(R.id.kitchenHoodBtn);
        onAllPowerPlugsBtn = findViewById(R.id.onAllPowerPlugsBtn);
        offAllPowerPlugsBtn = findViewById(R.id.offAllPowerPlugsBtn);

        //IMAGENS
        imageFridge = findViewById(R.id.imageFridge);
        imageCoffeeMachine = findViewById(R.id.imageCoffeeMachine);
        imageStove = findViewById(R.id.imageStove);
        imageMicrowave = findViewById(R.id.imageMicrowave);
        imageWaterHeater = findViewById(R.id.imageWaterHeater);
        imageKitchenHood = findViewById(R.id.imageKitchenHood);

        //VIEW
        barraFridge = findViewById(R.id.barraFridge);
        barraCoffeeMachine = findViewById(R.id.barraCoffeeMachine);
        barraStove = findViewById(R.id.barraStove);
        barraMicrowave = findViewById(R.id.barraMicrowave);
        barraWaterHeater = findViewById(R.id.barraWaterHeater);
        barraKitchenHood = findViewById(R.id.barraKitchenHood);

        //TEXTVIEW
        fridgeTxt = findViewById(R.id.fridgeTxt);
        coffeeMachineTxt = findViewById(R.id.coffeeMachineTxt);
        stoveTxt = findViewById(R.id.stoveTxt);
        microwaveTxt = findViewById(R.id.microwaveTxt);
        waterHeaterTxt = findViewById(R.id.waterHeaterTxt);
        kitchenHoodTxt = findViewById(R.id.kitchenHoodTxt);

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
                    fridgeStr = dataSnapshot.child("Home").child("PowerPlug").child("Fridge").getValue().toString();
                    coffeeMachineStr = dataSnapshot.child("Home").child("PowerPlug").child("CoffeeMachine").getValue().toString();
                    stoveStr = dataSnapshot.child("Home").child("PowerPlug").child("Stove").getValue().toString();
                    microwaveStr = dataSnapshot.child("Home").child("PowerPlug").child("Microwave").getValue().toString();
                    waterHeaterStr = dataSnapshot.child("Home").child("PowerPlug").child("WaterHeater").getValue().toString();
                    kitchenHoodStr = dataSnapshot.child("Home").child("PowerPlug").child("KitchenHood").getValue().toString();
                    totalPowerPlugStr = dataSnapshot.child("Home").child("PowerPlug").child("TotalKitchen").getValue().toString();

                    //CONVERTER OS VALORES PARA INT
                    fridge = Integer.parseInt(fridgeStr);
                    coffeeMachine = Integer.parseInt(coffeeMachineStr);
                    stove = Integer.parseInt(stoveStr);
                    microwave = Integer.parseInt(microwaveStr);
                    waterHeater = Integer.parseInt(waterHeaterStr);
                    kitchenHood = Integer.parseInt(kitchenHoodStr);
                    totalPowerPlug = Integer.parseInt(totalPowerPlugStr);

                    //ATUALIZAR BARRA DE STATUS, ÍCONES E TEXTO
                    if(fridge==1){
                        fridgeOn();
                    }else{
                        fridgeOff();
                    }

                    if(coffeeMachine==1){
                        coffeeMachineOn();
                    }else{
                        coffeeMachineOff();
                    }

                    if(stove==1){
                        stoveOn();
                    }else{
                        stoveOff();
                    }

                    if(microwave==1){
                        microwaveOn();
                    }else{
                        microwaveOff();
                    }

                    if(waterHeater==1){
                        waterHeaterOn();
                    }else{
                        waterHeaterOff();
                    }

                    if(kitchenHood==1){
                        kitchenHoodOn();
                    }else{
                        kitchenHoodOff();
                    }

                }catch (Exception e){

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        fridgeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(fridge==0){
                    fridge=1;
                    totalPowerPlug++;
                    databaseReference.child("Home").child("PowerPlug").child("Fridge").setValue(1);
                    databaseReference.child("Home").child("PowerPlug").child("TotalKitchen").setValue(totalPowerPlug);
                }else {
                    fridge=0;
                    totalPowerPlug--;
                    databaseReference.child("Home").child("PowerPlug").child("Fridge").setValue(0);
                    databaseReference.child("Home").child("PowerPlug").child("TotalKitchen").setValue(totalPowerPlug);
                }
            }
        });

        coffeeMachineBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(coffeeMachine==0){
                    coffeeMachine=1;
                    totalPowerPlug++;
                    databaseReference.child("Home").child("PowerPlug").child("CoffeeMachine").setValue(1);
                    databaseReference.child("Home").child("PowerPlug").child("TotalKitchen").setValue(totalPowerPlug);
                }else {
                    coffeeMachine=0;
                    totalPowerPlug--;
                    databaseReference.child("Home").child("PowerPlug").child("CoffeeMachine").setValue(0);
                    databaseReference.child("Home").child("PowerPlug").child("TotalKitchen").setValue(totalPowerPlug);
                }
            }
        });

        stoveBtn .setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(stove==0){
                    stove=1;
                    totalPowerPlug++;
                    databaseReference.child("Home").child("PowerPlug").child("Stove").setValue(1);
                    databaseReference.child("Home").child("PowerPlug").child("TotalKitchen").setValue(totalPowerPlug);
                }else {
                    stove=0;
                    totalPowerPlug--;
                    databaseReference.child("Home").child("PowerPlug").child("Stove").setValue(0);
                    databaseReference.child("Home").child("PowerPlug").child("TotalKitchen").setValue(totalPowerPlug);
                }
            }
        });

        microwaveBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(microwave==0){
                    microwave=1;
                    totalPowerPlug++;
                    databaseReference.child("Home").child("PowerPlug").child("Microwave").setValue(1);
                    databaseReference.child("Home").child("PowerPlug").child("TotalKitchen").setValue(totalPowerPlug);
                }else {
                    microwave=0;
                    totalPowerPlug--;
                    databaseReference.child("Home").child("PowerPlug").child("Microwave").setValue(0);
                    databaseReference.child("Home").child("PowerPlug").child("TotalKitchen").setValue(totalPowerPlug);
                }
            }
        });

        waterHeaterBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(waterHeater==0){
                    waterHeater=1;
                    totalPowerPlug++;
                    databaseReference.child("Home").child("PowerPlug").child("WaterHeater").setValue(1);
                    databaseReference.child("Home").child("PowerPlug").child("TotalKitchen").setValue(totalPowerPlug);
                }else {
                    microwave=0;
                    totalPowerPlug--;
                    databaseReference.child("Home").child("PowerPlug").child("WaterHeater").setValue(0);
                    databaseReference.child("Home").child("PowerPlug").child("TotalKitchen").setValue(totalPowerPlug);
                }
            }
        });

        kitchenHoodBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(kitchenHood==0){
                    kitchenHood=1;
                    totalPowerPlug++;
                    databaseReference.child("Home").child("PowerPlug").child("KitchenHood").setValue(1);
                    databaseReference.child("Home").child("PowerPlug").child("TotalKitchen").setValue(totalPowerPlug);
                }else {
                    kitchenHood=0;
                    totalPowerPlug--;
                    databaseReference.child("Home").child("PowerPlug").child("KitchenHood").setValue(0);
                    databaseReference.child("Home").child("PowerPlug").child("TotalKitchen").setValue(totalPowerPlug);
                }
            }
        });

        onAllPowerPlugsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Kitchen_Power_Plugs_Control.this)
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

            }
        });

        offAllPowerPlugsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(Kitchen_Power_Plugs_Control.this)
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
        });
    }

    public void fridgeOn(){
        imageFridge.setImageResource(R.drawable.fridgeon);
        barraFridge.setBackgroundResource(R.drawable.barraon);
        fridgeTxt.setText(getString(R.string.TurnOff));
    }

    public void fridgeOff(){
        imageFridge.setImageResource(R.drawable.fridge);
        barraFridge.setBackgroundResource(R.drawable.barraoff);
        fridgeTxt.setText(getString(R.string.TurnOn));
    }

    public void coffeeMachineOn(){
        imageCoffeeMachine.setImageResource(R.drawable.coffeemachineon);
        barraCoffeeMachine.setBackgroundResource(R.drawable.barraon);
        coffeeMachineTxt.setText(getString(R.string.TurnOff));
    }

    public void coffeeMachineOff(){
        imageCoffeeMachine.setImageResource(R.drawable.coffeemachine);
        barraCoffeeMachine.setBackgroundResource(R.drawable.barraoff);
        coffeeMachineTxt.setText(getString(R.string.TurnOn));
    }

    public void stoveOn(){
        imageStove.setImageResource(R.drawable.cookeron);
        barraStove.setBackgroundResource(R.drawable.barraon);
        stoveTxt.setText(getString(R.string.TurnOff));
    }

    public void stoveOff(){
        imageStove.setImageResource(R.drawable.cooker);
        barraStove.setBackgroundResource(R.drawable.barraoff);
        stoveTxt.setText(getString(R.string.TurnOn));
    }

    public void microwaveOn(){
        imageMicrowave.setImageResource(R.drawable.microwaveon);
        barraMicrowave.setBackgroundResource(R.drawable.barraon);
        microwaveTxt.setText(getString(R.string.TurnOff));
    }

    public void microwaveOff(){
        imageMicrowave.setImageResource(R.drawable.microwave);
        barraMicrowave.setBackgroundResource(R.drawable.barraoff);
        microwaveTxt.setText(getString(R.string.TurnOn));
    }

    public void waterHeaterOn(){
        imageWaterHeater.setImageResource(R.drawable.waterheateron);
        barraWaterHeater.setBackgroundResource(R.drawable.barraon);
        waterHeaterTxt.setText(getString(R.string.TurnOff));
    }

    public void waterHeaterOff(){
        imageWaterHeater.setImageResource(R.drawable.waterheater);
        barraWaterHeater.setBackgroundResource(R.drawable.barraoff);
        waterHeaterTxt.setText(getString(R.string.TurnOn));
    }

    public void kitchenHoodOn(){
        imageKitchenHood.setImageResource(R.drawable.kitchenhoodon);
        barraKitchenHood.setBackgroundResource(R.drawable.barraon);
        kitchenHoodTxt.setText(getString(R.string.TurnOff));
    }

    public void kitchenHoodOff(){
        imageKitchenHood.setImageResource(R.drawable.kitchenhood);
        barraKitchenHood.setBackgroundResource(R.drawable.barraoff);
        kitchenHoodTxt.setText(getString(R.string.TurnOn));
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
}



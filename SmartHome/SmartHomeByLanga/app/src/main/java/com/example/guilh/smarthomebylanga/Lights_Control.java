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

public class Lights_Control extends AppCompatActivity {

    private ImageButton kitchenBtn,livingRoomBtn, bathroomBtn, coupleBedBtn, singleBedBtn, laundryBtn, garageBtn, onAllLightsBtn, offAllLightsBtn;

    private ImageView imageKitchen, imageLivingRoom, imageBathroom, imageCoupleBed, imageSingleBed, imageLaundry, imageGarage;

    private View barraKitchen, barraLivingRoom, barraBathroom, barraCoupleBed, barraSingleBed, barraLaundry, barraGarage;

    private TextView kitchenTxt, livingRoomTxt, bathroomTxt, coupleBedTxt, singleBedTxt, laundryTxt, garageTxt;

    private String kitchenStr, livingRoomStr, bathroomStr, coupleBedSrt, singleBedSrt, laundryStr, garageStr, totalLightsStr;

    private int kitchen, livingRoom, bathroom, coupleBed, singleBed, laundry, garage, totalLights;

    private FirebaseAuth auth;

    //REFERÊNCIA DA BASE DE DADOS
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lights__control);

        //BOTÕES
        kitchenBtn = findViewById(R.id.fridgeBtn);
        livingRoomBtn = findViewById(R.id.coffeeMachineBtn);
        bathroomBtn = findViewById(R.id.stoveBtn);
        coupleBedBtn = findViewById(R.id.microwaveBtn);
        singleBedBtn = findViewById(R.id.waterHeaterBtn);
        laundryBtn = findViewById(R.id.kitchenHoodBtn);
        garageBtn = findViewById(R.id.garageBtn);
        onAllLightsBtn = findViewById(R.id.onAllPowerPlugsBtn);
        offAllLightsBtn = findViewById(R.id.offAllPowerPlugsBtn);

        //IMAGENS
        imageKitchen = findViewById(R.id.imageKitchen);
        imageLivingRoom = findViewById(R.id.imageCoffeeMachine);
        imageBathroom = findViewById(R.id.imageStove);
        imageCoupleBed = findViewById(R.id.imageMicrowave);
        imageSingleBed = findViewById(R.id.imageWaterHeater);
        imageLaundry = findViewById(R.id.imageKitchenHood);
        imageGarage = findViewById(R.id.imageGarage);

        //VIEW
        barraKitchen = findViewById(R.id.barraKitchen);
        barraLivingRoom = findViewById(R.id.barraLivingRoom);
        barraBathroom = findViewById(R.id.barraBathroom);
        barraCoupleBed = findViewById(R.id.barraCoupleBed);
        barraSingleBed = findViewById(R.id.barraSingleBed);
        barraLaundry = findViewById(R.id.barraLaundry);
        barraGarage = findViewById(R.id.barraGarage);

        //TEXTVIEW
        kitchenTxt = findViewById(R.id.fridgeTxt);
        livingRoomTxt = findViewById(R.id.coffeeMachineTxt);
        bathroomTxt = findViewById(R.id.stoveTxt);
        coupleBedTxt = findViewById(R.id.microwaveTxt);
        singleBedTxt = findViewById(R.id.waterHeaterTxt);
        laundryTxt = findViewById(R.id.kitchenHoodTxt);
        garageTxt = findViewById(R.id.garageTxt);


        //LIGAR A BASE DE DADOS
        auth = FirebaseAuth.getInstance();

        //PEGAR O UTILIZADOR LOGADO
        FirebaseUser currenteUser = auth.getCurrentUser();
        final String user = currenteUser.getUid();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                try {
                    //BUSCAR A SITUAÇÃO ATUAL 1=ON 0=OFF
                    kitchenStr = dataSnapshot.child("Home").child("Light").child("Kitchen").getValue().toString();
                    livingRoomStr = dataSnapshot.child("Home").child("Light").child("LivingRoom").getValue().toString();
                    bathroomStr = dataSnapshot.child("Home").child("Light").child("Bathroom").getValue().toString();
                    coupleBedSrt = dataSnapshot.child("Home").child("Light").child("CoupleBed").getValue().toString();
                    singleBedSrt = dataSnapshot.child("Home").child("Light").child("SingleBed").getValue().toString();
                    laundryStr = dataSnapshot.child("Home").child("Light").child("Laundry").getValue().toString();
                    garageStr = dataSnapshot.child("Home").child("Light").child("Garage").getValue().toString();
                    totalLightsStr = dataSnapshot.child("Home").child("Light").child("Total").getValue().toString();

                    //CONVERTER OS VALORES PARA INT
                    kitchen = Integer.parseInt(kitchenStr);
                    livingRoom = Integer.parseInt(livingRoomStr);
                    bathroom = Integer.parseInt(bathroomStr);
                    coupleBed = Integer.parseInt(coupleBedSrt);
                    singleBed = Integer.parseInt(singleBedSrt);
                    laundry = Integer.parseInt(laundryStr);
                    garage = Integer.parseInt(garageStr);
                    totalLights = Integer.parseInt(totalLightsStr);

                    //ATUALIZAR BARRA DE STATUS, ÍCONES E TEXTO
                    if(kitchen==1){
                        kitchenOn();
                    }else{
                        kitchenOff();
                    }

                    if(livingRoom==1){
                        livingRoomOn();
                    }else{
                        livingRoomOff();
                    }

                    if(bathroom==1){
                        bathroomOn();
                    }else{
                        bathroonOff();
                    }

                    if(singleBed==1){
                        singleBedOn();
                    }else{
                        singleBedOff();
                    }

                    if(coupleBed==1){
                        coupleBedOn();
                    }else{
                        coupleBedOff();
                    }

                    if(laundry==1){
                        laundryOn();
                    }else{
                        laundryOff();
                    }

                    if(garage==1){
                        garageOn();
                    }else{
                        garageOff();
                    }

                }catch (Exception e){

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        //BOTÕES
        kitchenBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(kitchen==0){
                    kitchen=1;
                    totalLights++;
                    databaseReference.child("Home").child("Light").child("Kitchen").setValue(1);
                    databaseReference.child("Home").child("Light").child("Total").setValue(totalLights);
                }else {
                    kitchen=0;
                    totalLights--;
                    databaseReference.child("Home").child("Light").child("Kitchen").setValue(0);
                    databaseReference.child("Home").child("Light").child("Total").setValue(totalLights);
                }
            }
        });

        livingRoomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(livingRoom==0){
                    livingRoom=1;
                    totalLights++;
                    databaseReference.child("Home").child("Light").child("LivingRoom").setValue(1);
                    databaseReference.child("Home").child("Light").child("Total").setValue(totalLights);
                }else {
                    livingRoom=0;
                    totalLights--;
                    databaseReference.child("Home").child("Light").child("LivingRoom").setValue(0);
                    databaseReference.child("Home").child("Light").child("Total").setValue(totalLights);
                }
            }
        });

        bathroomBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(bathroom==0){
                    bathroom=1;
                    totalLights++;
                    databaseReference.child("Home").child("Light").child("Bathroom").setValue(1);
                    databaseReference.child("Home").child("Light").child("Total").setValue(totalLights);
                }else {
                    bathroom=0;
                    totalLights--;
                    databaseReference.child("Home").child("Light").child("Bathroom").setValue(0);
                    databaseReference.child("Home").child("Light").child("Total").setValue(totalLights);
                }
            }
        });

        coupleBedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(coupleBed==0){
                    coupleBed=1;
                    totalLights++;
                    databaseReference.child("Home").child("Light").child("CoupleBed").setValue(1);
                    databaseReference.child("Home").child("Light").child("Total").setValue(totalLights);
                }else {
                    coupleBed=0;
                    totalLights--;
                    databaseReference.child("Home").child("Light").child("CoupleBed").setValue(0);
                    databaseReference.child("Home").child("Light").child("Total").setValue(totalLights);
                }
            }
        });

        singleBedBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(singleBed==0){
                    singleBed=1;
                    totalLights++;
                    databaseReference.child("Home").child("Light").child("SingleBed").setValue(1);
                    databaseReference.child("Home").child("Light").child("Total").setValue(totalLights);
                }else {
                    singleBed=0;
                    totalLights--;
                    databaseReference.child("Home").child("Light").child("SingleBed").setValue(0);
                    databaseReference.child("Home").child("Light").child("Total").setValue(totalLights);
                }
            }
        });

        laundryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(laundry==0){
                    laundry=1;
                    totalLights++;
                    databaseReference.child("Home").child("Light").child("Laundry").setValue(1);
                    databaseReference.child("Home").child("Light").child("Total").setValue(totalLights);
                }else {
                    laundry=0;
                    totalLights--;
                    databaseReference.child("Home").child("Light").child("Laundry").setValue(0);
                    databaseReference.child("Home").child("Light").child("Total").setValue(totalLights);
                }
            }
        });

        garageBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(garage==0){
                    garage=1;
                    totalLights++;
                    databaseReference.child("Home").child("Light").child("Garage").setValue(1);
                    databaseReference.child("Home").child("Light").child("Total").setValue(totalLights);
                }else {
                    garage=0;
                    totalLights--;
                    databaseReference.child("Home").child("Light").child("Garage").setValue(0);
                    databaseReference.child("Home").child("Light").child("Total").setValue(totalLights);
                }
            }
        });

        onAllLightsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                new AlertDialog.Builder(Lights_Control.this)
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



            }
        });

        offAllLightsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new AlertDialog.Builder(Lights_Control.this)
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
        });


    }

    public void kitchenOn(){
        imageKitchen.setImageResource(R.drawable.kitchenon);
        barraKitchen.setBackgroundResource(R.drawable.barraon);
        kitchenTxt.setText(getString(R.string.TurnOff));
    }

    public void kitchenOff(){
        imageKitchen.setImageResource(R.drawable.kitchenoff);
        barraKitchen.setBackgroundResource(R.drawable.barraoff);
        kitchenTxt.setText(getString(R.string.TurnOn));
    }

    public void livingRoomOn(){
        imageLivingRoom.setImageResource(R.drawable.livingroomon);
        barraLivingRoom.setBackgroundResource(R.drawable.barraon);
        livingRoomTxt.setText(getString(R.string.TurnOff));
    }

    public void livingRoomOff(){
        imageLivingRoom.setImageResource(R.drawable.livingroomoff);
        barraLivingRoom.setBackgroundResource(R.drawable.barraoff);
        livingRoomTxt.setText(getString(R.string.TurnOn));
    }

    public void bathroomOn(){
        imageBathroom.setImageResource(R.drawable.bathroomon);
        barraBathroom.setBackgroundResource(R.drawable.barraon);
        bathroomTxt.setText(getString(R.string.TurnOff));
    }

    public void bathroonOff(){
        imageBathroom.setImageResource(R.drawable.bathroomoff);
        barraBathroom.setBackgroundResource(R.drawable.barraoff);
        bathroomTxt.setText(getString(R.string.TurnOn));
    }

    public void coupleBedOn(){
        imageCoupleBed.setImageResource(R.drawable.bedroomon);
        barraCoupleBed.setBackgroundResource(R.drawable.barraon);
        coupleBedTxt.setText(getString(R.string.TurnOff));
    }

    public void coupleBedOff(){
        imageCoupleBed.setImageResource(R.drawable.bedroomoff);
        barraCoupleBed.setBackgroundResource(R.drawable.barraoff);
        coupleBedTxt.setText(getString(R.string.TurnOn));
    }

    public void singleBedOn(){
        imageSingleBed.setImageResource(R.drawable.bedon);
        barraSingleBed.setBackgroundResource(R.drawable.barraon);
        singleBedTxt.setText(getString(R.string.TurnOff));
    }

    public void singleBedOff(){
        imageSingleBed.setImageResource(R.drawable.bedoff);
        barraSingleBed.setBackgroundResource(R.drawable.barraoff);
        singleBedTxt.setText(getString(R.string.TurnOn));
    }

    public void laundryOn(){
        imageLaundry.setImageResource(R.drawable.laundryon);
        barraLaundry.setBackgroundResource(R.drawable.barraon);
        laundryTxt.setText(getString(R.string.TurnOff));
    }

    public void laundryOff(){
        imageLaundry.setImageResource(R.drawable.laundryoff);
        barraLaundry.setBackgroundResource(R.drawable.barraoff);
        laundryTxt.setText(getString(R.string.TurnOn));
    }

    public void garageOn(){
        imageGarage.setImageResource(R.drawable.garageon);
        barraGarage.setBackgroundResource(R.drawable.barraon);
        garageTxt.setText(getString(R.string.TurnOff));
    }

    public void garageOff(){
        imageGarage.setImageResource(R.drawable.garageoff);
        barraGarage.setBackgroundResource(R.drawable.barraoff);
        garageTxt.setText(getString(R.string.TurnOn));
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
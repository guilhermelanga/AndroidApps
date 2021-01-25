package com.example.guilh.mefit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class newcalculation extends AppCompatActivity {

    private TextView date, weight;
    private Calendar currentDate;
    private int day, month, year, ageint, genderint;
    private float heightfloat, imc, pesomin, pesomax, weightfloat, ig, mg, at, mineral, proteina;
    private Button calculate, save, imcHistoric;
    String data;




    //BASE DE DADOS
    private FirebaseAuth auth;

    //PEGAR A REFERENCIA DA BASE DE DADOS
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newcalculation);

        date = findViewById(R.id.date);
        weight = findViewById(R.id.weight);
        calculate = findViewById(R.id.calculate);
        save = findViewById(R.id.save);
        imcHistoric = findViewById(R.id.imcHistoric);
        currentDate = Calendar.getInstance();


        day = currentDate.get(Calendar.DAY_OF_MONTH);
        month = currentDate.get(Calendar.MONTH);
        year = currentDate.get(Calendar.YEAR);

        date.setText(day+"/"+(month+1)+"/"+year);

        data=day+"/"+(month+1)+"/"+year;



        //LIGAR A BASE DE DADOS
        auth = FirebaseAuth.getInstance();

        //PEGAR O UTILIZADOR LOGADO
        FirebaseUser currentUser = auth.getCurrentUser();
        final String user = currentUser.getUid();

        databaseReference.child("users").child(user).child("height").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String height = dataSnapshot.getValue(String.class);
                heightfloat = Float.parseFloat(height);
                //Toast.makeText(newcalculation.this, heightfloat+"altura", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("users").child(user).child("age").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String age = dataSnapshot.getValue(String.class);
                ageint = Integer.parseInt(age);

               //Toast.makeText(newcalculation.this, ageint+"idade", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {



            }
        });

        databaseReference.child("users").child(user).child("gender").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String gender = dataSnapshot.getValue(String.class);

                genderint = Integer.parseInt(gender);

                //Toast.makeText(newcalculation.this, genderint+"sexo", Toast.LENGTH_SHORT).show();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                DatePickerDialog datePickerDialog = new DatePickerDialog(newcalculation.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int selectedYear, int selectedMonth, int selectedDay) {

                        year = selectedYear;
                        month = selectedMonth;
                        day = selectedDay;

                        date.setText(selectedDay + "/" + (selectedMonth+1) + "/" + selectedYear);

                        data=selectedDay + "/" + (selectedMonth+1) + "/" + selectedYear;

                    }
                }, year, month, day);
                datePickerDialog.show();


            }
        });

        calculate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(weight.getText().toString().isEmpty()||date.getText().toString().isEmpty()){
                    Toast.makeText(newcalculation.this, getResources().getString(R.string.insertpersonaldata), Toast.LENGTH_SHORT).show();


                }else{
                    String newweight = weight.getText().toString();

                    weightfloat = Float.parseFloat(newweight);

                    //calculo imc
                    imc = weightfloat / (heightfloat * heightfloat);
                    //Toast.makeText(newcalculation.this, imc+"imc", Toast.LENGTH_SHORT).show();


                    //CALCULO DO PESO MINIMO
                    pesomin = heightfloat*heightfloat*20;
                    //Toast.makeText(newcalculation.this, pesomin+"min", Toast.LENGTH_SHORT).show();

                    //CALCULO DO PESO MAXIMO
                    pesomax = heightfloat*heightfloat*25;
                    //Toast.makeText(newcalculation.this, pesomax+"max", Toast.LENGTH_SHORT).show();

                    //INDICE DE GORDURA
                    //String newage =
                    //agefloat = Float.parseFloat(String.valueOf(ageint));
                    //genderfloat = Float.parseFloat(String.valueOf(genderint));

                    if(ageint<18){
                        ig = (float) (1.51*imc-0.7*ageint-3.6*genderint+1.4);
                        //Toast.makeText(newcalculation.this, ig+"gordura", Toast.LENGTH_SHORT).show();

                    }else{
                        ig = (float) (1.2*imc+0.23*ageint-10.8*genderint-5.4);
                        //Toast.makeText(newcalculation.this, ig+"gordura", Toast.LENGTH_SHORT).show();

                    }

                    //MASSA GORDA
                    mg = (weightfloat*ig)/100;
                    //Toast.makeText(newcalculation.this, mg+"massagorda", Toast.LENGTH_SHORT).show();

                    //AGUA TOTAL
                    if(genderint==1){

                        at = (float) (2.447 - (0.09156 * ageint) + (0.1074 * heightfloat * 100) + (0.3362 * weightfloat));
                        //Toast.makeText(newcalculation.this, ageint+"idade", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(newcalculation.this, heightfloat+"altura", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(newcalculation.this, weightfloat+"peso", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(newcalculation.this, at+"agua", Toast.LENGTH_SHORT).show();

                    }else{

                        at= (float) (2.079 + (0.1069 * heightfloat * 100) + (0.2446 * weightfloat));
                        //Toast.makeText(newcalculation.this, at+"agua", Toast.LENGTH_SHORT).show();

                    }

                    //MINERAL
                    mineral = (weightfloat - (mg + at))*1/3;
                    //Toast.makeText(newcalculation.this, mineral+"mineral", Toast.LENGTH_SHORT).show();

                    //PROTEINA
                    proteina = (weightfloat - (mg + at))*2/3;
                    //Toast.makeText(newcalculation.this, proteina+"proteina", Toast.LENGTH_SHORT).show();


                    //GUARDAR OS RESULTADOS NA FIREBASE
                    String imcfb = Float.toString(imc);
                    String pesominfb = Float.toString(pesomin);
                    String pesomaxfb = Float.toString(pesomax);
                    String igfb = Float.toString(ig);
                    String mgfb = Float.toString(mg);
                    String atfb = Float.toString(at);
                    String mineralfb = Float.toString(mineral);
                    String proteinafb = Float.toString(proteina);
                    String datafb = data;
                    String weightfb = Float.toString(weightfloat);



                    Map results = new HashMap();
                    results.put("date", datafb);
                    results.put("imc", imcfb);
                    results.put("pesomin", pesominfb);
                    results.put("pesomax", pesomaxfb);
                    results.put("ig", igfb);
                    results.put("mg", mgfb);
                    results.put("at", atfb);
                    results.put("mineral", mineralfb);
                    results.put("proteina", proteinafb);
                    results.put("weight", weightfb);


                    databaseReference.child("users").child(user).child("results").push().setValue(results);



                    startActivity(new Intent(newcalculation.this, imchistoric.class));
                }

            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(weight.getText().toString().isEmpty()||date.getText().toString().isEmpty()){
                    Toast.makeText(newcalculation.this, getResources().getString(R.string.insertpersonaldata), Toast.LENGTH_SHORT).show();


                }else{
                    String newweight = weight.getText().toString();

                    weightfloat = Float.parseFloat(newweight);

                    //calculo imc
                    imc = weightfloat / (heightfloat * heightfloat);
                    //Toast.makeText(newcalculation.this, imc+"imc", Toast.LENGTH_SHORT).show();


                    //CALCULO DO PESO MINIMO
                    pesomin = heightfloat*heightfloat*20;
                    //Toast.makeText(newcalculation.this, pesomin+"min", Toast.LENGTH_SHORT).show();

                    //CALCULO DO PESO MAXIMO
                    pesomax = heightfloat*heightfloat*25;
                    //Toast.makeText(newcalculation.this, pesomax+"max", Toast.LENGTH_SHORT).show();

                    //INDICE DE GORDURA
                    //String newage =
                    //agefloat = Float.parseFloat(String.valueOf(ageint));
                    //genderfloat = Float.parseFloat(String.valueOf(genderint));

                    if(ageint<18){
                        ig = (float) (1.51*imc-0.7*ageint-3.6*genderint+1.4);
                        //Toast.makeText(newcalculation.this, ig+"gordura", Toast.LENGTH_SHORT).show();

                    }else{
                        ig = (float) (1.2*imc+0.23*ageint-10.8*genderint-5.4);
                        //Toast.makeText(newcalculation.this, ig+"gordura", Toast.LENGTH_SHORT).show();

                    }

                    //MASSA GORDA
                    mg = (weightfloat*ig)/100;
                    //Toast.makeText(newcalculation.this, mg+"massagorda", Toast.LENGTH_SHORT).show();

                    //AGUA TOTAL
                    if(genderint==1){

                        at = (float) (2.447 - (0.09156 * ageint) + (0.1074 * heightfloat * 100) + (0.3362 * weightfloat));
                        //Toast.makeText(newcalculation.this, ageint+"idade", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(newcalculation.this, heightfloat+"altura", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(newcalculation.this, weightfloat+"peso", Toast.LENGTH_SHORT).show();
                        //Toast.makeText(newcalculation.this, at+"agua", Toast.LENGTH_SHORT).show();

                    }else{

                        at= (float) (2.079 + (0.1069 * heightfloat * 100) + (0.2446 * weightfloat));
                        //Toast.makeText(newcalculation.this, at+"agua", Toast.LENGTH_SHORT).show();

                    }

                    //MINERAL
                    mineral = (weightfloat - (mg + at))*1/3;
                    //Toast.makeText(newcalculation.this, mineral+"mineral", Toast.LENGTH_SHORT).show();

                    //PROTEINA
                    proteina = (weightfloat - (mg + at))*2/3;
                    //Toast.makeText(newcalculation.this, proteina+"proteina", Toast.LENGTH_SHORT).show();


                    //Toast.makeText(newcalculation.this, data+"", Toast.LENGTH_SHORT).show();

                    //GUARDAR OS RESULTADOS NA FIREBASE
                    String imcfb = Float.toString(imc);
                    String pesominfb = Float.toString(pesomin);
                    String pesomaxfb = Float.toString(pesomax);
                    String igfb = Float.toString(ig);
                    String mgfb = Float.toString(mg);
                    String atfb = Float.toString(at);
                    String mineralfb = Float.toString(mineral);
                    String proteinafb = Float.toString(proteina);
                    String datafb = data;
                    String weightfb = Float.toString(weightfloat);



                    Map results = new HashMap();
                    results.put("date", datafb);
                    results.put("imc", imcfb);
                    results.put("pesomin", pesominfb);
                    results.put("pesomax", pesomaxfb);
                    results.put("ig", igfb);
                    results.put("mg", mgfb);
                    results.put("at", atfb);
                    results.put("mineral", mineralfb);
                    results.put("proteina", proteinafb);
                    results.put("weight", weightfb);


                    databaseReference.child("users").child(user).child("results").push().setValue(results);

                    Toast.makeText(newcalculation.this, getResources().getString(R.string.savedata), Toast.LENGTH_SHORT).show();
                }

            }
        });


        imcHistoric.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                startActivity(new Intent(newcalculation.this, imchistoric.class));
                
            }
        });


    }
}

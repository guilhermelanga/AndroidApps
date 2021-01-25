package com.example.guilh.mefit;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
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

public class registerpersonaldata extends AppCompatActivity {

    private CheckBox registerMale, registerFemale;
    private TextView registerBirthday;
    private Button saveRegister, gotoHomepage;
    private Calendar currentDate;
    private int day, month, year;
    private TextView registerName, registerHeight;
    private int sex, age, selectedsex;
    private Button newCalculation;
    String birthday;


    //BASE DE DADOS
    private FirebaseAuth auth;

    //PEGAR A REFERENCIA DA BASE DE DADOS
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onStart() {
        super.onStart();
        //VERIFICAR SE O UTILIZAR ESTÁ LOGADO
        FirebaseUser currentUser = auth.getCurrentUser();

        if(currentUser == null){
            Intent intent = new Intent(registerpersonaldata.this, Login.class);
            startActivity(intent);
        }else {
            //Toast.makeText(this, getResources().getString(R.string.signin), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registerpersonaldata);

        saveRegister = findViewById(R.id.saveRegister);
        gotoHomepage = findViewById(R.id.gotoHomepage);
        registerMale = findViewById(R.id.registerMale);
        registerFemale = findViewById(R.id.registerFemale);
        registerName = findViewById(R.id.registerName);
        registerHeight = findViewById(R.id.registerHeight);
        registerBirthday = findViewById(R.id.registerBirthday);
        newCalculation = findViewById(R.id.newCalculation);
        currentDate = Calendar.getInstance();
        day = currentDate.get(Calendar.DAY_OF_MONTH);
        month = currentDate.get(Calendar.MONTH);
        year = currentDate.get(Calendar.YEAR);


        //LIGAR A BASE DE DADOS
        auth = FirebaseAuth.getInstance();

        //PEGAR O UTILIZADOR LOGADO
        FirebaseUser currentUser = auth.getCurrentUser();
        final String user = currentUser.getUid();

        databaseReference.child("users").child(user).child("height").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String height = dataSnapshot.getValue(String.class);
                registerHeight.setText(height);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("users").child(user).child("name").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String name = dataSnapshot.getValue(String.class);
                registerName.setText(name);
                
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("users").child(user).child("birthday").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                String birthday = dataSnapshot.getValue(String.class);
                registerBirthday.setText(birthday);

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        databaseReference.child("users").child(user).child("gender").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                String sexo = dataSnapshot.getValue(String.class);

                selectedsex = Integer.parseInt(sexo);

                if (selectedsex==0){
                    registerMale.setChecked(false);
                    registerFemale.setChecked(true);
                }else{
                    registerMale.setChecked(true);
                    registerFemale.setChecked(false);
                }

                //Toast.makeText(registerpersonaldata.this, selectedsex, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




        gotoHomepage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(registerpersonaldata.this, homepage.class));
            }
        });

        //CHECKBOX NÃO MARCA AMBOS OS SEXOS E NÃO FICA SEM MARCAÇÃO
        registerMale.setChecked(true);

        registerMale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (registerMale.isChecked()){
                    registerMale.setChecked(true);
                    registerFemale.setChecked(false);
                }else{
                    registerMale.setChecked(false);
                    registerFemale.setChecked(true);
                }
            }
        });

        registerFemale.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    if (registerFemale.isChecked()){
                        registerMale.setChecked(false);
                        registerFemale.setChecked(true);
                    }else{
                        registerMale.setChecked(true);
                        registerFemale.setChecked(false);
                    }
            }
        });

        //DATEPICKER
        registerBirthday.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog datePickerDialog = new DatePickerDialog(registerpersonaldata.this, new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker datePicker, int selectedYear, int selectedMonth, int selectedDay) {

                        year = selectedYear;
                        month = selectedMonth;
                        day = selectedDay;

                        registerBirthday.setText(selectedDay + "/" + (selectedMonth + 1) + "/" + selectedYear);

                        birthday=selectedDay+"/"+(selectedMonth+1)+"/"+selectedYear;

                    }
                }, year, month, day);
                datePickerDialog.show();

                if (month > currentDate.get(Calendar.MONTH)) {
                    age = currentDate.get(Calendar.YEAR) - year - 1;
                    //Toast.makeText(register.this, (currentDate.get(Calendar.YEAR) - year - 1) + "anos", Toast.LENGTH_SHORT).show();
                } else if (month == currentDate.get(Calendar.MONTH)) {
                    if (day > currentDate.get(Calendar.DAY_OF_MONTH)) {
                        age = currentDate.get(Calendar.YEAR) - year - 1;
                        //Toast.makeText(register.this, (currentDate.get(Calendar.YEAR) - year - 1) + "anos", Toast.LENGTH_SHORT).show();
                    } else {
                        age = currentDate.get(Calendar.YEAR) - year;
                        //Toast.makeText(register.this, (currentDate.get(Calendar.YEAR) - year) + "anos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    age = currentDate.get(Calendar.YEAR) - year;
                    //Toast.makeText(register.this, (currentDate.get(Calendar.YEAR) - year) + "anos", Toast.LENGTH_SHORT).show();
                }
            }
        });

        saveRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //VERIFICAR SE HA CAMPOS VAZIOS
                if (registerName.getText().toString().isEmpty() || registerHeight.getText().toString().isEmpty() || registerBirthday.getText().toString().isEmpty()) {
                    Toast.makeText(registerpersonaldata.this, getResources().getString(R.string.insertpersonaldata), Toast.LENGTH_SHORT).show();
                }

                if(registerMale.isChecked()){
                    sex=1;
                    //Toast.makeText(register.this, "Male", Toast.LENGTH_SHORT).show();
                }else{
                    sex=0;
                    //Toast.makeText(register.this, "Female", Toast.LENGTH_SHORT).show();
                }

                if (month > currentDate.get(Calendar.MONTH)) {
                    age=currentDate.get(Calendar.YEAR) - year - 1;
                    //Toast.makeText(register.this, (currentDate.get(Calendar.YEAR) - year - 1) + "anos", Toast.LENGTH_SHORT).show();
                } else if (month == currentDate.get(Calendar.MONTH)) {
                    if (day > currentDate.get(Calendar.DAY_OF_MONTH)) {
                        age=currentDate.get(Calendar.YEAR) - year - 1;
                        //Toast.makeText(register.this, (currentDate.get(Calendar.YEAR) - year - 1) + "anos", Toast.LENGTH_SHORT).show();
                    } else {
                        age=currentDate.get(Calendar.YEAR) - year;
                        //Toast.makeText(register.this, (currentDate.get(Calendar.YEAR) - year) + "anos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    age=currentDate.get(Calendar.YEAR) - year;
                    //Toast.makeText(register.this, (currentDate.get(Calendar.YEAR) - year) + "anos", Toast.LENGTH_SHORT).show();
                }

                String registername = registerName.getText().toString();
                String registerheight = registerHeight.getText().toString();
                String sexstr = String.valueOf(sex);
                String agestr = String.valueOf(age);



                databaseReference.child("users").child(user).child("name").setValue(registername);
                databaseReference.child("users").child(user).child("height").setValue(registerheight);
                databaseReference.child("users").child(user).child("gender").setValue(sexstr);
                databaseReference.child("users").child(user).child("age").setValue(agestr);
                databaseReference.child("users").child(user).child("birthday").setValue(birthday);

                Toast.makeText(registerpersonaldata.this, getResources().getString(R.string.savedata), Toast.LENGTH_SHORT).show();



            }
    });

        newCalculation.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //VERIFICAR SE HA CAMPOS VAZIOS
                if (registerName.getText().toString().isEmpty() || registerHeight.getText().toString().isEmpty() || registerBirthday.getText().toString().isEmpty()) {
                    Toast.makeText(registerpersonaldata.this, getResources().getString(R.string.insertpersonaldata), Toast.LENGTH_SHORT).show();
                }

                if(registerMale.isChecked()){
                    sex=1;
                    //Toast.makeText(register.this, "Male", Toast.LENGTH_SHORT).show();
                }else{
                    sex=0;
                    //Toast.makeText(register.this, "Female", Toast.LENGTH_SHORT).show();
                }

                if (month > currentDate.get(Calendar.MONTH)) {
                    age=currentDate.get(Calendar.YEAR) - year - 1;
                    //Toast.makeText(register.this, (currentDate.get(Calendar.YEAR) - year - 1) + "anos", Toast.LENGTH_SHORT).show();
                } else if (month == currentDate.get(Calendar.MONTH)) {
                    if (day > currentDate.get(Calendar.DAY_OF_MONTH)) {
                        age=currentDate.get(Calendar.YEAR) - year - 1;
                        //Toast.makeText(register.this, (currentDate.get(Calendar.YEAR) - year - 1) + "anos", Toast.LENGTH_SHORT).show();
                    } else {
                        age=currentDate.get(Calendar.YEAR) - year;
                        //Toast.makeText(register.this, (currentDate.get(Calendar.YEAR) - year) + "anos", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    age=currentDate.get(Calendar.YEAR) - year;
                    //Toast.makeText(register.this, (currentDate.get(Calendar.YEAR) - year) + "anos", Toast.LENGTH_SHORT).show();
                }

                String registername = registerName.getText().toString();
                String registerheight = registerHeight.getText().toString();
                String sexstr = String.valueOf(sex);
                String agestr = String.valueOf(age);



                databaseReference.child("users").child(user).child("name").setValue(registername);
                databaseReference.child("users").child(user).child("height").setValue(registerheight);
                databaseReference.child("users").child(user).child("gender").setValue(sexstr);
                databaseReference.child("users").child(user).child("age").setValue(agestr);
                databaseReference.child("users").child(user).child("birthday").setValue(birthday);

                Toast.makeText(registerpersonaldata.this, getResources().getString(R.string.savedata), Toast.LENGTH_SHORT).show();

                startActivity(new Intent(registerpersonaldata.this, newcalculation.class));

            }
        });






    }
}
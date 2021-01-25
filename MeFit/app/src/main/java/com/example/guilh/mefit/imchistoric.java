package com.example.guilh.mefit;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class imchistoric extends AppCompatActivity {

    private ListView imcListview;

    //BASE DE DADOS
    private FirebaseAuth auth;

    //PEGAR A REFERENCIA DA BASE DE DADOS
    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_imchistoric);

        imcListview = findViewById(R.id.imcListview);

        //LIGAR A BASE DE DADOS
        auth = FirebaseAuth.getInstance();

        //PEGAR O UTILIZADOR LOGADO
        FirebaseUser currentUser = auth.getCurrentUser();
        final String user = currentUser.getUid();

        final ArrayList<Data> dataList = new ArrayList<Data>();
        final ArrayList<Keys> keysList = new ArrayList<Keys>();

        final ArrayAdapter<Data> dataArrayAdapter = new ArrayAdapter<Data>(this, android.R.layout.simple_list_item_1, dataList);

        imcListview.setAdapter(dataArrayAdapter);


        databaseReference.child("users").child(user).child("results").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                dataList.clear();
                keysList.clear();
                dataArrayAdapter.clear();

                Iterable<DataSnapshot> children = dataSnapshot.getChildren();

                for(DataSnapshot child : children){
                    Data data = child.getValue(Data.class);
                    Keys indexKey = child.getValue(Keys.class);

                    String fbKey = child.getKey().toString();

                    indexKey.setKey(fbKey);

                    keysList.add(indexKey);
                    dataList.add(data);
                    dataArrayAdapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }
}

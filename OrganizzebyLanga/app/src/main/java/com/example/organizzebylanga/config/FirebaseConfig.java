package com.example.organizzebylanga.config;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;


public class FirebaseConfig {

    private static FirebaseAuth auth;
    private static DatabaseReference firebase;

    //CONFIGURA O AUTH DO FIREBASE BASE(BUSCA A INSTÂNCIA DO UTILIZADOR)
    public static FirebaseAuth getFirebaseAuth(){
        if(auth==null){
            auth = FirebaseAuth.getInstance();
        }
        return  auth;
    }

    //CONFIGURA O DATABASEREFERENCE (BUSCA A BASE DE DADOS)
    public static DatabaseReference getFirebaseDataBase(){
        if(firebase==null){
            firebase = FirebaseDatabase.getInstance().getReference();
        }
        return  firebase;
    }
}

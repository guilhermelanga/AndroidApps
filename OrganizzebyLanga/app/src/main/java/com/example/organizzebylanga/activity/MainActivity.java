package com.example.organizzebylanga.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;


import com.example.organizzebylanga.R;
import com.example.organizzebylanga.config.FirebaseConfig;
import com.google.firebase.auth.FirebaseAuth;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;
import com.heinrichreimersoftware.materialintro.slide.FragmentSlide;


public class MainActivity extends IntroActivity {

    //CONEXÃO COM O FIREBASE
    private FirebaseAuth auth;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_main);


        //CRIAR OS SLIDES INICIAIS
        setButtonBackVisible(false);
        setButtonNextVisible(false);

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_fragments_1)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_fragments_2)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_fragments_3)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_fragments_4)
                .build()
        );

        addSlide(new FragmentSlide.Builder()
                .background(android.R.color.white)
                .fragment(R.layout.intro_fragments_5)
                .canGoForward(false)
                .build()
        );

    }


    @Override
    protected void onStart() {
        super.onStart();

        //VERIFICAR SE JÁ HÁ LOGIN
        verificarUtilizadorLogado();
    }

    //ONCLICK DO BOTÃO CRIAR CONTA
    public void btCrieConta(View view){
        startActivity(new Intent(this, RegistoActivity.class));
    }

    //ONCLICK DO TEXTO JÁ TENHO CONTA
    public void btEntrar(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }

    //MÉTODO VERIFICA SE O UTILIZADOR JÁ ESTÁ COM O LOGIN EFETUADO
    public void verificarUtilizadorLogado(){

        auth = FirebaseConfig.getFirebaseAuth();
        //auth.signOut();
        if(auth.getCurrentUser()!=null){
            abrirPrincipalActivity();
        }
    }

    //MÉTODO PARA ABRIR A ACTIVITY EM CASO NO UTILIZADOR JÁ LOGADO
    public void abrirPrincipalActivity(){
        startActivity(new Intent(this, PrincipalActivity.class));
    }
}
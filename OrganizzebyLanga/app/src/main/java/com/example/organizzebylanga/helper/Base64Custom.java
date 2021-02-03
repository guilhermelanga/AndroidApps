package com.example.organizzebylanga.helper;


import android.util.Base64;

public class Base64Custom {

    //CODIFICA O EMAIL DO UTILIZADOR EM BASE64
    public static String codifica(String txt){
        return Base64.encodeToString(txt.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
    }

    //DECOCODIFICA O EMAIL DO UTILIZADOR EM BASE64
    public static String decodifica(String txtcodificado){
        return new String(Base64.decode(txtcodificado, Base64.DEFAULT));

    }
}

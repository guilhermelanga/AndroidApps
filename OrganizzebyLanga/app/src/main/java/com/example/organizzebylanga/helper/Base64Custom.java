package com.example.organizzebylanga.helper;


import android.util.Base64;

public class Base64Custom {

    public static String codifica(String txt){
        return Base64.encodeToString(txt.getBytes(), Base64.DEFAULT).replaceAll("(\\n|\\r)", "");
    }

    public static String decodifica(String txtcodificado){
        return new String(Base64.decode(txtcodificado, Base64.DEFAULT));

    }
}

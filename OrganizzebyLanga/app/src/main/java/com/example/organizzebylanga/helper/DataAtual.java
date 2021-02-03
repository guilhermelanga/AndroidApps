package com.example.organizzebylanga.helper;

import java.text.SimpleDateFormat;

public class DataAtual {

    //FORMATA A DATA ATUAL
    public static String dataAtual(){

        long data = System.currentTimeMillis();
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");
        String dataString = simpleDateFormat.format(data);
        return dataString;
    }

    //FAZ COM QUE SEJA POSSÍVEL TERMOS APENAS O MES E ANO SELECIONADOS
    //PARA BUSCAR NA BASE DE DADOS APENAS OS MOVIENTOS DE UM ESPECÍFICO MES E ANO
    public static String mesAnoDataSelecionada(String data){
        String retornoData[] = data.split("/");
        String dia = retornoData[0];
        String mes = retornoData[1];
        String ano = retornoData[2];

        String mesAno= mes+ano;
        return  mesAno;
    }
}

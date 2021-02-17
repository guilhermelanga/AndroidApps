package com.example.youtubebylanga.model;

import java.util.List;

//RECEBE E ARQUIVA TODOS OS DADOS VINDOS DA API EM JSON
public class Resultado {

    //GUARDA O PRIMEIRO ITEM(OBJETO)
    public String regionCode;


    //DENTRO DO PRIMEIRO OBJETO EXISTE OUTROS OBJETOS
    //ESTE É ARMAZENADO EM OUTRA CLASS
    public PageInfo pageInfo;
    public List<Items> items;
}

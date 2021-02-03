package com.example.organizzebylanga.model;

import com.example.organizzebylanga.config.FirebaseConfig;
import com.example.organizzebylanga.helper.Base64Custom;
import com.example.organizzebylanga.helper.DataAtual;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.Base64;

public class Movimentos {

    private String data, categoria, descricao, tipo, id;

    private Double valor;

    public void guardar(String dataSelecionada){
        FirebaseAuth auth = FirebaseConfig.getFirebaseAuth();
        String idUtilizador = Base64Custom.codifica(auth.getCurrentUser().getEmail());
        String mesAno = DataAtual.mesAnoDataSelecionada(dataSelecionada);
        DatabaseReference firebase = FirebaseConfig.getFirebaseDataBase();
        firebase.child("movimentos")
                .child(idUtilizador)
                .child(mesAno)
                .push()
                .setValue(this);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public Movimentos() {
    }
}

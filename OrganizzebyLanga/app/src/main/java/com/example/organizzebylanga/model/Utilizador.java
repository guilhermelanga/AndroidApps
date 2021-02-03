package com.example.organizzebylanga.model;


import com.example.organizzebylanga.config.FirebaseConfig;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

public class Utilizador {

    private String nome, email, palavraPasse, idUtilizador;
    private double receitaTotal=0.00, despesaTotal=0.00;

    public Utilizador() {
    }

    public void guardar(){
        DatabaseReference firebase = FirebaseConfig.getFirebaseDataBase();
        firebase.child("users")
                .child(this.idUtilizador)
                .setValue(this);

    }

    public double getReceitaTotal() {
        return receitaTotal;
    }

    public void setReceitaTotal(double receitaTotal) {
        this.receitaTotal = receitaTotal;
    }

    public double getDespesaTotal() {
        return despesaTotal;
    }

    public void setDespesaTotal(double despesaTotal) {
        this.despesaTotal = despesaTotal;
    }

    @Exclude
    public String getIdUtilizador() {
        return idUtilizador;
    }

    public void setIdUtilizador(String idUtilizador) {
        this.idUtilizador = idUtilizador;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Exclude
    public String getPalavraPasse() {
        return palavraPasse;
    }

    public void setPalavraPasse(String palavraPasse) {
        this.palavraPasse = palavraPasse;
    }


}

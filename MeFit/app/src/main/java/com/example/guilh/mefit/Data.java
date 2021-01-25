package com.example.guilh.mefit;

public class Data {

    private String date, imc, pesomin, pesomax, ig, mg, at, mineral, proteina, weight;

    public String getDate(){
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getImc(){
        return imc;
    }

    public void setImc(String imc) {
        this.imc = imc;
    }

    public String getPesomin(){
        return pesomin;
    }

    public void setPesomin(String pesomin) {
        this.pesomin = pesomin;
    }

    public String getPesomax(){
        return pesomax;
    }

    public void setPesomax(String pesomax) {
        this.pesomax = pesomax;
    }

    public String getIg(){
        return ig;
    }

    public void setIg(String ig) {
        this.ig = ig;
    }

    public String getMg(){
        return mg;
    }

    public void setMg(String mg) {
        this.mg = mg;
    }

    public String getAt(){
        return at;
    }

    public void setAt(String at) {
        this.at = at;
    }
    public String getMineral(){
        return mineral;
    }

    public void setMineral(String mineral) {
        this.mineral = mineral;
    }

    public String getProteina(){
        return proteina;
    }


    public void setProteinafb(String proteinafb) {
        this.proteina = proteina;
    }

    public String getWeight(){
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public String toString(){
        return "\n"+"Data: " + date+"\n"+"Peso: " +weight+"\n"+"IMC: "+imc+"\n"+ "Peso Mínimo: " + pesomin+"\n"+"Peso Máximo: "+pesomax+"\n"+"Índice de Gordura: " + ig+"\n" +"Massa Gorda: "+mg+"\n" +"Água Total: "+at+"\n"+"Mineral: "+mineral+"\n"+"Proteína: "+proteina+"\n";
    }


}

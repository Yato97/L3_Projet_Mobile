package com.yato.projet;

public class Bird {
    //-----------------------Variables--------------------------//
    private String nom;
    private String memonic;
    private int vie;
    private String carte;
    private int score;
    private int meilleurescore;
    //----------------------------------------------------------//

    //-----------------------GETTERS----------------------------//
    //----------------------------------------------------------//
    public int getMeilleurescore() {
        return meilleurescore;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public void setMemonic(String memonic) {
        this.memonic = memonic;
    }

    public void setVie(int vie) {
        this.vie = vie;
    }

    public void setCarte(String carte) {
        this.carte = carte;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public void setMeilleurescore(int meilleurescore) {
        this.meilleurescore = meilleurescore;
    }
    //----------------------------------------------------------//
    //--------------------------GETTERS-------------------------//


    //-------------------------SETTERS--------------------------//
    //----------------------------------------------------------//
    public int getScore() { return score; }

    public String getNom() {
        return nom;
    }

    public int getVie() {
        return vie;
    }

    public String getMemonic() {
        return memonic;
    }

    public String getCarte() {
        return carte;
    }
    //----------------------------------------------------------//
    //-------------------------SETTERS--------------------------//


    //-----------------------CONSTRUCTEUR-----------------------//
    public Bird(String nom, int vie, String memonic, String carte, int score) {
        this.nom = nom;
        this.vie = vie;
        this.memonic = memonic;
        this.carte = carte;
        this.score = score;
    }
    //-----------------------CONSTRUCTEUR-----------------------//
}

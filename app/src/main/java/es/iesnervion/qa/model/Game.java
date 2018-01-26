package es.iesnervion.qa.model;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * Created by adripol94 on 3/8/17.
 */

public class Game {
    @SerializedName("id")
    private int idGame;
    @SerializedName("idCategory")
    private int idCategory;
    @SerializedName("idUserCreator")
    private int idUserCreator;
    @SerializedName("numGammers")
    private int numGammers;
    @SerializedName("questions")
    private List<IdQuestion> questions;

    public Game(int idCategory, int idUserCreator, int numGammers, List<IdQuestion> questions) {
        this.idCategory = idCategory;
        this.idUserCreator = idUserCreator;
        this.numGammers = numGammers;
        this.questions = questions;
    }

    public Game(int idGame, int idCategory, int idUserCreator, int numGammers, List<IdQuestion> questions) {
        this.idGame = idGame;
        this.idCategory = idCategory;
        this.idUserCreator = idUserCreator;
        this.numGammers = numGammers;
        this.questions = questions;
    }

    public int getIdGame() {
        return idGame;
    }

    public void setIdGame(int idGame) {
        this.idGame = idGame;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public int getIdUserCreator() {
        return idUserCreator;
    }

    public void setIdUserCreator(int idUserCreator) {
        this.idUserCreator = idUserCreator;
    }

    public int getNumGammers() {
        return numGammers;
    }

    public void setNumGammers(int numGammers) {
        this.numGammers = numGammers;
    }

    public List<IdQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<IdQuestion> questions) {
        this.questions = questions;
    }

}
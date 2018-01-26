package es.iesnervion.qa.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adripol94 on 3/9/17.
 */

public class IdQuestion {
    @SerializedName("idQuestion")
    private int idQuestion;


    public IdQuestion(int id) {
        this.idQuestion = id;
    }

    public int getId() {
        return idQuestion;
    }

    public void setId(int id) {
        this.idQuestion = id;
    }
}
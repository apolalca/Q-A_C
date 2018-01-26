package es.iesnervion.qa.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.List;

import es.iesnervion.qa.model.Category;
import es.iesnervion.qa.model.Game;
import es.iesnervion.qa.model.GoogleUser;
import es.iesnervion.qa.model.Question;
import es.iesnervion.qa.model.RetrofitInterfaceQA;
import es.iesnervion.qa.model.User;
import es.iesnervion.qa.model.Validator;
import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by adripol94 on 1/31/17.
 */

public class RetrofitControler {
    private static String NAME_SERVER = "https://api.apol.ciclo.iesnervion.es/";
    private RetrofitInterfaceQA service;
    private Retrofit retrofit;


    public RetrofitControler() {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(NAME_SERVER)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(RetrofitInterfaceQA.class);
    }

    public RetrofitControler(String url) {

        Gson gson = new GsonBuilder()
                .setLenient()
                .create();

        retrofit = new Retrofit.Builder()
                .baseUrl(url)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        service = retrofit.create(RetrofitInterfaceQA.class);
    }

    public Call<Question> getListQuestion(String token) {
        Call<Question> questions = service.lisQuestions(token);

        return questions;
    }

    public Call<List<Question>> getListQuestionByCategory(String token, String idCategory) {
        Call<List<Question>> call = service.getListQuestionsByCategory(token, idCategory);

        return call;
    }

    public Call<Validator> postValidationPoints(String token, Validator validator) {
        Call<Validator> call = service.makeValidation(token, validator);

        return call;
    }

    public Call<List<Category>> getListCategory(String token) {
        Call<List<Category>> categories = service.listCategory(token);

        return categories;
    }

    public Call<User> getUserByName(String token, String name) {
        Call<User> call = service.getUser(token, name);

        return call;
    }

    public Call<Game> postGame(String token, Game game) {
        Call<Game> call = service.postGame(token, game);
        return call;
    }

    /**
     * Connecta con el servidor con el token, en este caso el token tendra el nombre de usuario
     * conectado (por GoogleApiClient).
     * @param token Games Player Class -> Name of user.
     * @return
     */
    public Call<GoogleUser> postConnection(String token) {
        Call<GoogleUser> call = service.postConnect(token);
        return call;
    }
}

package es.iesnervion.qa.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * Created by apol on 1/02/17.
 */

public interface RetrofitInterfaceQA {
    @GET("question")
    Call<Question> lisQuestions(@Header("WWW-Authenticate") String token);

    @GET("question/{id}")
    Call<Question> getQuestion(@Header("WWW-Authenticate") String token);

    @GET("question")
    Call<List<Question>> getListQuestionsByCategory(@Header("WWW-Authenticate") String token, @Query("category") String idCategory);

    @GET("category")
    Call<List<Category>> listCategory(@Header("WWW-Authenticate") String token);

    @Deprecated
    @POST("user")
    Call<User> createUser(@Header("WWW-Authenticate") String token, @Body User user);

    @POST("validation")
    @Headers("Content-type: application/json")
    Call<Validator> makeValidation(@Header("WWW-Authenticate") String token, @Body Validator body);

    @POST("game")
    @Headers("Content-type: application/json")
    Call<Game> postGame(@Header("WWW-Authenticate") String token, @Body Game body);

    @GET("user")
    Call<User> getUser(@Header("WWW-Authenticate") String token, @Query("email") String email);

    @POST("connect")
    Call<GoogleUser> postConnect(@Header("WWW-Authenticate") String token);
}

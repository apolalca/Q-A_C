package es.iesnervion.qa.model;

import android.content.Context;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by apol on 1/02/17.
 */

public class CallBackProgress<T> implements Callback<T> {
    private Context c;
    private T lisato;
    Responser res;


    public CallBackProgress(Responser r, Context c) {
        this.c = c;
        res = r;
    }

    /**
     * Invoked for a received HTTP response.
     * <p>
     * Note: An HTTP response may still indicate an application-level failure such as a 404 or 500.
     * Call {@link Response#isSuccessful()} to determine if the response indicates success.
     *
     * @param call
     * @param response
     */
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.body() == null) {
            onFailure(call, new Throwable(response.message()));
        } else {
            lisato = response.body();
            String bearer = response.headers().get("WWW-Authenticate");
            res.onFinish(lisato, bearer);
        }
    }

    /**
     * Invoked when a network exception occurred talking to the server or when an unexpected
     * exception occurred creating the request or processing the response.
     *
     * @param call
     * @param t
     */
    @Override
    public void onFailure(Call<T> call, Throwable t) {
        //TODO solo para el depurado
        Toast.makeText(c, t.getMessage(), Toast.LENGTH_LONG).show();
        t.printStackTrace();
        res.onFailure(t);
    }
}

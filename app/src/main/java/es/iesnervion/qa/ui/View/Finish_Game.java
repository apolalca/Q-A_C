
package es.iesnervion.qa.ui.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.circleprogress.ArcProgress;
import com.google.gson.Gson;

import es.iesnervion.qa.controller.RetrofitControler;
import es.iesnervion.qa.model.Bearer;
import es.iesnervion.qa.model.CallBackProgress;
import es.iesnervion.qa.model.Responser;
import es.iesnervion.qa.model.Validator;
import es.iesnervion.qa.R;
import retrofit2.Call;

public class Finish_Game extends AppCompatActivity implements Responser<Validator> {

    public final static String validator = "Validator";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finish__game);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        int i = getIntent().getIntExtra(GamingActivity.BACK_ACTIVITY, 0);

        if (i == 1) {
            cancellGamming();
        } else {

            Validator validator = getIntent().getParcelableExtra(Finish_Game.validator);

            Gson gson = new Gson();
            String a = gson.toJson(validator);

            String token = Bearer.getDefaults(Bearer.BEARER_KEY, this);
            RetrofitControler retrofitControler = new RetrofitControler();

            try {
                Call<Validator> listCall = retrofitControler.postValidationPoints(token, validator);
                listCall.enqueue(new CallBackProgress<Validator>(this, this));
            } catch (Exception e) {
                //TODO for depure
                e.printStackTrace();
            }

        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(view -> {
            Intent it = new Intent(Finish_Game.this, MenuActivity.class);
            startActivity(it);
        });
    }

    private void cancellGamming() {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.finishGameRespuestasMostrar);
        TextView tv = new TextView(this);
        tv.setText("Has salido de la partida");

        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(500,200);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        relativeLayout.addView(tv,params);
    }

    private void createRating(Validator obj) {
        RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.finishGameRespuestasMostrar);

        // Cogemos el nombre de usuario
        String user = Bearer.getDefaults(Bearer.USER_NAME_KEY, this);

        ArcProgress ratting = new ArcProgress(this);
        ratting.setProgress(obj.getPoints());
        ratting.setBottomText(user);
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(300,300);
        params.addRule(RelativeLayout.CENTER_IN_PARENT);
        relativeLayout.addView(ratting,params);
    }

    @Override
    public void onFinish(Validator obj, String bearer) {
        findViewById(R.id.finishGameCargaResp).setVisibility(View.GONE);
        findViewById(R.id.finishGameRespuestasMostrar).setVisibility(View.VISIBLE);
        createRating(obj);
    }

    @Override
    public void onFailure(Throwable t) {

    }
}

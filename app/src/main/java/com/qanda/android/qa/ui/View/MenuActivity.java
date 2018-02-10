package com.qanda.android.qa.ui.View;

import android.app.ActivityOptions;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import com.qanda.android.qa.R;
import com.qanda.android.qa.ui.Transitions.TransitionInActivity;

public class MenuActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
    }

    private void startActivityWithOptions(Intent intent) {
        ActivityOptions transitionActivity =
                ActivityOptions.makeSceneTransitionAnimation(MenuActivity.this);
        startActivity(intent, transitionActivity.toBundle());
    }


    private void handlerLuncher(View v, Class activity) {
        v.startAnimation(AnimationUtils.loadAnimation(
                v.getContext(), android.R.anim.fade_out));

        Intent it = new Intent(MenuActivity.this, activity.class);
        it.putExtra(TransitionInActivity.EXTRA_TRANSITION, TransitionInActivity.TRANSITION_EXPLODE);
        startActivityWithOptions(it);
    }

    private void handlerSalir() {
        Intent newIntent = new Intent(this, WelcomeActivity.class);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        newIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        newIntent.putExtra(WelcomeActivity.FLAG_OUT ,WelcomeActivity.RC_SIGN_OUT);
        startActivity(newIntent);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.txtJuego:
                handlerLuncher(v, CategoriesActivity.class);
                break;
            case R.id.txtSalir:
                handlerSalir();
                break;
            case R.id.txtSettings:
                handlerLuncher(v, );
                break;
            case R.id.txtAmigos:
                handlerLuncher(v, );
                break;
            case R.id.txtRealizarPreguntas:
                handlerLuncher(v, );
                break;
        }
    }
}

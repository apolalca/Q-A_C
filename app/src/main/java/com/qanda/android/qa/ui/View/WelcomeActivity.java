package com.qanda.android.qa.ui.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.qanda.android.qa.R;
import com.qanda.android.qa.controller.RetrofitControler;
import com.qanda.android.qa.model.Bearer;
import com.qanda.android.qa.model.CallBackProgress;
import com.qanda.android.qa.model.GoogleUser;
import com.qanda.android.qa.model.Responser;

import retrofit2.Call;

public class WelcomeActivity extends AppCompatActivity implements Responser<GoogleUser>, View.OnClickListener {

    static {
        System.loadLibrary("native-lib");
    }

    // Google references
    public static final int RC_SIGN_IN = 9001;
    public static final int RC_SIGN_OUT = 9002;
    public static final String FLAG_OUT = "Salir";
    private static final String TAG = "WelcomeActivity";
    private GoogleSignInClient mGoogleApiClient;
    private boolean mExplicitSignOut = false;


    private RetrofitControler retrofitControler;
    public native String stringFromJNI();


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        findViewById(R.id.sign_in_button).setOnClickListener(this);
        updateUI("");
        Intent it = getIntent();

        Toast.makeText(this, stringFromJNI(), Toast.LENGTH_LONG).show();
        buildGoogle();

        if (it != null && it.getIntExtra(FLAG_OUT, 0) == RC_SIGN_OUT) {
            mExplicitSignOut = true;
            mGoogleApiClient.revokeAccess();
            mGoogleApiClient.signOut();
        }
    }

    /**
     * Actualiza una etiqueta en la pantalla responsable de informar que es lo que esta sucediendo
     * @param str
     */
    private void updateUI(String str) {
        ((TextView) findViewById(R.id.information_label)).setText(str);
    }

    public void goToMenuActivity() {
        Intent it = new Intent(WelcomeActivity.this, MenuActivity.class);
        startActivity(it);
    }

    @Deprecated
    public void goToLoginActivity() {
        Intent it = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivityForResult(it, RC_SIGN_OUT);
    }

    /**
     * Build a Google API Client
     */
    private void buildGoogle() {
        // Configure sign-in to request the user's ID, email address, and basic
        // profile. ID and basic profile are included in DEFAULT_SIGN_IN.
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        mGoogleApiClient = GoogleSignIn.getClient(this, gso);
    }

    private void connectWithApi(String name) {

        String token = "Basic " + Base64.encodeToString(name.getBytes(), Base64.NO_WRAP);

        retrofitControler = new RetrofitControler();
        try {
            Call<GoogleUser> listCall = new RetrofitControler().postConnection(token);
            listCall.enqueue(new CallBackProgress<>(WelcomeActivity.this, WelcomeActivity.this));
        } catch (Exception e) {
            //TODO for depure
            e.printStackTrace();
        }
    }


    /**
     * Called after {@link #onCreate} &mdash; or after {@link #onRestart} when
     * the activity had been stopped, but is now again being displayed to the
     * user.  It will be followed by {@link #onResume}.
     * <p>
     * <p><em>Derived classes must call through to the super class's
     * implementation of this method.  If they do not, an exception will be
     * thrown.</em></p>
     *
     * @see #onCreate
     * @see #onStop
     * @see #onResume
     */
    @Override
    protected void onStart() {
        super.onStart();

        if (!mExplicitSignOut) {
            // Check for existing Google Sign In account, if the user is already signed in
            // the GoogleSignInAccount will be non-null.
            GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(this);
            if (account != null) {
                connectWithApi(account.getDisplayName());
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        updateUI("");
    }

    /**
     * Dispatch incoming result to the correct fragment.
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            try {
                Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                GoogleSignInAccount account = task.getResult(ApiException.class);
                connectWithApi(account.getDisplayName());
            } catch (ApiException e) {
                e.printStackTrace();
            }
        } else if (requestCode == RC_SIGN_OUT) {
            mGoogleApiClient.signOut().addOnCompleteListener(this, task -> updateUI("Hasta pronto"));
        }
    }

    @Override
    public void onFinish(GoogleUser obj, String bearer) {
        Bearer.setDefaults(Bearer.BEARER_KEY, bearer, this);
        Bearer.setDefaultsInt(Bearer.USER_ID_KEY, obj.getId(), this);
        Bearer.setDefaults(Bearer.USER_NAME_KEY, obj.getName(), this);

        goToMenuActivity();

    }

    @Override
    public void onFailure(Throwable t) {
        Toast.makeText(this, R.string.connectToApiFailure, Toast.LENGTH_LONG);
    }

    /**
     * Called when a view has been clicked.
     *
     * @param v The view that was clicked.
     */
    @Override
    public void onClick(View v) {
        Intent singIntent = mGoogleApiClient.getSignInIntent();
        startActivityForResult(singIntent, RC_SIGN_IN);
    }
}

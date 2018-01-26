package es.iesnervion.qa.ui.View;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.games.Games;
import com.google.android.gms.games.Player;
import com.google.example.games.basegameutils.BaseGameUtils;

import es.iesnervion.qa.controller.RetrofitControler;
import es.iesnervion.qa.model.Bearer;
import es.iesnervion.qa.model.CallBackProgress;
import es.iesnervion.qa.model.GoogleUser;
import es.iesnervion.qa.model.Responser;
import es.iesnervion.qa.R;
import retrofit2.Call;

public class WelcomeActivity extends AppCompatActivity implements
        GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener,
        Responser<GoogleUser> {

    static {
        System.loadLibrary("native-lib");
    }

    // Google references
    private static int RC_SIGN_IN = 9001;
    public static int RC_SIGN_OUT = 9002;
    // Cuando se puelsa al boton exit da como resultado ese flag
    private int FLAG_SIGNOUT = 335544320;
    private GoogleApiClient mGoogleApiClient;
    private boolean mExplicitSignOut = false;
    private boolean mInSignInFlow = false;

    private boolean mResolvingConnectionFailure = false;
    private boolean mAutoStartSignInflow = true;
    private boolean mSignInClicked = false;
    private boolean mSignOutClicked = false;

    private RetrofitControler retrofitControler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        ((TextView) findViewById(R.id.information_label)).setText("");
        Toast.makeText(this, stringFromJNI(), Toast.LENGTH_LONG);
        Intent it = getIntent();

        if (it != null && it.getFlags() == FLAG_SIGNOUT) {
            //En este caso se ha salido de la App
            mExplicitSignOut = true;
            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                Games.signOut(mGoogleApiClient);
                mGoogleApiClient.disconnect();
            }
        }
        buildGoogle();
        buildView();
    }

    public native String stringFromJNI();

    private void buildView() {
        //Build Buttons listeners
        SignInButton mGooglSignInButton = (SignInButton) findViewById(R.id.sign_in_button);
        mGooglSignInButton.setSize(SignInButton.SIZE_WIDE);
        mGooglSignInButton.setOnClickListener(v -> attempLoginWithGoogle());
    }

    public void goToMenuActivity() {
        Intent it = new Intent(WelcomeActivity.this, MenuActivity.class);
        startActivityForResult(it, RC_SIGN_OUT);
    }

    @Deprecated
    public void goToLoginActivity() {
        Intent it = new Intent(WelcomeActivity.this, LoginActivity.class);
        startActivity(it);
    }

    /**
     * Build a Google API Client
     */
    private void buildGoogle() {

        // Create the Googe Api Client with access to the Play Game services
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(Games.API).addScope(Games.SCOPE_GAMES)
                .addApi(Drive.API).addScope(Drive.SCOPE_APPFOLDER) // Drive API
                .build();
    }

    private void attempLoginWithGoogle() {
        // start the asynchronous sign in flow
        mSignInClicked = true;
        ((TextView) findViewById(R.id.information_label)).setText("... Conectando");
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnected(@Nullable Bundle bundle) {
        Player p = Games.Players.getCurrentPlayer(mGoogleApiClient);
        synchronized (this) {
            try {
                wait(5000);
                ((TextView) findViewById(R.id.information_label)).setText("Conectado! Bienvenido...");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        connectWithApi(p.getName());
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


    @Override
    public void onConnectionSuspended(int i) {
        // Attempt to reconnect
        mGoogleApiClient.connect();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
        if (mResolvingConnectionFailure) {
            // already resolving
            return;
        }

        // if the sign-in button was clicked or if auto sign-in is enabled,
        // launch the sign-in flow
        if (mSignInClicked || mAutoStartSignInflow) {
            mAutoStartSignInflow = false;
            mSignInClicked = false;
            mResolvingConnectionFailure = true;

            // Attempt to resolve the connection failure using BaseGameUtils.
            // The R.string.signin_other_error value should reference a generic
            // error string in your strings.xml file, such as "There was
            // an issue with sign-in, please try again later."
            if (!BaseGameUtils.resolveConnectionFailure(this,
                    mGoogleApiClient, connectionResult,
                    RC_SIGN_IN, getString(R.string.signin_other_error))) {
                mResolvingConnectionFailure = false;
            }
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
        if (!mInSignInFlow && !mExplicitSignOut) {
            ((TextView) findViewById(R.id.information_label)).setText("... Conectando");

            // auto sign in
            if (mGoogleApiClient != null && !mGoogleApiClient.isConnected()) {
                mGoogleApiClient.connect();
            }
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        ((TextView) findViewById(R.id.information_label)).setText("");
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

        // Result returned from launching the Intent from
        //   GoogleSignInApi.getSignInIntent(...);
        if (requestCode == RC_SIGN_IN) {
            mSignInClicked = false;
            mResolvingConnectionFailure = false;

            if (resultCode == RESULT_OK) {
                mGoogleApiClient.connect();

            } else {
                // Bring up an error dialog to alert the user that sign-in
                // failed. The R.string.signin_failure should reference an error
                // string in your strings.xml file that tells the user they
                // could not be signed in, such as "Unable to sign in."
                BaseGameUtils.showActivityResultError(this,
                        requestCode, resultCode, R.string.signin_other_error);
            }
        } else if (requestCode == RC_SIGN_OUT) {

            mExplicitSignOut = true;
            if (mGoogleApiClient != null && mGoogleApiClient.isConnected()) {
                Games.signOut(mGoogleApiClient);
                mGoogleApiClient.disconnect();
            }
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
}

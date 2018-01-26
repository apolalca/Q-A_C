package es.iesnervion.qa.ui.View;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.github.lzyzsd.circleprogress.CircleProgress;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import es.iesnervion.qa.controller.RetrofitControler;
import es.iesnervion.qa.model.Answer;
import es.iesnervion.qa.model.Bearer;
import es.iesnervion.qa.model.CallBackProgress;
import es.iesnervion.qa.model.Category;
import es.iesnervion.qa.model.Game;
import es.iesnervion.qa.model.IdQuestion;
import es.iesnervion.qa.model.Question;
import es.iesnervion.qa.model.QuestionAnswer;
import es.iesnervion.qa.model.Responser;
import es.iesnervion.qa.model.ResponserAnswer;
import es.iesnervion.qa.model.TimerEndGamming;
import es.iesnervion.qa.model.Validator;
import es.iesnervion.qa.R;
import es.iesnervion.qa.ui.Adapter.AnswerAdapter;
import retrofit2.Call;

/**
 * IMPORTANTE: Ir al Manifest, esta actividad no se guarda en la pila de actividades
 */
public class GamingActivity extends AppCompatActivity implements ResponserAnswer, TimerEndGamming {
    private MediaPlayer mediaPlayer;
    private MediaPlayer mediaAnswerCorrectPlayer;
    private MediaPlayer mediaAnswerErrorPlayer;
    private int iClicks = 0;
    private int idGame;
    private int numPreguntas;
    private ProgressBar progressBarAnswerCount;
    private boolean isFinishGameRequest;
    private static final String CLICK_VALUE = "click_value";
    private CircleProgress arcClicks;
    private View mProgressView;
    private List<Question> questions;
    private int contadorPreguntas;
    private ArrayList<String> respuestas;
    private TextView questionGaming;
    private Timer timer;
    private Validator validator;
    private String token;
    private QuestionAnswer questionAnswer;
    private RetrofitControler retrofitControler;
    private ResponsePreguntas responsePreguntas;
    private ResponseGame responseGame;
    private Category category;
    public final static String BACK_ACTIVITY = "isBacked";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gaming);

        category = getIntent().getParcelableExtra(Category.CATEGORY_KEY);

        isFinishGameRequest = false;
        idGame = 0;
        numPreguntas = 0;

        //Inicializacion de las interfaces!!!!!!!!!!!
        responsePreguntas = new ResponsePreguntas();
        responseGame = new ResponseGame();

        progressBarAnswerCount = (ProgressBar)findViewById(R.id.progressBarAnswerCount);

        mediaPlayer = MediaPlayer.create(this, R.raw.music);
        mediaAnswerCorrectPlayer = MediaPlayer.create(this, R.raw.success);
        mediaAnswerErrorPlayer = MediaPlayer.create(this, R.raw.error);

        arcClicks = (CircleProgress) findViewById(R.id.clock_gaming_pb);
        mProgressView = findViewById(R.id.login_progress);
        questionGaming = (TextView)findViewById(R.id.question_gaming_tv);
        RelativeLayout rl = (RelativeLayout)findViewById(R.id.stackPanel_clockGamming);
        contadorPreguntas = 0;
        respuestas = new ArrayList<>();
        timer = new Timer();

        rl.setBackground(new BitmapDrawable(getResources(), category.getImgBitMap()));

        questionGaming.setText("Preparando las respuestas...");

        token = Bearer.getDefaults(Bearer.BEARER_KEY, this);
        int idUser = Bearer.getDefaultsInt(Bearer.USER_ID_KEY, this);

        validator = new Validator(idUser, category.getId());

        retrofitControler = new RetrofitControler();
        try {
            Call<List<Question>> listCall = retrofitControler.getListQuestionByCategory(token,
                    String.valueOf(category.getId()));
            listCall.enqueue(new CallBackProgress<List<Question>>(responsePreguntas, this));
        } catch (Exception e) {
            //TODO for depure
            e.printStackTrace();
        }
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(GamingActivity.CLICK_VALUE, iClicks);
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mediaPlayer.stop();
    }

    /**
     * Take care of popping the fragment back stack or finishing the activity
     * as appropriate.
     */
    @Override
    public void onBackPressed() {
        Intent it = new Intent(this, Finish_Game.class);
        it.putExtra(BACK_ACTIVITY, 1);
        startActivity(it);
    }

    private void setQuestions(Question q) {
        if (contadorPreguntas == 0)
            timer.schedule(new RunClock(this), 0, 1000);

        questionGaming.setText("Pregunta " + (contadorPreguntas + 1) +
                ": " + q.getQuestion());
        (findViewById(R.id.question_progress)).setVisibility(View.GONE);
        findViewById(R.id.txtInformacionGamming).setVisibility(View.GONE);

        questionAnswer = new QuestionAnswer();
        questionAnswer.setIdQuestion(q.getId());

        RecyclerView mRecyclerView = (RecyclerView)findViewById(R.id.answers_gaming_lv);
        AnswerAdapter mCategoryAdapter = new AnswerAdapter(questions.get(contadorPreguntas).getAnswer(), this);
        mRecyclerView.setAdapter(mCategoryAdapter);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setHasFixedSize(true);
    }

    @Override
    public void onAnswerSelected(Answer answer) {
        respuestas.add(answer.getAnswer());
        questionAnswer.setIdAnswer(answer.getId());
        validator.putAnswer(questionAnswer);
        numPreguntas++;

        if (answer.isCorrect()) {
            progressBarAnswerCount.setProgress(numPreguntas * 100 / questions.size());
            //Tenemos problemas cuando responden demasiado rapido no se escucha
            //Por eso tenemos que parar el audio y resetear volviendo a implementar el audio incluso.
            if (mediaAnswerCorrectPlayer.isPlaying()) {
                mediaAnswerCorrectPlayer.stop();
                mediaAnswerCorrectPlayer.reset();
                mediaAnswerCorrectPlayer = MediaPlayer.create(this, R.raw.success);
            }
            mediaAnswerCorrectPlayer.start();
        } else {
            mediaAnswerErrorPlayer.start();
        }

        if (contadorPreguntas < questions.size() -1) {
            contadorPreguntas++;
            setQuestions(questions.get(contadorPreguntas));
        } else {
            timer.cancel();
            mediaPlayer.stop();
            isFinish(false);
        }
    }

    @Override
    public void isFinish(boolean timeOut) {
        ProgressBar progressBar = null;
        TextView textView = null;
        Toast.makeText(this, "Juego acabado", Toast.LENGTH_SHORT).show();
        validator.setTime(iClicks);

        //Nunca se generará un idQuestion de Game inferior a 1, mientras que sea 0 o no haya
        // recibido respuesta
        while (idGame != 0 && !isFinishGameRequest) {
            textView = (TextView)findViewById(R.id.txtInformacionGamming);
            progressBar = (ProgressBar)findViewById(R.id.question_progress);

            textView.setText("Esperando al servidor...");
            textView.setVisibility(View.VISIBLE);
            progressBar.setVisibility(View.VISIBLE);
        }

        if (progressBar != null) {
            textView.setVisibility(View.GONE);
            progressBar.setVisibility(View.GONE);
        }

        validator.setIdGame(idGame);

        Intent it = new Intent(this, Finish_Game.class);
        it.putExtra(Finish_Game.validator, validator);
        if (timeOut)
            it.putExtra(GamingActivity.BACK_ACTIVITY, 1);
        startActivity(it);
    }

    /**
     * Clase para el reloj!
     */
    public class RunClock extends TimerTask {
        private Context c;

        public RunClock(Context c) {
            this.c = c;
        }

        @Override
        public boolean cancel() {
            ((TimerEndGamming) c).isFinish(true);


            return super.cancel();
        }

        @Override
        public void run() {
            runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    // task to be done every 100 milliseconds
                    //TODO error aqui cuando click corre mas rapido.
                    iClicks += 1;
                    if (iClicks > 100)
                        cancel();

                    arcClicks.setProgress(iClicks);
                }
            });


        }
    }

    public class ResponsePreguntas implements Responser<List<Question>> {

        @Override
        public void onFinish(List<Question> obj, String bearer) {
            int idUser = Bearer.getDefaultsInt(Bearer.USER_ID_KEY, GamingActivity.this);
            questions = obj;

            List<IdQuestion> idsQuestions = new ArrayList<>();
            //Solo para API 7.0 obj.forEach(x -> idsQuestions.add(x.getId()));
            for (Question q : questions)
                idsQuestions.add(new IdQuestion(q.getId()));

            retrofitControler = new RetrofitControler();
            Game g = new Game(category.getId(),
                    idUser, 1, idsQuestions);
            String a = new Gson().toJson(g);
            Call<Game> gameCall = retrofitControler.postGame(token, g);
            gameCall.enqueue(new CallBackProgress<Game>(responseGame, GamingActivity.this));

            if (!mediaPlayer.isPlaying())
                mediaPlayer.start();

            setQuestions(questions.get(contadorPreguntas));
        }

        @Override
        public void onFailure(Throwable t) {

        }

    }

    public class ResponseGame implements Responser<Game> {

        @Override
        public void onFinish(Game obj, String bearer) {
            idGame = obj.getIdGame();
            isFinishGameRequest = true;
        }

        @Override
        public void onFailure(Throwable t) {
            isFinishGameRequest = true;

        }
    }
}

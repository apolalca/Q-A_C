package es.iesnervion.qa.ui.View;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Toast;

import java.util.List;

import es.iesnervion.qa.controller.RetrofitControler;
import es.iesnervion.qa.model.Bearer;
import es.iesnervion.qa.model.CallBackProgress;
import es.iesnervion.qa.model.Category;
import es.iesnervion.qa.model.GetImages;
import es.iesnervion.qa.model.Responser;
import es.iesnervion.qa.R;
import es.iesnervion.qa.ui.Adapter.CategoriaAdapter;
import retrofit2.Call;

public class CategoriesActivity extends AppCompatActivity {

    private List<Category> categories;
    private RetrofitControler retrofitControler;
    private RecyclerView mRecyclerView;
    private ResponseCategory responseCategory = new ResponseCategory(this);
    private ResponserBitMapCategory responserBitMapCategory = new ResponserBitMapCategory(this);

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories_game);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Start Flating button...
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setBackgroundTintList(ColorStateList.valueOf(getResources().getColor(R.color.colorQuestions)));
        fab.setOnClickListener(view -> finish());

        mRecyclerView = (RecyclerView)findViewById(R.id.rvCategorias);

        String token = Bearer.getDefaults(Bearer.BEARER_KEY, this);

        retrofitControler = new RetrofitControler();
        Call<List<Category>> categCall = retrofitControler.getListCategory(token);
        categCall.enqueue(new CallBackProgress<>(responseCategory, this));

    }

    public class ResponseCategory implements Responser<List<Category>>{
        private Context c;

        public ResponseCategory(Context c){
            this.c= c;
        }

        @Override
        public void onFinish(List<Category> obj, String bearer) {
            categories = obj;

            for (int i = 0; i < categories.size(); i++)
                new GetImages(categories.get(i).getImg(), responserBitMapCategory ,this.c, i).execute();

        }

        @Override
        public void onFailure(Throwable t) {
            Toast.makeText(c, t.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
    public class ResponserBitMapCategory implements Responser<Bitmap>{
        private Context c;

        public ResponserBitMapCategory(Context c){
            this.c = c;
        }

        @Override
        public void onFinish(Bitmap obj, String bearer) {
            int i = Integer.parseInt(bearer);
            categories.get(i).setImgBitMap(obj);

            (findViewById(R.id.progressBarCategory)).setVisibility(View.GONE);
            (findViewById(R.id.cargando_content_tv)).setVisibility(View.GONE);

            CategoriaAdapter mCategoryAdapter = new CategoriaAdapter(categories, c);
            mRecyclerView.setAdapter(mCategoryAdapter);
            mRecyclerView.setLayoutManager(new LinearLayoutManager(c));
            mRecyclerView.setHasFixedSize(true);
        }

        @Override
        public void onFailure(Throwable t) {

        }
    }

}

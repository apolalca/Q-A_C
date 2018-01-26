package es.iesnervion.qa.model;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

/**
 * Created by adripol94 on 2/7/17.
 */

public class GetImages extends AsyncTask<Void, Void, Void> {
    private String urlImg;
    private Bitmap bitmap;
    private String pos;
    private Responser<Bitmap> r;
    private Context c;

    public GetImages(String url, Responser r, Context c, int pos) {
        this.urlImg = url;
        this.pos = String.valueOf(pos);
        this.c = c;
        this.r = r;
    }

    @Override
    protected Void doInBackground(Void... arg0) {
        URL url = null;
        try {
            InputStream inputStream = new URL(urlImg).openStream();
            bitmap = ((BitmapDrawable) Drawable.createFromStream(inputStream, null)).getBitmap();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        r.onFinish(bitmap, pos);
    }
}


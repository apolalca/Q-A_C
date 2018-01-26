package es.iesnervion.qa.model;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Parcel;
import android.os.Parcelable;
import android.preference.PreferenceManager;

/**
 * Created by adripol94 on 2/4/17.
 */

public class Bearer implements Parcelable {
    public final static String USER_NAME_KEY = "userName";
    public final static String USER_ID_KEY = "idUser";
    public final static String BEARER_KEY = "bearer";
    public final static String PREFENCE_KEY_NAME = "bearerPreference";
    String bearer;

    public Bearer(String Bearer){
        this.bearer = bearer;
    }

    public String getBearer(){
        return this.bearer;
    }

    public static void delDefaults(Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        prefs.edit().clear().commit();
    }

    public static void setDefaults(String key, String value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(key, value);
        editor.commit();
    }

    public static String getDefaults(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getString(key, null);
    }

    public static void setDefaultsInt(String key, int value, Context context) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putInt(key, value);
        editor.commit();
    }

    public static int getDefaultsInt(String key, Context context) {
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(context);
        return preferences.getInt(key, 0);
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.bearer);
    }

    protected Bearer(Parcel in) {
        this.bearer = in.readString();
    }

    public static final Parcelable.Creator<Bearer> CREATOR = new Parcelable.Creator<Bearer>() {
        @Override
        public Bearer createFromParcel(Parcel source) {
            return new Bearer(source);
        }

        @Override
        public Bearer[] newArray(int size) {
            return new Bearer[size];
        }
    };
}

package es.iesnervion.qa.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by adripol94 on 1/27/17.
 */

public class Answer implements Parcelable {
    private int id;
    private String answer;
    private boolean correct;

    public Answer() {
    }

    public Answer(int id, String answer, boolean correct) {
        this.id = id;
        this.answer = answer;
        this.correct = correct;
    }

    public int getId() {
        return id;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean isCorrect() {
        return correct;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.answer);
        dest.writeByte(this.correct ? (byte) 1 : (byte) 0);
    }

    protected Answer(Parcel in) {
        this.id = in.readInt();
        this.answer = in.readString();
        this.correct = in.readByte() != 0;
    }

    public static final Parcelable.Creator<Answer> CREATOR = new Parcelable.Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel source) {
            return new Answer(source);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };
}

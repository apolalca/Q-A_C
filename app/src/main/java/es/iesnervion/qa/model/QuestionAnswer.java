package es.iesnervion.qa.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.SerializedName;

/**
 * Created by adripol94 on 3/7/17.
 */

public class QuestionAnswer implements Parcelable {
    @SerializedName("idQuestion")
    private int idQuestion;
    @SerializedName("idAnswer")
    private int idAnswer;

    public QuestionAnswer() {
    }

    public QuestionAnswer(int idQuestion, int idAnswer) {
        this.idQuestion = idQuestion;
        this.idAnswer = idAnswer;
    }

    public int getIdQuestion() {
        return idQuestion;
    }

    public void setIdQuestion(int idQuestion) {
        this.idQuestion = idQuestion;
    }

    public int getIdAnswer() {
        return idAnswer;
    }

    public void setIdAnswer(int idAnswer) {
        this.idAnswer = idAnswer;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.idQuestion);
        dest.writeInt(this.idAnswer);
    }

    protected QuestionAnswer(Parcel in) {
        this.idQuestion = in.readInt();
        this.idAnswer = in.readInt();
    }

    public static final Parcelable.Creator<QuestionAnswer> CREATOR = new Parcelable.Creator<QuestionAnswer>() {
        @Override
        public QuestionAnswer createFromParcel(Parcel source) {
            return new QuestionAnswer(source);
        }

        @Override
        public QuestionAnswer[] newArray(int size) {
            return new QuestionAnswer[size];
        }
    };
}

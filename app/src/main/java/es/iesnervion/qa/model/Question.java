package es.iesnervion.qa.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Clase Qestion, esta clase se encargará de guardar las preguntas y sus dichas respuestas
 * se utilizaran el posicionamiento como estrategia para conocer la respuesta correecta.
 * Created by apol on 24/01/17.
 */
//TODO Preguntar a Miguel si es mejor con un Objeto de atributo o que herede de este...
public class Question extends Category implements Parcelable {
    private String question;
    private List<Answer> answer;
    public static final String CATEGORY_KEY = "category";

    public Question() {
    }

    public Question(String question, List<Answer> answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public List<Answer> getAnswer() {
        return answer;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
        dest.writeString(this.question);
        dest.writeTypedList(this.answer);
    }

    protected Question(Parcel in) {
        super(in);
        this.question = in.readString();
        this.answer = in.createTypedArrayList(Answer.CREATOR);
    }

    public static final Creator<Question> CREATOR = new Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}

package es.iesnervion.qa.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Object de usuario, necesario para la conexión en la base de datos.
 *
 * Created by adripol94 on 1/27/17.
 */

public class User implements Parcelable {
    private int id;
    private String email;
    private String name;
    private String surname;
    private String age;
    private String user;
    private String password;
    private String token;

    public User() {
    }

    public User(int id, String email, String name, String surname, String age, String user, String password) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.surname = surname;
        this.age = age;
        this.user = user;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    //Parcelable Methods

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.email);
        dest.writeString(this.name);
        dest.writeString(this.surname);
        dest.writeSerializable(this.age);
        dest.writeString(this.user);
        dest.writeString(this.password);
    }

    protected User(Parcel in) {
        this.id = in.readInt();
        this.email = in.readString();
        this.name = in.readString();
        this.surname = in.readString();
        this.age = in.readString();
        this.user = in.readString();
        this.password = in.readString();
    }

    public static final Parcelable.Creator<User> CREATOR = new Parcelable.Creator<User>() {
        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}

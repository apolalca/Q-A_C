package es.iesnervion.qa.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by adripol94 on 1/26/17.
 */

//TODO Preguntar a Miguel si es mejor un atributo preguntas o si una vez seleccionada la name
// hacer un selecet con el nombre de la name o con el id.
public class Category implements Parcelable {
    public static String CATEGORY_KEY = "category";
    private int id;
    private String name;
    private String image;
    private Bitmap imgBitMap;

    public Category() {
    }

    public Category(int id, String name, String image) {
        this.id = id;
        this.name = name;
        this.image = image;
    }

    public Bitmap getImgBitMap() {
        return imgBitMap;
    }

    public void setImgBitMap(Bitmap imgBitMap) {
        this.imgBitMap = imgBitMap;
    }

    public String getImg() {
        return image;
    }

    public void setImg(String img) {
        this.image = img;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.id);
        dest.writeString(this.name);
        dest.writeString(this.image);
        dest.writeParcelable(this.imgBitMap, flags);
    }

    protected Category(Parcel in) {
        this.id = in.readInt();
        this.name = in.readString();
        this.image = in.readString();
        this.imgBitMap = in.readParcelable(Bitmap.class.getClassLoader());
    }

    public static final Creator<Category> CREATOR = new Creator<Category>() {
        @Override
        public Category createFromParcel(Parcel source) {
            return new Category(source);
        }

        @Override
        public Category[] newArray(int size) {
            return new Category[size];
        }
    };
}

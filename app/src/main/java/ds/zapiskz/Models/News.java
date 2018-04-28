package ds.zapiskz.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class News implements Serializable{

    @SerializedName("salons")
    @Expose
    private ArrayList<News> arrayList;

    @SerializedName("id")
    @Expose
    private int id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("type")
    @Expose
    private String type;

    @SerializedName("checkRating")
    @Expose
    private String checkRating;

    @SerializedName("pictureUrl")
    @Expose
    private String pictureUrl;

    public ArrayList<News> getArrayList() {
        return arrayList;
    }

    public void setArrayList(ArrayList<News> arrayList) {
        this.arrayList = arrayList;
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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getCheckRating() {
        return checkRating;
    }

    public void setCheckRating(String checkRating) {
        this.checkRating = checkRating;
    }

    public String getPictureUrl() {
        return pictureUrl;
    }

    public void setPictureUrl(String pictureUrl) {
        this.pictureUrl = pictureUrl;
    }
}
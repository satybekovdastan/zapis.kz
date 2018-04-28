package ds.zapiskz.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;

public class Detail implements Serializable{

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

    @SerializedName("pictures")
    @Expose
    private ArrayList<String> pictures;

    @SerializedName("phoneNumbers")
    @Expose
    private ArrayList<String> phoneNumbers;

    @SerializedName("address")
    @Expose
    private String address;

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


    public void setPictures(ArrayList<String> pictures) {
        this.pictures = pictures;
    }


    public ArrayList<String> getPictures() {
        return pictures;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getAddress() {
        return address;
    }


    public void setPhoneNumbers(ArrayList<String> phoneNumbers) {
        this.phoneNumbers = phoneNumbers;
    }

    public ArrayList<String> getPhoneNumbers() {
        return phoneNumbers;
    }
}
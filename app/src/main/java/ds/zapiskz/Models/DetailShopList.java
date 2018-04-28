package ds.zapiskz.Models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class DetailShopList {
 
    @SerializedName("salon")
    @Expose
    private Detail salon;


    @SerializedName("categories")
    @Expose
    private ArrayList<Categories> categories;


    public void setSalon(Detail salon) {
        this.salon = salon;
    }

    public Detail getContacts() {
        return salon;
    }

    public void setCategories(ArrayList<Categories> categories) {
        this.categories = categories;
    }


    public ArrayList<Categories> getCategories() {
        return categories;
    }

}
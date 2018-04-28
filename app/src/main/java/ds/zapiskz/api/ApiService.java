package ds.zapiskz.api;

import ds.zapiskz.Models.DetailShopList;
import ds.zapiskz.Models.News;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Url;

public interface ApiService {

    @GET("salon/getPopular")
    Call<News> getPopular();

    @GET("salon/getRecommended")
    Call<News> getRecommended();

    @GET("salon/getRecentlyAdded")
    Call<News> getRecentlyAdded();

    @GET
    Call<DetailShopList> profilePicture(@Url String url);

}
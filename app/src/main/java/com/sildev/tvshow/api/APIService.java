package com.sildev.tvshow.api;

import com.sildev.tvshow.models.TVShowDetailResponse;
import com.sildev.tvshow.models.TVShowResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface APIService {

    @GET("api/most-popular")
    Call<TVShowResponse> getMostPopularTVShows(@Query("page") int page);

    @GET("api/show-details")
    Call<TVShowDetailResponse> getTVShowDetail(@Query("q") String q);

    @GET("api/search")
    Call<TVShowResponse> getResultSearchTVShows(@Query("q") String q, @Query("page") int page);

}

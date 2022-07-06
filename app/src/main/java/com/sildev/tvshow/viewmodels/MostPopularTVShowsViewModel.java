package com.sildev.tvshow.viewmodels;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sildev.tvshow.api.APIService;
import com.sildev.tvshow.api.ApiClient;
import com.sildev.tvshow.models.TVShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MostPopularTVShowsViewModel extends ViewModel {
    private MutableLiveData<TVShowResponse> tvShowResponseLiveData;

    public MostPopularTVShowsViewModel(){
        tvShowResponseLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<TVShowResponse> getTvShowResponseLiveData(int page) {
        makeAPI(page);
        return tvShowResponseLiveData;
    }

    private void makeAPI(int page){
        APIService apiService = ApiClient.getRetrofit().create(APIService.class);
        apiService.getMostPopularTVShows(page).enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(Call<TVShowResponse> call, Response<TVShowResponse> response) {
                tvShowResponseLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TVShowResponse> call, Throwable t) {
                tvShowResponseLiveData.setValue(null);
            }
        });

    }
}

package com.sildev.tvshow.viewmodels;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sildev.tvshow.api.APIService;
import com.sildev.tvshow.api.ApiClient;
import com.sildev.tvshow.models.TVShowDetailResponse;
import com.sildev.tvshow.models.TVShowResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SearchViewModel extends ViewModel {
    private MutableLiveData<TVShowResponse> tvShowDetailResponseMutableLiveData;

    public SearchViewModel(){
        tvShowDetailResponseMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<TVShowResponse> getTvShowDetailResponseMutableLiveData(String q){

        APIService apiService = ApiClient.getRetrofit().create(APIService.class);
        apiService.getResultSearchTVShows(q,1).enqueue(new Callback<TVShowResponse>() {
            @Override
            public void onResponse(Call<TVShowResponse> call, Response<TVShowResponse> response) {
                tvShowDetailResponseMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<TVShowResponse> call, Throwable t) {
                tvShowDetailResponseMutableLiveData.setValue(null);
            }
        });

        return tvShowDetailResponseMutableLiveData;
    }
}

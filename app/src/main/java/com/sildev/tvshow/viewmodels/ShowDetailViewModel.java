package com.sildev.tvshow.viewmodels;

import android.app.Application;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.sildev.tvshow.api.APIService;
import com.sildev.tvshow.api.ApiClient;
import com.sildev.tvshow.database.TVShowDatabase;
import com.sildev.tvshow.models.TVShow;
import com.sildev.tvshow.models.TVShowDetailResponse;

import io.reactivex.Completable;
import io.reactivex.Flowable;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ShowDetailViewModel extends AndroidViewModel {
    private MutableLiveData<TVShowDetailResponse> tvShowDetailResponseMutableLiveData;
    private TVShowDatabase tvShowDatabase;

    public ShowDetailViewModel(@NonNull Application application) {
        super(application);

        tvShowDetailResponseMutableLiveData = new MutableLiveData<>();
        tvShowDatabase = TVShowDatabase.getTvShowDatabase(application);
    }

    public MutableLiveData<TVShowDetailResponse> getTvShowDetailResponseMutableLiveData(String q) {
        getTVShowDetail(q);
        return tvShowDetailResponseMutableLiveData;
    }

    private void getTVShowDetail(String q) {
        APIService apiService = ApiClient.getRetrofit().create(APIService.class);
        apiService.getTVShowDetail(q).enqueue(new Callback<TVShowDetailResponse>() {
            @Override
            public void onResponse(Call<TVShowDetailResponse> call, Response<TVShowDetailResponse> response) {
                tvShowDetailResponseMutableLiveData.setValue(response.body());
                Log.e("responseDetail", response.body().getTvShowDetail().getDescription());
            }

            @Override
            public void onFailure(Call<TVShowDetailResponse> call, Throwable t) {
                tvShowDetailResponseMutableLiveData.setValue(null);
            }
        });

    }

    public Completable addToWatchlist(TVShow tvShow) {
        return tvShowDatabase.tvShowDAO().addToWatchlist(tvShow);
    }

    public Flowable<TVShow> getTVShow(String tvShowId) {
        return tvShowDatabase.tvShowDAO().getTVShow(tvShowId);
    }

    public Completable deleteWatchlist(TVShow tvShow) {
        return tvShowDatabase.tvShowDAO().removeFromWatchlist(tvShow);
    }
}

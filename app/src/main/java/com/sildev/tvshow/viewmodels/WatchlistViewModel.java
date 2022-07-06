package com.sildev.tvshow.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;

import com.sildev.tvshow.database.TVShowDatabase;
import com.sildev.tvshow.models.TVShow;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Flowable;

public class WatchlistViewModel extends AndroidViewModel {
    private TVShowDatabase tvShowDatabase;

    public WatchlistViewModel(@NonNull Application application) {
        super(application);
        tvShowDatabase = TVShowDatabase.getTvShowDatabase(application);
    }

    public Flowable<List<TVShow>> loadWatchlist() {

        return tvShowDatabase.tvShowDAO().getWatchlist();
    }

    public Completable deleteWatchlist(TVShow tvShow) {
        return tvShowDatabase.tvShowDAO().removeFromWatchlist(tvShow);
    }

}

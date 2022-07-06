package com.sildev.tvshow.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;


import com.sildev.tvshow.R;
import com.sildev.tvshow.adapters.TVShowsWatchlistAdapter;
import com.sildev.tvshow.databinding.ActivityWatchlistBinding;
import com.sildev.tvshow.models.TVShow;
import com.sildev.tvshow.viewmodels.WatchlistViewModel;

import java.util.ArrayList;

import io.reactivex.Completable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class WatchlistActivity extends AppCompatActivity {

    private ActivityWatchlistBinding binding;
    private WatchlistViewModel watchlistViewModel;
    private TVShowsWatchlistAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_watchlist);

        initial();

    }

    private void initial() {
        watchlistViewModel = new ViewModelProvider(this).get(WatchlistViewModel.class);
        binding.imageBack.setOnClickListener(v -> onBackPressed());

        adapter = new TVShowsWatchlistAdapter(null, this, new TVShowsWatchlistAdapter.IClickListener() {
            @Override
            public void onClickImageDelete(TVShow tvShow) {
                CompositeDisposable compositeDisposable = new CompositeDisposable();
                compositeDisposable.add(watchlistViewModel.deleteWatchlist(tvShow)
                        .subscribeOn(Schedulers.computation())
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe(()->{
                            compositeDisposable.dispose();
                        }));

            }
        });
        binding.watchlistRecyclerView.setAdapter(adapter);
    }

    private void loadWatchlist() {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(watchlistViewModel.loadWatchlist()
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(tvShows -> {
                    adapter.setData(tvShows);
                    compositeDisposable.dispose();
                }));
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadWatchlist();
    }
}
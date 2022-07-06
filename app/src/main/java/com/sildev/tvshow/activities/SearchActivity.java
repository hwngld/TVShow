package com.sildev.tvshow.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;

import com.sildev.tvshow.R;
import com.sildev.tvshow.adapters.TVShowsAdapter;
import com.sildev.tvshow.databinding.ActivitySearchBinding;
import com.sildev.tvshow.models.TVShow;
import com.sildev.tvshow.models.TVShowResponse;
import com.sildev.tvshow.viewmodels.SearchViewModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SearchActivity extends AppCompatActivity {
    private ActivitySearchBinding binding;
    private SearchViewModel searchViewModel;
    private List<TVShow> tvShows;
    private Timer timer;
    private TVShowsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search);
        initial();
    }

    private void initial() {
        tvShows = new ArrayList<>();
        adapter = new TVShowsAdapter(tvShows, this);
        binding.tvShowsResultRecyclerView.setAdapter(adapter);
        binding.imageBack.setOnClickListener(v -> onBackPressed());
        binding.inputSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (TextUtils.isEmpty(s.toString().trim())) {
                    tvShows.clear();
                    adapter.setData(tvShows);
                    binding.tvShowsResultRecyclerView.setVisibility(View.GONE);
                } else {
                    binding.tvShowsResultRecyclerView.setVisibility(View.VISIBLE);
                    searchTVShow(s.toString().trim());
                }



            }
        });


    }

    private void searchTVShow(String key) {
        binding.setIsSearching(true);
        searchViewModel = new ViewModelProvider(this).get(SearchViewModel.class);
        searchViewModel.getTvShowDetailResponseMutableLiveData(key).observe(this, new Observer<TVShowResponse>() {
            @Override
            public void onChanged(TVShowResponse tvShowResponse) {
                adapter.setData(tvShowResponse.getTvShows());
                binding.setIsSearching(false);
            }
        });
    }
}
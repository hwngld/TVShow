package com.sildev.tvshow.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.sildev.tvshow.R;
import com.sildev.tvshow.adapters.EpisodeAdapter;
import com.sildev.tvshow.adapters.ImageSliderAdapter;
import com.sildev.tvshow.databinding.ActivityTvshowDetailBinding;
import com.sildev.tvshow.databinding.LayoutEpisodesBinding;
import com.sildev.tvshow.models.TVShow;
import com.sildev.tvshow.models.TVShowDetail;
import com.sildev.tvshow.models.TVShowDetailResponse;
import com.sildev.tvshow.viewmodels.ShowDetailViewModel;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;

public class TVShowDetailActivity extends AppCompatActivity {
    private ActivityTvshowDetailBinding binding;
    private ShowDetailViewModel showDetailViewModel;
    private BottomSheetDialog bottomSheetDialog;
    private LayoutEpisodesBinding layoutEpisodesBinding;
    private Boolean isInWatchlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_tvshow_detail);
        initData();
    }

    private void initData() {
        showDetailViewModel = new ViewModelProvider(this).get(ShowDetailViewModel.class);
        binding.imageBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        isInWatchlist = false;
        getData();

    }

    private void checkTVShowInWatchlist(String idTVShow) {
        CompositeDisposable compositeDisposable = new CompositeDisposable();
        compositeDisposable.add(showDetailViewModel.getTVShow(idTVShow)
                .subscribeOn(Schedulers.computation())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(tvShow -> {
                    isInWatchlist = true;
                    binding.imageAdd.setImageResource(R.drawable.ic_check);
                    compositeDisposable.dispose();
                })
        );
    }

    private void getData() {
        binding.setIsLoading(true);
        TVShow tvShow = (TVShow) getIntent().getSerializableExtra("tvShow");
        checkTVShowInWatchlist(tvShow.getId() + "");
        binding.setTvShow(tvShow);
        showDetailViewModel.getTvShowDetailResponseMutableLiveData(tvShow.getId() + "").observe(this, new Observer<TVShowDetailResponse>() {
            @Override
            public void onChanged(TVShowDetailResponse tvShowDetailResponse) {
                binding.setIsLoading(false);
                if (tvShowDetailResponse != null) {
                    TVShowDetail tvShowDetail = tvShowDetailResponse.getTvShowDetail();
                    binding.buttonWebsite.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(tvShowDetail.getUrl()));
                            startActivity(intent);
                        }
                    });
                    binding.setTvShowDetail(tvShowDetail);

                    layoutEpisodesBinding = DataBindingUtil.inflate(
                            LayoutInflater.from(TVShowDetailActivity.this),
                            R.layout.layout_episodes,
                            findViewById(R.id.layoutTvShowDetail),
                            false
                    );
                    bottomSheetDialog = new BottomSheetDialog(TVShowDetailActivity.this);
                    bottomSheetDialog.setContentView(layoutEpisodesBinding.getRoot());
                    layoutEpisodesBinding.episodeRecyclerView.setAdapter(new EpisodeAdapter(tvShowDetail.getEpisodes()));
                    layoutEpisodesBinding.textTitle.setText("Episodes  | " + tvShow.getName());
                    layoutEpisodesBinding.imageClose.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bottomSheetDialog.dismiss();
                        }
                    });
                    binding.buttonEpisode.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            bottomSheetDialog.show();
                        }
                    });
                    binding.imageAdd.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            CompositeDisposable compositeDisposable = new CompositeDisposable();
                            if (isInWatchlist) {
                                compositeDisposable.add(showDetailViewModel.deleteWatchlist(tvShow)
                                        .subscribeOn(Schedulers.computation())
                                        .observeOn(AndroidSchedulers.mainThread())
                                        .subscribe(() -> {
                                            binding.imageAdd.setImageResource(R.drawable.ic_add);
                                            isInWatchlist = false;
                                            Toast.makeText(TVShowDetailActivity.this, "Removed to watchlist", Toast.LENGTH_SHORT).show();
                                            compositeDisposable.dispose();
                                        })
                                );
                            } else {
                                compositeDisposable
                                        .add(showDetailViewModel.addToWatchlist(tvShow)
                                                .subscribeOn(Schedulers.io())
                                                .observeOn(AndroidSchedulers.mainThread())
                                                .subscribe(() -> {
                                                    isInWatchlist = true;
                                                    binding.imageAdd.setImageResource(R.drawable.ic_check);
                                                    Toast.makeText(TVShowDetailActivity.this, "Added to watchlist", Toast.LENGTH_SHORT).show();
                                                    compositeDisposable.dispose();
                                                })
                                        );
                            }
                        }
                    });
                    loadImageSlider(tvShowDetail.getPictures());
                }
            }
        });
        binding.textRead.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.textDescription.setMaxLines(Integer.MAX_VALUE);
                binding.textDescription.setEllipsize(null);
                binding.textRead.setVisibility(View.GONE);
            }
        });

    }

    private void loadImageSlider(String[] images) {
        binding.sliderViewPager.setOffscreenPageLimit(1);
        binding.sliderViewPager.setAdapter(new ImageSliderAdapter(images));
        setUpSliderIndicators(images.length);
        binding.sliderViewPager.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                setCurrentSliderIndicators(position);
            }
        });
    }

    private void setUpSliderIndicators(int count) {
        ImageView[] imageViews = new ImageView[count];
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        params.setMargins(8, 0, 8, 0);
        for (int i = 0; i < imageViews.length; i++) {
            imageViews[i] = new ImageView(getApplicationContext());
            imageViews[i].setImageDrawable(getDrawable(R.drawable.background_indicator_inactive));
            imageViews[i].setLayoutParams(params);
            binding.layoutIndicator.addView(imageViews[i]);
        }
        setCurrentSliderIndicators(0);
    }

    private void setCurrentSliderIndicators(int position) {
        int childCount = binding.layoutIndicator.getChildCount();
        for (int i = 0; i < childCount; i++) {
            ImageView imageView = (ImageView) binding.layoutIndicator.getChildAt(i);
            if (i == position) {
                imageView.setImageDrawable(getDrawable(R.drawable.background_indicator_active));
            } else {
                imageView.setImageDrawable(getDrawable(R.drawable.background_indicator_inactive));
            }
        }
    }
}
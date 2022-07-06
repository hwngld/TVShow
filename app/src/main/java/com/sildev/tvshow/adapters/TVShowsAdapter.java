package com.sildev.tvshow.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sildev.tvshow.R;
import com.sildev.tvshow.activities.TVShowDetailActivity;
import com.sildev.tvshow.databinding.ItemTvShowBinding;
import com.sildev.tvshow.models.TVShow;

import java.util.List;

public class TVShowsAdapter extends RecyclerView.Adapter<TVShowsAdapter.TVShowViewHolder> {

    private List<TVShow> tvShows;
    private LayoutInflater layoutInflater;
    private Context context;

    public TVShowsAdapter(List<TVShow> tvShows, Context context){
        this.tvShows = tvShows;
        this.context = context;
    }

    public void setData(List<TVShow> tvShows){
        this.tvShows = tvShows;
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public TVShowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater==null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemTvShowBinding itemTvShowBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_tv_show, parent, false);
        return new TVShowViewHolder(itemTvShowBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowViewHolder holder, int position) {
        holder.bindTVShow(tvShows.get(position));
    }

    @Override
    public int getItemCount() {
        if (tvShows!=null){
            return tvShows.size();
        }
        return 0;
    }


    class TVShowViewHolder extends RecyclerView.ViewHolder{

        private final ItemTvShowBinding itemTvShowBinding;

        public TVShowViewHolder(ItemTvShowBinding itemTvShowBinding){
            super(itemTvShowBinding.getRoot());
            this.itemTvShowBinding = itemTvShowBinding;
        }
        public void bindTVShow(TVShow tvShow){
            itemTvShowBinding.setTvShow(tvShow);
            itemTvShowBinding.executePendingBindings();
            itemTvShowBinding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context.getApplicationContext(), TVShowDetailActivity.class);
                    intent.putExtra("tvShow",tvShow);
                    context.startActivity(intent);
                }
            });
        }
    }
}

package com.sildev.tvshow.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sildev.tvshow.R;
import com.sildev.tvshow.databinding.ItemEpisodeBinding;
import com.sildev.tvshow.models.Episode;

import java.util.List;

public class EpisodeAdapter extends RecyclerView.Adapter<EpisodeAdapter.EpisodeViewHolder> {

    private List<Episode> episodes;
    private LayoutInflater layoutInflater;

    public EpisodeAdapter(List<Episode> episodes){
        this.episodes = episodes;
    }
    @NonNull
    @Override
    public EpisodeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (layoutInflater==null){
            layoutInflater = LayoutInflater.from(parent.getContext());
        }
        ItemEpisodeBinding itemEpisodeBinding = DataBindingUtil.inflate(layoutInflater, R.layout.item_episode, parent,false);
        return new EpisodeViewHolder(itemEpisodeBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull EpisodeViewHolder holder, int position) {
        holder.bindingEpisode(episodes.get(position));
    }

    @Override
    public int getItemCount() {
        if (episodes!=null){
            return episodes.size();
        }
        return 0;
    }

    class EpisodeViewHolder extends RecyclerView.ViewHolder {

        private ItemEpisodeBinding itemEpisodeBinding;

        public EpisodeViewHolder(ItemEpisodeBinding itemEpisodeBinding) {
            super(itemEpisodeBinding.getRoot());
            this.itemEpisodeBinding = itemEpisodeBinding;
        }

        public void bindingEpisode(Episode episode){
            itemEpisodeBinding.setEpisode(episode);
        }
    }
}

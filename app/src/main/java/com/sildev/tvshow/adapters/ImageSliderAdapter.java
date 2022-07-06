package com.sildev.tvshow.adapters;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.sildev.tvshow.R;
import com.sildev.tvshow.databinding.ItemSliderImageBinding;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.ImageSliderViewHolder> {
    private String[] urlImages;
    private LayoutInflater inflater;

    public ImageSliderAdapter(String[] urlImages) {
        this.urlImages = urlImages;
    }

    @NonNull
    @Override
    public ImageSliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (inflater == null) {
            inflater = LayoutInflater.from(parent.getContext());
        }
        ItemSliderImageBinding itemSliderImageBinding = DataBindingUtil.inflate(inflater, R.layout.item_slider_image, parent, false);
        return new ImageSliderViewHolder(itemSliderImageBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ImageSliderViewHolder holder, int position) {
        holder.bindImageSlider(urlImages[position]);
    }

    @Override
    public int getItemCount() {
        if (urlImages != null) {
            return urlImages.length;
        }
        return 0;
    }

    class ImageSliderViewHolder extends RecyclerView.ViewHolder {

        private ItemSliderImageBinding itemSliderImageBinding;

        public ImageSliderViewHolder(ItemSliderImageBinding itemSliderImageBinding) {
            super(itemSliderImageBinding.getRoot());
            this.itemSliderImageBinding = itemSliderImageBinding;
        }

        public void bindImageSlider(String imageURL) {
            itemSliderImageBinding.setImageURL(imageURL);
        }
    }
}

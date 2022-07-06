package com.sildev.tvshow.models;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class TVShowResponse {
    private String total;
    private int page;
    private int pages;
    @SerializedName("tv_shows")
    private List<TVShow> tvShows;

    public TVShowResponse() {
    }

    public TVShowResponse(String total, int page, int pages, List<TVShow> tvShows) {
        this.total = total;
        this.page = page;
        this.pages = pages;
        this.tvShows = tvShows;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getPages() {
        return pages;
    }

    public void setPages(int pages) {
        this.pages = pages;
    }

    public List<TVShow> getTvShows() {
        return tvShows;
    }

    public void setTvShows(List<TVShow> tvShows) {
        this.tvShows = tvShows;
    }
}

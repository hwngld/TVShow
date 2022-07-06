package com.sildev.tvshow.models;

import com.google.gson.annotations.SerializedName;

public class Episode {
    private int season;
    private int episode;
    private String name;
    @SerializedName("air_date")
    private String airDate;

    public Episode() {
    }

    public int getSeason() {
        return season;
    }

    public void setSeason(int season) {
        this.season = season;
    }

    public int getEpisode() {
        return episode;
    }

    public void setEpisode(int episode) {
        this.episode = episode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAirDate() {
        return airDate;
    }

    public void setAirDate(String airDate) {
        this.airDate = airDate;
    }
}

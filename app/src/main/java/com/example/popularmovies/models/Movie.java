package com.example.popularmovies.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Movie implements Serializable {
    private static final String IMAGE_PATH = "https://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE = "w185";

    @SerializedName("original_title")
    private String title;

    @SerializedName("poster_path")
    private String posterPath;

    @SerializedName("overview")
    private String overview;

    @SerializedName("release_date")
    private String releaseDate;

    @SerializedName("vote_average")
    private float voteAverage;

    @SerializedName("genres")
    private List<Genres> genres = null;

    @SerializedName("runtime")
    private String duration;

    public Movie() {
    }

    public Movie(String title, String posterPath, String overview, String releaseDate, float voteAverage, List<Genres> genres, String duration) {
        this.title = title;
        this.posterPath = posterPath;
        this.overview = overview;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.genres = genres;
        this.duration = duration;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return IMAGE_PATH + IMAGE_SIZE + posterPath;
    }

    public String getOverview() {
        return overview;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public float getVoteAverage() {
        return voteAverage;
    }

    public List<Genres> getGenres() {
        return genres;
    }

    public String getDuration() {
        return duration;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setOverview(String overview) {
        this.overview = overview;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setVoteAverage(float voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setGenres(List<Genres> genres) {
        this.genres = genres;
    }

    public void setDuration(String duration) {
        this.duration = duration;
    }
}


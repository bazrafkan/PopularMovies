package com.example.popularmovies.models;

import com.google.gson.annotations.SerializedName;

public class ListMovies {
    private final String IMAGE_PATH = "http://image.tmdb.org/t/p/w185";

    @SerializedName("id")
    private int id;

    @SerializedName("poster_path")
    private String posterPath;

    public ListMovies() {
    }

    public ListMovies(int id, String posterPath) {
        this.id = id;
        this.posterPath = posterPath;
    }

    public int getId() {
        return id;
    }

    public String getPosterPath() {
        return IMAGE_PATH + posterPath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}

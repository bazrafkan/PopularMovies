package com.example.popularmovies.models;

import com.google.gson.annotations.SerializedName;

public class ListMovies {

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
        return posterPath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}

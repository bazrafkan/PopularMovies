package com.example.popularmovies.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ListMovies implements Serializable {
    private static final String IMAGE_PATH = "https://image.tmdb.org/t/p/";
    private static final String IMAGE_SIZE = "w185";

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
        return IMAGE_PATH + IMAGE_SIZE + posterPath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }
}

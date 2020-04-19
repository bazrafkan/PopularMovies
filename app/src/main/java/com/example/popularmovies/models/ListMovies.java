package com.example.popularmovies.models;

import androidx.annotation.Nullable;

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

    public String getOriginalPosterPath() {
        return posterPath;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (obj == null && this == null) {
            return true;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        } else {
            ListMovies method = (ListMovies) obj;
            if (this.id == method.getId()
                    && this.posterPath.equals(method.getOriginalPosterPath())) {
                return true;
            }
            return false;
        }
    }
}

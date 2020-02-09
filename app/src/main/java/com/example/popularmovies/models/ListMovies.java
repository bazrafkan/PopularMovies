package com.example.popularmovies.models;

public class ListMovies {
    private int id;
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

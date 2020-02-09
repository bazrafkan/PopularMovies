package com.example.popularmovies.models;

import java.util.List;

public class Movie {

    private String title;
    private String posterPath;
    private String description;
    private String releaseDate;
    private int voteAverage;
    private List<String> genres = null;

    public Movie() {
    }

    public Movie(String title, String posterPath, String description, String releaseDate, int voteAverage, List<String> genres) {
        this.title = title;
        this.posterPath = posterPath;
        this.description = description;
        this.releaseDate = releaseDate;
        this.voteAverage = voteAverage;
        this.genres = genres;
    }

    public String getTitle() {
        return title;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public String getDescription() {
        return description;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public int getVoteAverage() {
        return voteAverage;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPosterPath(String posterPath) {
        this.posterPath = posterPath;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

    public void setVoteAverage(int voteAverage) {
        this.voteAverage = voteAverage;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }


}

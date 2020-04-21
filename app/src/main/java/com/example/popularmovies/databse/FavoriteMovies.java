package com.example.popularmovies.databse;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.popularmovies.databse.entry.Movie;
import com.example.popularmovies.databse.entry.Genre;
import com.example.popularmovies.databse.entry.Review;
import com.example.popularmovies.databse.entry.Video;

import java.io.Serializable;
import java.util.List;

public class FavoriteMovies implements Serializable {
    @Embedded
    public Movie movie;

    @Relation(parentColumn = "id", entityColumn = "favoriteId", entity = Genre.class)
    public List<Genre> genres;

    @Relation(parentColumn = "id", entityColumn = "favoriteId", entity = Review.class)
    public List<Review> reviews;


    @Relation(parentColumn = "id", entityColumn = "favoriteId", entity = Video.class)
    public List<Video> videos;
}
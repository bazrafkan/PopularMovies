package com.example.popularmovies.databse;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.example.popularmovies.databse.entry.Favorites;
import com.example.popularmovies.databse.entry.Genre;

import java.io.Serializable;
import java.util.List;

public class FavoritesMovies implements Serializable {
    @Embedded
    public Favorites favorites;

    @Relation(parentColumn = "id", entityColumn = "favoritesId", entity = Genre.class)
    public List<Genre> genres;
}
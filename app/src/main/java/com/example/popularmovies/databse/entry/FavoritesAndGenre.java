package com.example.popularmovies.databse.entry;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.io.Serializable;
import java.util.List;

public class FavoritesAndGenre implements Serializable {
    @Embedded
    public Favorites favorites;

    @Relation(parentColumn = "id", entityColumn = "favoritesId", entity = Genre.class)
    public List<Genre> genres;
}

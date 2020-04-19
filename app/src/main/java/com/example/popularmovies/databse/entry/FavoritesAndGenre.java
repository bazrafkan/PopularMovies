package com.example.popularmovies.databse.entry;

import androidx.room.Embedded;
import androidx.room.Relation;

import java.util.List;

public class FavoritesAndGenre {
    @Embedded
    public Favorites favorites;

    @Relation(parentColumn = "id", entityColumn = "favoritesId", entity = Genre.class)
    public List<Genre> genres;
}

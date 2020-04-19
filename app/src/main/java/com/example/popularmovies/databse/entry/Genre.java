package com.example.popularmovies.databse.entry;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.popularmovies.models.Genres;

@Entity(tableName = "genre")
public class Genre {

    @PrimaryKey
    private int id;

    private int favoritesId;

    private String name;

    @Ignore
    public Genre() {
    }

    @Ignore
    public Genre(Genres genres, int favoritesId) {
        this.id = genres.getId();
        this.favoritesId = favoritesId;
        this.name = genres.getName();
    }

    @Ignore
    public Genre(Genres genres) {
        this.id = genres.getId();
        this.name = genres.getName();
    }

    public Genre(int id, int favoritesId, String name) {
        this.id = id;
        this.favoritesId = favoritesId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getFavoritesId() {
        return favoritesId;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFavoritesId(int favoritesId) {
        this.favoritesId = favoritesId;
    }

    public void setName(String name) {
        this.name = name;
    }
}

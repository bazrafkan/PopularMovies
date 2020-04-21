package com.example.popularmovies.databse.entry;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "genre")
public class Genre implements Serializable {

    @PrimaryKey
    @Expose(serialize = false)
    private int id;

    private int favoriteId;

    @SerializedName("name")
    private String name;

    @Ignore
    public Genre() {
    }

    public Genre(int id, int favoriteId, String name) {
        this.id = id;
        this.favoriteId = favoriteId;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getFavoriteId() {
        return favoriteId;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public void setName(String name) {
        this.name = name;
    }
}

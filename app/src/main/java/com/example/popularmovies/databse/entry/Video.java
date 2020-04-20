package com.example.popularmovies.databse.entry;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.example.popularmovies.models.Videos;

import java.io.Serializable;

@Entity(tableName = "video")
public class Video implements Serializable {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private int favoriteId;

    private String key;

    private String name;

    @Ignore
    public Video() {
    }

    @Ignore
    public Video(Videos videos, int favoriteId) {
        this.favoriteId = favoriteId;
        this.key = videos.getKey();
        this.name = videos.getName();
    }

    @Ignore
    public Video(Videos videos) {
        this.key = videos.getKey();
        this.name = videos.getName();
    }

    public Video(int id, int favoriteId, String key, String name) {
        this.id = id;
        this.favoriteId = favoriteId;
        this.key = key;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public int getFavoriteId() {
        return favoriteId;
    }

    public String getKey() {
        return key;
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

    public void setKey(String key) {
        this.key = key;
    }

    public void setName(String name) {
        this.name = name;
    }
}

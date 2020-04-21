package com.example.popularmovies.databse.entry;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "video")
public class Video implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int idPrimaryKey;

    private int favoriteId;

    @SerializedName("key")
    private String key;

    @SerializedName("name")
    private String name;

    @Ignore
    public Video() {
    }

//    @Ignore
//    public Video(Videos videos, int favoriteId) {
//        this.favoriteId = favoriteId;
//        this.key = videos.getKey();
//        this.name = videos.getName();
//    }
//
//    @Ignore
//    public Video(Videos videos) {
//        this.key = videos.getKey();
//        this.name = videos.getName();
//    }

    public Video(int idPrimaryKey, int favoriteId, String key, String name) {
        this.idPrimaryKey = idPrimaryKey;
        this.favoriteId = favoriteId;
        this.key = key;
        this.name = name;
    }

    public int getIdPrimaryKey() {
        return idPrimaryKey;
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

    public void setIdPrimaryKey(int idPrimaryKey) {
        this.idPrimaryKey = idPrimaryKey;
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

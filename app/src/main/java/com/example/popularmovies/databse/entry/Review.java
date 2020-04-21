package com.example.popularmovies.databse.entry;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity(tableName = "review")
public class Review implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int idPrimaryKey;

    private int favoriteId;

    @SerializedName("author")
    private String author;

    @SerializedName("content")
    private String content;

    @Ignore
    public Review() {
    }

    public Review(int favoriteId, int idPrimaryKey, String author, String content) {
        this.idPrimaryKey = idPrimaryKey;
        this.favoriteId = favoriteId;
        this.author = author;
        this.content = content;
    }

    public int getIdPrimaryKey() {
        return idPrimaryKey;
    }

    public int getFavoriteId() {
        return favoriteId;
    }

    public String getAuthor() {
        return author;
    }

    public String getContent() {
        return content;
    }

    public void setIdPrimaryKey(int idPrimaryKey) {
        this.idPrimaryKey = idPrimaryKey;
    }

    public void setFavoriteId(int favoriteId) {
        this.favoriteId = favoriteId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setContent(String content) {
        this.content = content;
    }
}

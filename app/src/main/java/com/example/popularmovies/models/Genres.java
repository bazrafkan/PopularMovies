package com.example.popularmovies.models;

import com.google.gson.annotations.SerializedName;

public class Genres {

    @SerializedName("id")
    private int id;

    @SerializedName("name")
    private String name;

    public Genres() {

    }

    public Genres(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }
}

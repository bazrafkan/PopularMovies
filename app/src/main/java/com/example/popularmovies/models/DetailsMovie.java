package com.example.popularmovies.models;

import com.example.popularmovies.BuildConfig;
import com.example.popularmovies.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class DetailsMovie implements Serializable {
    private final static String TMDB_URL =
            "https://api.themoviedb.org/3/movie/";
    private final static String PARAM_API_KEY = "api_key";

    public static Movie getDetailsMovie(int id) {
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put(PARAM_API_KEY, BuildConfig.tmdb_api_key);
            URL url = NetworkUtils.buildUrl(TMDB_URL + id, params);
            String result = NetworkUtils.getResponseFromHttpUrl(url);
            return parseJSON(result);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Movie parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        Movie movieResponse = gson.fromJson(response, Movie.class);
        return movieResponse;
    }
}

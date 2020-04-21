package com.example.popularmovies.models;

import com.example.popularmovies.BuildConfig;
import com.example.popularmovies.databse.entry.Review;
import com.example.popularmovies.utils.NetworkUtils;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.annotations.SerializedName;

import java.io.IOException;
import java.io.Serializable;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DetailsReviews implements Serializable {
    private final static String TMDB_URL =
            "https://api.themoviedb.org/3/movie/";
    private final static String TMDB_URL_REVIEWS = "/reviews";
    private final static String PARAM_API_KEY = "api_key";

    @SerializedName("results")
    List<Review> listReviews = null;

    public static List<Review> getDetailsReviews(int id) {
        Map<String, String> params = new HashMap<String, String>();
        try {
            params.put(PARAM_API_KEY, BuildConfig.tmdb_api_key);
            URL url = NetworkUtils.buildUrl(TMDB_URL + id + TMDB_URL_REVIEWS, params);
            String result = NetworkUtils.getResponseFromHttpUrl(url);
            return parseJSON(result).listReviews;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static DetailsReviews parseJSON(String response) {
        Gson gson = new GsonBuilder().create();
        DetailsReviews detailsReviewsResponse = gson.fromJson(response, DetailsReviews.class);
        return detailsReviewsResponse;
    }
}

package com.example.popularmovies.tasks;

import android.os.AsyncTask;

import com.example.popularmovies.databse.entry.Review;
import com.example.popularmovies.models.DetailsReviews;

import java.util.List;

public class ListReviewsTask extends AsyncTask<Integer, Void, List<Review>> {
    public interface AsyncReviewTaskResult {
        void onPreExecute();

        void onPostExecuteListReviews(List<Review> result);
    }

    public AsyncReviewTaskResult delegate;

    public ListReviewsTask(AsyncReviewTaskResult delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.delegate.onPreExecute();
    }

    @Override
    protected List<Review> doInBackground(Integer... integers) {
        int id = integers[0];
        List<Review> reviews = DetailsReviews.getDetailsReviews(id);
        if (reviews != null && reviews.size() > 0) {
            for (int i = 0; i < reviews.size(); i++) {
                reviews.get(i).setFavoriteId(id);
            }
        }
        return reviews;
    }


    @Override
    protected void onPostExecute(List<Review> result) {
        this.delegate.onPostExecuteListReviews(result);
    }
}

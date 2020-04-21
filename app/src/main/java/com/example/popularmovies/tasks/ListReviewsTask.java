package com.example.popularmovies.tasks;

import android.os.AsyncTask;

import com.example.popularmovies.databse.entry.Review;
import com.example.popularmovies.models.DetailsReviews;

import java.util.List;

public class ListReviewsTask extends AsyncTask<Integer,Void, List<Review>> {
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
        return DetailsReviews.getDetailsReviews(integers[0]);
    }


    @Override
    protected void onPostExecute(List<Review> result) {
        this.delegate.onPostExecuteListReviews(result);
    }
}

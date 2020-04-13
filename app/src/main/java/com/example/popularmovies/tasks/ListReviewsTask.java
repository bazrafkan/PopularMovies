package com.example.popularmovies.tasks;

import android.os.AsyncTask;

import com.example.popularmovies.models.DetailsReviews;
import com.example.popularmovies.models.Reviews;

import java.util.List;

public class ListReviewsTask extends AsyncTask<Integer,Void, List<Reviews>> {
    public interface AsyncReviewTaskResult {
        void onPreExecute();
        void onPostExecuteListReviews(List<Reviews> result);
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
    protected List<Reviews> doInBackground(Integer... integers) {
        return DetailsReviews.getDetailsReviews(integers[0]);
    }


    @Override
    protected void onPostExecute(List<Reviews> videos) {
        this.delegate.onPostExecuteListReviews(videos);
    }
}

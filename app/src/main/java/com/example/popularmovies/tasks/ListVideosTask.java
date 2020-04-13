package com.example.popularmovies.tasks;

import android.os.AsyncTask;

import com.example.popularmovies.models.DetailsVideos;
import com.example.popularmovies.models.Videos;

import java.util.List;

public class ListVideosTask extends AsyncTask<Integer, Void, List<Videos>> {
    public interface AsyncVideosTaskResult {
        void onPreExecute();
        void onPostExecute(List<Videos> result);
    }

    public AsyncVideosTaskResult delegate;

    public ListVideosTask(AsyncVideosTaskResult delegate) {
        this.delegate = delegate;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.delegate.onPreExecute();
    }

    @Override
    protected List<Videos> doInBackground(Integer... integers) {
        return DetailsVideos.getDetailsVideos(integers[0]);
    }


    @Override
    protected void onPostExecute(List<Videos> videos) {
        this.delegate.onPostExecute(videos);
    }
}

package com.example.popularmovies.tasks;

import android.os.AsyncTask;

import com.example.popularmovies.databse.entry.Video;
import com.example.popularmovies.models.DetailsVideos;

import java.util.List;

public class ListVideosTask extends AsyncTask<Integer, Void, List<Video>> {
    public interface AsyncVideosTaskResult {
        void onPreExecute();
        void onPostExecute(List<Video> result);
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
    protected List<Video> doInBackground(Integer... integers) {
        return DetailsVideos.getDetailsVideos(integers[0]);
    }


    @Override
    protected void onPostExecute(List<Video> videos) {
        this.delegate.onPostExecute(videos);
    }
}

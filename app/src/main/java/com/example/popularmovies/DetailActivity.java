package com.example.popularmovies;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.example.popularmovies.adapters.ListChipAdapter;
import com.example.popularmovies.models.Movie;
import com.example.popularmovies.tasks.MoviesTask;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity implements MoviesTask.AsyncMoviesTaskResult {
    private ProgressBar mLoadingProgressBar;
    private TextView mTitleTextView;
    private ImageView mPosterImageView;
    private TextView mRatedTextView;
    private RatingBar mRatedRatingBar;
    private TextView mReleaseDateTextView;
    private TextView mOverviewTextView;
    private RecyclerView mGenresList;
    private MoviesTask moviesTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mLoadingProgressBar = findViewById(R.id.pb_loading_indicator);
        mTitleTextView = findViewById(R.id.tv_title_movie);
        mPosterImageView = findViewById(R.id.iv_image_detail);
        mRatedTextView = findViewById(R.id.tv_rated_movie);
        mRatedRatingBar = findViewById(R.id.rb_rated_movie);
        mReleaseDateTextView = findViewById(R.id.tv_release_date_movie);
        mOverviewTextView = findViewById(R.id.tv_overview_movie);
        mGenresList = findViewById(R.id.rv_genres_movie);

        Intent intent = getIntent();
        int id = intent.getIntExtra(Intent.EXTRA_TEXT, 0);
        if (id != 0) {
            moviesTask = new MoviesTask(this);
            moviesTask.execute(id);
        }
    }

    @Override
    protected void onDestroy() {
        cancelTask();
        super.onDestroy();
    }

    @Override
    public void onPreExecute() {
        mLoadingProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void onPostExecute(Movie result) {
        mLoadingProgressBar.setVisibility(View.INVISIBLE);
        if (result != null) {
            Picasso.with(DetailActivity.this)
                    .load(result.getPosterPath())
                    .into(mPosterImageView);
            mTitleTextView.setText(result.getTitle());
            float rated = result.getVoteAverage();
            mRatedTextView.setText(String.valueOf(rated));
            mRatedRatingBar.setRating(rated);
            mReleaseDateTextView.setText(result.getReleaseDate());
            mOverviewTextView.setText(result.getOverview());
            LinearLayoutManager layoutManager = new LinearLayoutManager(DetailActivity.this);
            layoutManager.setOrientation(RecyclerView.HORIZONTAL);
            mGenresList.setLayoutManager(layoutManager);
            mGenresList.setHasFixedSize(true);
            ListChipAdapter listGenreAdapter = new ListChipAdapter(result.getGenres());
            mGenresList.setAdapter(listGenreAdapter);
        } else {
            AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this)
                    .setMessage(R.string.can_not_show_details_of_this_movie)
                    .setTitle(R.string.error)
                    .setNegativeButton(android.R.string.no, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void cancelTask() {
        if (moviesTask != null && moviesTask.getStatus() == AsyncTask.Status.RUNNING) {
            moviesTask.cancel(true);
        }
    }
}

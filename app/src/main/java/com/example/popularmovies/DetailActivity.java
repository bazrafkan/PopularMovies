package com.example.popularmovies;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;


import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;


import com.example.popularmovies.models.DetailsMovie;
import com.example.popularmovies.models.Movie;
import com.squareup.picasso.Picasso;

public class DetailActivity extends AppCompatActivity {
    private ProgressBar mLoadingProgressBar;
    private TextView mTitleTextView;
    private ImageView mPosterImageView;
    private TextView mRatedTextView;
    private RatingBar mRatedRatingBar;
    private TextView mReleaseDateTextView;
    private TextView mOverviewTextView;

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

        Intent intent = getIntent();
        int id = intent.getIntExtra(Intent.EXTRA_TEXT, 0);
        if (id != 0) {
            new MoviesTask().execute(id);
        }
    }


    public class MoviesTask extends AsyncTask<Integer, Void, Movie> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgressBar.setVisibility(View.VISIBLE);
        }

        @Override
        protected Movie doInBackground(Integer... integers) {
            return DetailsMovie.getDetailsMovie(integers[0]);
        }


        @Override
        protected void onPostExecute(Movie movie) {
            mLoadingProgressBar.setVisibility(View.INVISIBLE);
            if (movie != null) {
                Picasso.with(DetailActivity.this)
                        .load(movie.getPosterPath())
                        .into(mPosterImageView);
                mTitleTextView.setText(movie.getTitle());
                float rated = movie.getVoteAverage();
                mRatedTextView.setText(String.valueOf(rated));
                mRatedRatingBar.setRating(rated);
                mReleaseDateTextView.setText(movie.getReleaseDate());
                mOverviewTextView.setText(movie.getOverview());
            } else {
                AlertDialog.Builder builder = new AlertDialog.Builder(DetailActivity.this)
                        .setMessage("Can not show details of this movie")
                        .setTitle("Error")
                        .setNegativeButton(android.R.string.no, null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }

    }
}

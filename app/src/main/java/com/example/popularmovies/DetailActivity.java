package com.example.popularmovies;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.popularmovies.adapters.ListChipAdapter;
import com.example.popularmovies.adapters.ListVideosAdapter;
import com.example.popularmovies.models.Movie;
import com.example.popularmovies.models.Videos;
import com.example.popularmovies.tasks.ListVideosTask;
import com.example.popularmovies.tasks.MoviesTask;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailActivity extends AppCompatActivity implements MoviesTask.AsyncMoviesTaskResult,
        ListVideosTask.AsyncVideosTaskResult,
        ListVideosAdapter.ListItemClickListener
{
    private ProgressBar mLoadingProgressBar;
    private TextView mTitleTextView;
    private ImageView mPosterImageView;
    private TextView mRatedTextView;
    private TextView mReleaseDateTextView;
    private TextView mOverviewTextView;
    private RecyclerView mGenresList;
    private MoviesTask moviesTask;
    private ListVideosTask listVideosTask;
    private List<Videos> listVideos;
    private RecyclerView mListVideos;
    private TextView mDurationTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        setTitle("MovieDetail");
        mLoadingProgressBar = findViewById(R.id.pb_loading_indicator);
        mTitleTextView = findViewById(R.id.tv_title_movie);
        mPosterImageView = findViewById(R.id.iv_image_detail);
        mRatedTextView = findViewById(R.id.tv_rated_movie);
        mReleaseDateTextView = findViewById(R.id.tv_release_date_movie);
        mOverviewTextView = findViewById(R.id.tv_overview_movie);
        mGenresList = findViewById(R.id.rv_genres_movie);
        mDurationTextView = findViewById(R.id.tv_duration_movie);
        mListVideos=findViewById(R.id.rv_trailers_movie);

        Intent intent = getIntent();
        int id = intent.getIntExtra(Intent.EXTRA_TEXT, 0);
        if (id != 0) {
            moviesTask = new MoviesTask(this);
            listVideosTask = new ListVideosTask(this);
            listVideosTask.execute(id);
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
    public void onPostExecute(List<Videos> result) {
        if (result != null && result.size() > 0) {
            DetailActivity.this.listVideos = result;
            LinearLayoutManager layoutManager = new LinearLayoutManager(DetailActivity.this);
            mListVideos.setLayoutManager(layoutManager);
            mListVideos.setHasFixedSize(true);
            ListVideosAdapter adapter = new ListVideosAdapter(result, DetailActivity.this);
            mListVideos.setAdapter(adapter);
        }
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
            mRatedTextView.setText(String.valueOf(rated) + "/10");
            mReleaseDateTextView.setText(result.getReleaseDate().split("-")[0]);
            mOverviewTextView.setText(result.getOverview());
            mDurationTextView.setText(result.getDuration() + "min");
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

    @Override
    public void onListItemClick(int id) {

    }
}

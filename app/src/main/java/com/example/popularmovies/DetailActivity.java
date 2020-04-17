package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.popularmovies.adapters.ListChipAdapter;
import com.example.popularmovies.adapters.ListReviewsAdapter;
import com.example.popularmovies.adapters.ListVideosAdapter;
import com.example.popularmovies.models.Movie;
import com.example.popularmovies.models.Reviews;
import com.example.popularmovies.models.Videos;
import com.example.popularmovies.tasks.ListReviewsTask;
import com.example.popularmovies.tasks.ListVideosTask;
import com.example.popularmovies.tasks.MoviesTask;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements MoviesTask.AsyncMoviesTaskResult,
        ListVideosTask.AsyncVideosTaskResult,
        ListVideosAdapter.ListItemClickListener,
        ListReviewsTask.AsyncReviewTaskResult {
    private static final String MOVIE_KEY = "movie_details";
    private static final String LIST_VIDEOS_KEY = "listVideos";
    private static final String LIST_REVIEWS_KEY = "listReviews";
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
    private ListReviewsTask listReviewsTask;
    private RecyclerView mListReviews;
    private Movie movie;
    private List<Reviews> listReviews;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        mLoadingProgressBar = findViewById(R.id.pb_loading_indicator);
        mTitleTextView = findViewById(R.id.tv_title_movie);
        mPosterImageView = findViewById(R.id.iv_image_detail);
        mRatedTextView = findViewById(R.id.tv_rated_movie);
        mReleaseDateTextView = findViewById(R.id.tv_release_date_movie);
        mOverviewTextView = findViewById(R.id.tv_overview_movie);
        mGenresList = findViewById(R.id.rv_genres_movie);
        mDurationTextView = findViewById(R.id.tv_duration_movie);
        mListVideos = findViewById(R.id.rv_trailers_movie);
        mListReviews = findViewById(R.id.rv_reviews_movie);

        Intent intent = getIntent();
        int id = intent.getIntExtra(Intent.EXTRA_TEXT, 0);
        if (id != 0) {
            if (savedInstanceState != null) {
                checkMovie(savedInstanceState, id);
                checkVideos(savedInstanceState, id);
                checkReviews(savedInstanceState, id);
            } else {
                getMovieDetails(id);
            }
        }
    }

    private void checkMovie(Bundle savedInstanceState, int id) {
        if (savedInstanceState.containsKey(MOVIE_KEY)) {
            Serializable serializable = savedInstanceState.getSerializable(MOVIE_KEY);
            try {
                if (serializable != null) {
                    movie = (Movie) serializable;
                    showMovie();
                } else {
                    getMovieDetails(id);
                }
            } catch (Exception e) {
                getMovieDetails(id);
            }
        } else {
            getMovieDetails(id);
        }
    }

    private void checkVideos(Bundle savedInstanceState, int id) {
        if (savedInstanceState.containsKey(LIST_VIDEOS_KEY)) {
            Serializable serializable = savedInstanceState.getSerializable(LIST_VIDEOS_KEY);
            ArrayList<Videos> list = new ArrayList<>();
            try {
                ArrayList tempList = (ArrayList) serializable;
                if (tempList != null && tempList.size() > 0) {
                    for (int i = 0; i < tempList.size(); i++) {
                        list.add(i, (Videos) tempList.get(i));
                    }
                    listVideos = new ArrayList<>(list);
                    showListVideos();
                }
            } catch (Exception e) {
                getMovieDetails(id);
            }
        }
    }

    private void checkReviews(Bundle savedInstanceState, int id) {
        if (savedInstanceState.containsKey(LIST_REVIEWS_KEY)) {
            Serializable serializable = savedInstanceState.getSerializable(LIST_REVIEWS_KEY);
            ArrayList<Reviews> list = new ArrayList<>();
            try {
                ArrayList tempList = (ArrayList) serializable;
                if (tempList != null && tempList.size() > 0) {
                    for (int i = 0; i < tempList.size(); i++) {
                        list.add(i, (Reviews) tempList.get(i));
                    }
                    listReviews = new ArrayList<>(list);
                    showListReviews();
                }
            } catch (Exception e) {
                getMovieDetails(id);
            }
        }
    }

    private void getMovieDetails(int id) {
        moviesTask = new MoviesTask(this);
        listVideosTask = new ListVideosTask(this);
        listReviewsTask = new ListReviewsTask(this);
        listVideosTask.execute(id);
        listReviewsTask.execute(id);
        moviesTask.execute(id);
    }

    private void showMovie() {
        Picasso.with(DetailActivity.this)
                .load(movie.getPosterPath())
                .into(mPosterImageView);
        mTitleTextView.setText(movie.getTitle());
        float rated = movie.getVoteAverage();
        mRatedTextView.setText(String.valueOf(rated) + "/10");
        mReleaseDateTextView.setText(movie.getReleaseDate().split("-")[0]);
        mOverviewTextView.setText(movie.getOverview());
        mDurationTextView.setText(movie.getDuration() + "min");
        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailActivity.this);
        layoutManager.setOrientation(RecyclerView.HORIZONTAL);
        mGenresList.setLayoutManager(layoutManager);
        mGenresList.setHasFixedSize(true);
        ListChipAdapter listGenreAdapter = new ListChipAdapter(movie.getGenres());
        mGenresList.setAdapter(listGenreAdapter);
    }

    private void showListVideos() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailActivity.this);
        mListVideos.setLayoutManager(layoutManager);
        mListVideos.setHasFixedSize(true);
        ListVideosAdapter adapter = new ListVideosAdapter(listVideos, DetailActivity.this);
        mListVideos.setAdapter(adapter);
    }

    private void showListReviews() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(DetailActivity.this);
        mListReviews.setLayoutManager(layoutManager);
        mListReviews.setHasFixedSize(true);
        ListReviewsAdapter adapter = new ListReviewsAdapter(listReviews);
        mListReviews.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (listVideos != null && listVideos.size() > 0) {
            ArrayList<Videos> arrayList = new ArrayList<>(listVideos);
            outState.putSerializable(LIST_VIDEOS_KEY, arrayList);
        }
        if (listReviews != null && listReviews.size() > 0) {
            ArrayList<Reviews> arrayList = new ArrayList<>(listReviews);
            outState.putSerializable(LIST_REVIEWS_KEY, arrayList);
        }
        if (movie != null) {
            outState.putSerializable(MOVIE_KEY, movie);
        }
        super.onSaveInstanceState(outState);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.settings, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                return true;
        }
        return super.onOptionsItemSelected(item);
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
    public void onPostExecuteListReviews(List<Reviews> result) {
        if (result != null && result.size() > 0) {
            this.listReviews = result;
            showListReviews();
        }
    }

    @Override
    public void onPostExecute(List<Videos> result) {
        if (result != null && result.size() > 0) {
            DetailActivity.this.listVideos = result;
            showListVideos();
        }
    }

    @Override
    public void onPostExecute(Movie result) {
        mLoadingProgressBar.setVisibility(View.INVISIBLE);
        if (result != null) {
            this.movie = result;
            showMovie();
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
        String youtubeId = listVideos.get(id).getKey();
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtubeId));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/watch?v=" + youtubeId));
        try {
            // Try in Youtube app
            this.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            // Try in Youtube web site
            this.startActivity(webIntent);
        }
    }
}

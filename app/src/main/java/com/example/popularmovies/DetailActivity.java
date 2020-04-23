package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.popularmovies.adapters.ListGenreAdapter;
import com.example.popularmovies.adapters.ListReviewsAdapter;
import com.example.popularmovies.adapters.ListVideosAdapter;
import com.example.popularmovies.databse.FavoriteMovies;
import com.example.popularmovies.databse.entry.Movie;
import com.example.popularmovies.databse.entry.Review;
import com.example.popularmovies.databse.entry.Video;
import com.example.popularmovies.tasks.FavoriteMoviesTask;
import com.example.popularmovies.tasks.FavoriteTask;
import com.example.popularmovies.tasks.ListReviewsTask;
import com.example.popularmovies.tasks.ListVideosTask;
import com.example.popularmovies.tasks.MoviesTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.Calendar;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements MoviesTask.AsyncMoviesTaskResult,
        ListVideosTask.AsyncVideosTaskResult,
        ListVideosAdapter.ListItemClickListener,
        ListReviewsTask.AsyncReviewTaskResult {
    private static final String MOVIE_KEY = "selected_movie";
    private static final String FAVORITES_KEY = "favorites_text";

    private ProgressBar mLoadingProgressBar;
    private TextView mTitleTextView;
    private ImageView mPosterImageView;
    private TextView mRatedTextView;
    private TextView mReleaseDateTextView;
    private TextView mOverviewTextView;
    private RecyclerView mGenresList;
    private RecyclerView mListVideos;
    private TextView mDurationTextView;
    private RecyclerView mListReviews;
    private Button mFavoriteButton;
    private String favoritesButtonText;

    private MoviesTask moviesTask;
    private ListVideosTask listVideosTask;
    private ListReviewsTask listReviewsTask;

    private FavoriteTask getFavoriteTask;
    private FavoriteMoviesTask insertFavoriteMoviesTask;
    private FavoriteMoviesTask deleteFavoriteMoviesTask;

    private FavoriteMovies selectedMovie = new FavoriteMovies();

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
        mFavoriteButton = findViewById(R.id.bn_change_favorite_movie);

        LinearLayoutManager layoutManagerGenres = new LinearLayoutManager(DetailActivity.this);
        layoutManagerGenres.setOrientation(RecyclerView.HORIZONTAL);
        mGenresList.setLayoutManager(layoutManagerGenres);
        mGenresList.setHasFixedSize(true);

        LinearLayoutManager layoutManagerVideos = new LinearLayoutManager(DetailActivity.this);
        mListVideos.setLayoutManager(layoutManagerVideos);
        mListVideos.setHasFixedSize(true);

        LinearLayoutManager layoutManagerReviews = new LinearLayoutManager(DetailActivity.this);
        mListReviews.setLayoutManager(layoutManagerReviews);
        mListReviews.setHasFixedSize(true);

        Intent intent = getIntent();
        Serializable serializable = intent.getSerializableExtra(MainActivity.FAVORITES_EXTRA_KEY);
        if (serializable != null) {
            try {
                selectedMovie = (FavoriteMovies) serializable;
                mFavoriteButton.setText(getString(R.string.mark_as_un_favorite));
                favoritesButtonText = mFavoriteButton.getText().toString();
                showMovie();
                showListReviews();
                showListVideos();
            } catch (Exception e) {
                getInitMovieDetails(savedInstanceState, intent);
            }
        } else {
            getInitMovieDetails(savedInstanceState, intent);
        }
    }

    private void getInitMovieDetails(Bundle savedInstanceState, Intent intent) {
        final int id = intent.getIntExtra(Intent.EXTRA_TEXT, 0);
        if (id != 0) {
            mFavoriteButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    changeFavoritesList(id);
                }
            });

            if (savedInstanceState != null) {
                checkMovie(savedInstanceState, id);
                checkFavorites(savedInstanceState, id);
            } else {
                getFavorites(id);
                getMovieDetails(id);
            }
        }

    }

    private void changeFavoritesList(int id) {
        if (selectedMovie.movie != null && id != 0) {
            selectedMovie.genres = selectedMovie.movie.getGenres();
            if (mFavoriteButton.getText().equals(getString(R.string.mark_as_favorite))) {
                if (selectedMovie.movie.getImage() == null || selectedMovie.movie.getImage().length == 0) {
                    Drawable d = mPosterImageView.getDrawable();
                    Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
                    ByteArrayOutputStream stream = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                    selectedMovie.movie.setImage(stream.toByteArray());
                }
                selectedMovie.movie.setUpdateAt(Calendar.getInstance().getTime());
                insertFavoriteMoviesTask = new FavoriteMoviesTask(
                        new FavoriteMoviesTask.AsyncFavoritesAndGenreTaskResult() {
                            @Override
                            public void onPreExecute() {
                                mFavoriteButton.setText(getString(R.string.please_wait));
                            }

                            @Override
                            public void onPostExecute(FavoriteMovies result) {
                                mFavoriteButton.setText(getString(R.string.mark_as_un_favorite));
                                favoritesButtonText = mFavoriteButton.getText().toString();
                            }
                        },
                        getApplicationContext(),
                        FavoriteMoviesTask.INSERT_ACTION
                );
                insertFavoriteMoviesTask.execute(selectedMovie);
            } else if (mFavoriteButton.getText().equals(getString(R.string.mark_as_un_favorite))) {
                deleteFavoriteMoviesTask = new FavoriteMoviesTask(
                        new FavoriteMoviesTask.AsyncFavoritesAndGenreTaskResult() {
                            @Override
                            public void onPreExecute() {
                                mFavoriteButton.setText(getString(R.string.please_wait));
                            }

                            @Override
                            public void onPostExecute(FavoriteMovies result) {
                                mFavoriteButton.setText(getString(R.string.mark_as_favorite));
                                favoritesButtonText = mFavoriteButton.getText().toString();
                            }
                        },
                        getApplicationContext(),
                        FavoriteMoviesTask.DELETE_ACTION
                );
                deleteFavoriteMoviesTask.execute(selectedMovie);
            }
        }
    }

    private void checkFavorites(Bundle savedInstanceState, int id) {
        if (savedInstanceState.containsKey(FAVORITES_KEY)) {
            String title = savedInstanceState.getString(FAVORITES_KEY);
            try {
                if (title != null && title != "") {
                    mFavoriteButton.setText(title);
                    favoritesButtonText = title;
                } else {
                    getFavorites(id);
                }
            } catch (Exception e) {
                getFavorites(id);
            }
        } else {
            getFavorites(id);
        }
    }

    private void checkMovie(Bundle savedInstanceState, int id) {
        if (savedInstanceState.containsKey(MOVIE_KEY)) {
            Serializable serializable = savedInstanceState.getSerializable(MOVIE_KEY);
            try {
                if (serializable != null) {
                    this.selectedMovie = (FavoriteMovies) serializable;
                    showMovie();
                    showListVideos();
                    showListReviews();
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

    private void getFavorites(int id) {
        Movie item = new Movie();
        item.setId(id);
        getFavoriteTask = new FavoriteTask(new FavoriteTask.AsyncListMoviesResult() {
            @Override
            public void onPreExecute() {
                mFavoriteButton.setText(getString(R.string.please_wait));
            }

            @Override
            public void onPostExecute(Movie result) {
                if (result != null) {
                    mFavoriteButton.setText(getString(R.string.mark_as_un_favorite));
                } else {
                    mFavoriteButton.setText(getString(R.string.mark_as_favorite));
                }
                favoritesButtonText = mFavoriteButton.getText().toString();
            }
        }, getApplicationContext());
        getFavoriteTask.execute(item);
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
        if (selectedMovie != null && selectedMovie.movie != null) {
            if (selectedMovie.movie.getImage() != null && selectedMovie.movie.getImage().length > 0) {
                Bitmap bmp = BitmapFactory.decodeByteArray(selectedMovie.movie.getImage(),
                        0,
                        selectedMovie.movie.getImage().length);
                mPosterImageView.setImageBitmap(bmp);
            } else {
                Picasso.with(DetailActivity.this)
                        .load(selectedMovie.movie.getPosterPathCompleted())
                        .into(mPosterImageView);
            }
            mTitleTextView.setText(selectedMovie.movie.getTitle());
            float rated = selectedMovie.movie.getVoteAverage();
            mRatedTextView.setText(String.valueOf(rated) + "/10");
            mReleaseDateTextView.setText(selectedMovie.movie.getReleaseDate().split("-")[0]);
            mOverviewTextView.setText(selectedMovie.movie.getOverview());
            mDurationTextView.setText(selectedMovie.movie.getDuration() + "min");
            ListGenreAdapter listGenreAdapter = new ListGenreAdapter(selectedMovie.genres);
            mGenresList.setAdapter(listGenreAdapter);
        }
    }

    private void showListVideos() {
        ListVideosAdapter adapter = new ListVideosAdapter(selectedMovie.videos, DetailActivity.this);
        mListVideos.setAdapter(adapter);
    }

    private void showListReviews() {
        ListReviewsAdapter adapter = new ListReviewsAdapter(selectedMovie.reviews);
        mListReviews.setAdapter(adapter);
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (selectedMovie != null && selectedMovie.movie != null) {
            if (selectedMovie.movie.getImage() == null || selectedMovie.movie.getImage().length == 0) {
                Drawable d = mPosterImageView.getDrawable();
                Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
                selectedMovie.movie.setImage(stream.toByteArray());
            }
            outState.putSerializable(MOVIE_KEY, selectedMovie);
        }
        if (favoritesButtonText != null && favoritesButtonText != "") {
            outState.putString(FAVORITES_KEY, favoritesButtonText);
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
    public void onPostExecuteListReviews(List<Review> result) {
        if (result != null && result.size() > 0) {
            this.selectedMovie.reviews = result;
            showListReviews();
        }
    }

    @Override
    public void onPostExecute(List<Video> result) {
        if (result != null && result.size() > 0) {
            this.selectedMovie.videos = result;
            showListVideos();
        }
    }

    @Override
    public void onPostExecute(Movie result) {
        mLoadingProgressBar.setVisibility(View.INVISIBLE);
        if (result != null) {
            this.selectedMovie.movie = result;
            this.selectedMovie.genres = result.getGenres();
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
        String youtubeId = selectedMovie.videos.get(id).getKey();
        Intent appIntent = new Intent(Intent.ACTION_VIEW, Uri.parse("vnd.youtube:" + youtubeId));
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("https://www.youtube.com/watch?v=" + youtubeId));
        try {
            this.startActivity(appIntent);
        } catch (ActivityNotFoundException ex) {
            this.startActivity(webIntent);
        }
    }
}

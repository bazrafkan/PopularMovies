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

import com.example.popularmovies.adapters.ListChipAdapter;
import com.example.popularmovies.adapters.ListReviewsAdapter;
import com.example.popularmovies.adapters.ListVideosAdapter;
import com.example.popularmovies.databse.FavoriteMovies;
import com.example.popularmovies.databse.entry.Favorite;
import com.example.popularmovies.databse.entry.Genre;
import com.example.popularmovies.databse.entry.Review;
import com.example.popularmovies.databse.entry.Video;
import com.example.popularmovies.models.Genres;
import com.example.popularmovies.models.Movie;
import com.example.popularmovies.models.Reviews;
import com.example.popularmovies.models.Videos;
import com.example.popularmovies.tasks.FavoriteMoviesTask;
import com.example.popularmovies.tasks.FavoriteTask;
import com.example.popularmovies.tasks.ListReviewsTask;
import com.example.popularmovies.tasks.ListVideosTask;
import com.example.popularmovies.tasks.MoviesTask;
import com.squareup.picasso.Picasso;

import java.io.ByteArrayOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class DetailActivity extends AppCompatActivity implements MoviesTask.AsyncMoviesTaskResult,
        ListVideosTask.AsyncVideosTaskResult,
        ListVideosAdapter.ListItemClickListener,
        ListReviewsTask.AsyncReviewTaskResult {
    private static final String MOVIE_KEY = "movie_details";
    private static final String FAVORITES_MOVIE_KEY = "favorites_movie_details";
    private static final String LIST_VIDEOS_KEY = "listVideos";
    private static final String LIST_REVIEWS_KEY = "listReviews";
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
    private List<Videos> listVideos;
    private ListReviewsTask listReviewsTask;

    private Movie movie;
    private List<Reviews> listReviews;

    private FavoriteTask getFavoriteTask;

    private FavoriteMoviesTask insertFavoriteMoviesTask;
    private FavoriteMoviesTask deleteFavoriteMoviesTask;
    private FavoriteMovies favoriteMovie;

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
                favoriteMovie = (FavoriteMovies) serializable;
                showMovie();
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
                checkVideos(savedInstanceState, id);
                checkReviews(savedInstanceState, id);
                checkFavorites(savedInstanceState, id);
            } else {
                getFavorites(id);
                getMovieDetails(id);
            }
        }

    }

    private void changeFavoritesList(int id) {
        if (movie != null && id != 0) {
            Drawable d = mPosterImageView.getDrawable();
            Bitmap bitmap = ((BitmapDrawable) d).getBitmap();
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream);
            List<Genre> genres = new ArrayList<>();
            for (Genres item : movie.getGenres()) {
                genres.add(new Genre(item, id));
            }
            List<Review> reviews = new ArrayList<>();
            for (Reviews item : listReviews) {
                reviews.add(new Review(item, id));
            }
            List<Video> videos = new ArrayList<>();
            for (Videos item : listVideos) {
                videos.add(new Video(item, id));
            }
            Favorite favorite = new Favorite(
                    id,
                    movie.getTitle(),
                    movie.getOriginalPosterPath(),
                    movie.getOverview(),
                    movie.getReleaseDate(),
                    movie.getVoteAverage(),
                    movie.getDuration(),
                    stream.toByteArray(),
                    Calendar.getInstance().getTime());
            FavoriteMovies favoriteMovies = new FavoriteMovies();
            favoriteMovies.favorite = favorite;
            favoriteMovies.genres = genres;
            favoriteMovies.reviews = reviews;
            favoriteMovies.videos = videos;
            if (mFavoriteButton.getText().equals(getString(R.string.mark_as_favorite))) {
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
                insertFavoriteMoviesTask.execute(favoriteMovies);
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
                deleteFavoriteMoviesTask.execute(favoriteMovies);
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
        if (savedInstanceState.containsKey(FAVORITES_MOVIE_KEY)) {
            Serializable serializable = savedInstanceState.getSerializable(FAVORITES_MOVIE_KEY);
            try {
                if (serializable != null) {
                    favoriteMovie = (FavoriteMovies) serializable;
                    showMovie();
                } else {
                    getMovieDetails(id);
                }
            } catch (Exception e) {
                getMovieDetails(id);
            }
        } else {
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

    private void getFavorites(int id) {
        Favorite item = new Favorite();
        item.setId(id);
        getFavoriteTask = new FavoriteTask(new FavoriteTask.AsyncListMoviesResult() {
            @Override
            public void onPreExecute() {
                mFavoriteButton.setText(getString(R.string.please_wait));
            }

            @Override
            public void onPostExecute(Favorite result) {
                if (result != null) {
                    mFavoriteButton.setText(getString(R.string.mark_as_un_favorite));
                } else {
                    mFavoriteButton.setText(getString(R.string.mark_as_favorite));
                }
                favoritesButtonText = mFavoriteButton.getText().toString();
            }
        }, getApplicationContext(), FavoriteTask.GET_ACTION);
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
        if (favoriteMovie != null && favoriteMovie.favorite != null) {
            Bitmap bmp = BitmapFactory.decodeByteArray(favoriteMovie.favorite.getImage(),
                    0,
                    favoriteMovie.favorite.getImage().length);
            mPosterImageView.setImageBitmap(bmp);
            mTitleTextView.setText(favoriteMovie.favorite.getTitle());
            float rated = favoriteMovie.favorite.getVoteAverage();
            mRatedTextView.setText(String.valueOf(rated) + "/10");
            mReleaseDateTextView.setText(favoriteMovie.favorite.getReleaseDate().split("-")[0]);
            mOverviewTextView.setText(favoriteMovie.favorite.getOverview());
            mDurationTextView.setText(favoriteMovie.favorite.getDuration() + "min");
            List<Genres> genres = new ArrayList<>();
            for (Genre item : favoriteMovie.genres) {
                genres.add(new Genres(item.getId(), item.getName()));
            }
            ListChipAdapter listGenreAdapter = new ListChipAdapter(genres);
            mGenresList.setAdapter(listGenreAdapter);
        } else if (movie != null) {
            Picasso.with(DetailActivity.this)
                    .load(movie.getPosterPath())
                    .into(mPosterImageView);
            mTitleTextView.setText(movie.getTitle());
            float rated = movie.getVoteAverage();
            mRatedTextView.setText(String.valueOf(rated) + "/10");
            mReleaseDateTextView.setText(movie.getReleaseDate().split("-")[0]);
            mOverviewTextView.setText(movie.getOverview());
            mDurationTextView.setText(movie.getDuration() + "min");
            ListChipAdapter listGenreAdapter = new ListChipAdapter(movie.getGenres());
            mGenresList.setAdapter(listGenreAdapter);
        } else {
            //TODO show empty
        }

    }

    private void showListVideos() {
        ListVideosAdapter adapter = new ListVideosAdapter(listVideos, DetailActivity.this);
        mListVideos.setAdapter(adapter);
    }

    private void showListReviews() {
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
        if (favoriteMovie != null && favoriteMovie.favorite != null) {
            outState.putSerializable(FAVORITES_MOVIE_KEY, favoriteMovie);
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

package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.preference.PreferenceManager;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.popularmovies.adapters.ListMoviesAdapter;
import com.example.popularmovies.databse.AppDatabase;
import com.example.popularmovies.databse.FavoriteMovies;
import com.example.popularmovies.models.SortedMovies;
import com.example.popularmovies.tasks.ListMoviesTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        ListMoviesAdapter.ListItemClickListener,
        ListMoviesTask.AsyncListMoviesResult {
    private static final String LIST_MOVIES_KEY = "listMovies";
    private static final String FILTER_LIST_MOVIES_KEY = "filter_listMovies";
    public static final String FAVORITES_EXTRA_KEY = "favorites_extra_key";
    private TextView mEmptyMoviesTextView;
    private ProgressBar mLoadingProgressBar;
    private RecyclerView mListMovies;
    private List<FavoriteMovies> listMovies;
    private ListMoviesTask listMoviesTask;
    private String filterListMovies;
    private AppDatabase mAppDatabase;
    private List<FavoriteMovies> listFavoriteMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEmptyMoviesTextView = findViewById(R.id.tv_empty_message);
        mLoadingProgressBar = findViewById(R.id.pb_loading_indicator);
        mListMovies = findViewById(R.id.rv_list_movies);
        mAppDatabase = AppDatabase.getInstance(getApplicationContext());
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
        mListMovies.setLayoutManager(layoutManager);
        mListMovies.setHasFixedSize(true);

        if (savedInstanceState != null) {
            checkListMovies(savedInstanceState);
            checkFilter(savedInstanceState);
        } else {
            getListVideos();
        }
        initDatabase();
    }

    private void checkListMovies(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(LIST_MOVIES_KEY)) {
            Serializable serializable = savedInstanceState.getSerializable(LIST_MOVIES_KEY);
            ArrayList<FavoriteMovies> list = new ArrayList<>();
            try {
                ArrayList tempList = (ArrayList) serializable;
                if (tempList != null && tempList.size() > 0) {
                    for (int i = 0; i < tempList.size(); i++) {
                        list.add(i, (FavoriteMovies) tempList.get(i));
                    }
                    listMovies = new ArrayList<>(list);
                    showListMovies();
                } else {
                    getListVideos();
                }
            } catch (Exception e) {
                getListVideos();
            }
        } else {
            getListVideos();
        }
    }

    private void checkFilter(Bundle savedInstanceState) {
        if (savedInstanceState.containsKey(FILTER_LIST_MOVIES_KEY)) {
            String filter = savedInstanceState.getString(FILTER_LIST_MOVIES_KEY);
            filterListMovies = filter;
            if (filter != null) {
                if (filter.equals(getString(R.string.most_popular_value))) {
                    setTitle(getString(R.string.pop_movies));
                }
                if (filter.equals(getString(R.string.top_rate_value))) {
                    setTitle(getString(R.string.top_movies));
                }
                if (filter.equals(getString(R.string.favorite_value))) {
                    setTitle(getString(R.string.favorite_movies));
                }
            }
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (listMovies != null && listMovies.size() > 0) {
            ArrayList<FavoriteMovies> arrayList = new ArrayList<>(listMovies);
            outState.putSerializable(LIST_MOVIES_KEY, arrayList);
        }
        outState.putString(FILTER_LIST_MOVIES_KEY, filterListMovies);
        super.onSaveInstanceState(outState);
    }

    private void getListVideos() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String filter = sharedPreferences.getString(getString(R.string.pref_filter_key),
                getString(R.string.most_popular_value));
        filterListMovies = filter;
        if (filter.equals(getString(R.string.most_popular_value))) {
            setTitle(getString(R.string.pop_movies));
            makeListMovies(SortedMovies.Popular);
        }
        if (filter.equals(getString(R.string.top_rate_value))) {
            setTitle(getString(R.string.top_movies));
            makeListMovies(SortedMovies.TopRated);
        }
        if (filter.equals(getString(R.string.favorite_value))) {
            setTitle(getString(R.string.favorite_movies));
            makeListMovies(SortedMovies.Favorite);
        }
    }

    private void showListMovies() {
        mEmptyMoviesTextView.setVisibility(View.INVISIBLE);
        ListMoviesAdapter listMoviesAdapter = new ListMoviesAdapter(listMovies, MainActivity.this);
        mListMovies.setAdapter(listMoviesAdapter);
    }

    private void showEmptyListMovies() {
        ListMoviesAdapter listMoviesAdapter = new ListMoviesAdapter(null, MainActivity.this);
        mListMovies.setAdapter(listMoviesAdapter);
        mEmptyMoviesTextView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        cancelTask();
        super.onDestroy();
    }

    private void initDatabase() {
        LiveData<List<FavoriteMovies>> liveData = mAppDatabase.favoriteMoviesDao().loadListFavoriteMovies();
        liveData.observe(
                this,
                new Observer<List<FavoriteMovies>>() {
                    @Override
                    public void onChanged(List<FavoriteMovies> result) {
                        listFavoriteMovies = result;
                        if (filterListMovies.equals(getString(R.string.favorite_value))) {
                            if (listMovies == null || !listFavoriteMovies.equals(listMovies)) {
                                listMovies = listFavoriteMovies;
                                if (listMovies != null && listMovies.size() > 0) {
                                    showListMovies();
                                } else {
                                    showEmptyListMovies();
                                }
                            }
                        }
                    }
                }
        );
    }

    private void makeListMovies(SortedMovies sortedMovies) {
        cancelTask();
        if (sortedMovies == SortedMovies.Favorite
        ) {
            if (listFavoriteMovies != null
                    && listFavoriteMovies.size() > 0) {
                if (listMovies == null || !listMovies.equals(listFavoriteMovies)) {
                    listMovies = listFavoriteMovies;
                    if (listMovies != null && listMovies.size() > 0) {
                        showListMovies();
                    } else {
                        showEmptyListMovies();
                    }
                }
            } else {
                showEmptyListMovies();
            }
        } else {
            listMoviesTask = new ListMoviesTask(this);
            listMoviesTask.execute(sortedMovies);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.filter, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_filter_by_popular:
                filterListMovies = getString(R.string.most_popular_value);
                setTitle(getString(R.string.pop_movies));
                makeListMovies(SortedMovies.Popular);
                return true;
            case R.id.action_filter_by_rate:
                filterListMovies = getString(R.string.top_rate_value);
                setTitle(getString(R.string.top_movies));
                makeListMovies(SortedMovies.TopRated);
                return true;
            case R.id.action_filter_by_favorite:
                filterListMovies = getString(R.string.favorite_value);
                setTitle(getString(R.string.favorite_movies));
                makeListMovies(SortedMovies.Favorite);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        if (filterListMovies.equals(getString(R.string.favorite_value))) {
            intent.putExtra(FAVORITES_EXTRA_KEY, MainActivity.this.listFavoriteMovies.get(position));
        } else {
            intent.putExtra(Intent.EXTRA_TEXT, MainActivity.this.listMovies.get(position).movie.getId());
        }
        startActivity(intent);
    }

    @Override
    public void onPreExecute() {
        mLoadingProgressBar.setVisibility(View.VISIBLE);
        mEmptyMoviesTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPostExecute(List<FavoriteMovies> result) {
        mLoadingProgressBar.setVisibility(View.INVISIBLE);
        if (result != null && result.size() > 0) {
            this.listMovies = result;
            showListMovies();
        } else {
            showEmptyListMovies();
        }
    }

    private void cancelTask() {
        if (listMoviesTask != null && listMoviesTask.getStatus() == AsyncTask.Status.RUNNING) {
            listMoviesTask.cancel(true);
        }
    }
}

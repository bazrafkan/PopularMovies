package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
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
import com.example.popularmovies.models.ListMovies;
import com.example.popularmovies.models.SortedMovies;
import com.example.popularmovies.tasks.ListMoviesTask;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        ListMoviesAdapter.ListItemClickListener,
        ListMoviesTask.AsyncListMoviesResult {
    private static final String LIST_MOVIES_KEY = "listMovies";
    private TextView mEmptyMoviesTextView;
    private ProgressBar mLoadingProgressBar;
    private RecyclerView mListMovies;
    private List<ListMovies> listMovies;
    private ListMoviesTask listMoviesTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEmptyMoviesTextView = findViewById(R.id.tv_empty_message);
        mLoadingProgressBar = findViewById(R.id.pb_loading_indicator);
        mListMovies = findViewById(R.id.rv_list_movies);
        if (savedInstanceState != null) {
            if (savedInstanceState.containsKey(LIST_MOVIES_KEY)) {
                Serializable serializable = savedInstanceState.getSerializable(LIST_MOVIES_KEY);
                ArrayList<ListMovies> list = new ArrayList<>();
                try {
                    ArrayList tempList = (ArrayList) serializable;
                    if (tempList != null && tempList.size() > 0) {
                        for (int i = 0; i < tempList.size(); i++) {
                            list.add(i, (ListMovies) tempList.get(i));
                        }
                        listMovies = new ArrayList<>(list);
                        showListMovies();
                    } else {
                        getSharedPreferences();
                    }
                } catch (Exception e) {
                    getSharedPreferences();
                }
            } else {
                getSharedPreferences();
            }
        } else {
            getSharedPreferences();
        }
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        if (listMovies != null && listMovies.size() > 0) {
            ArrayList<ListMovies> arrayList = new ArrayList<>(listMovies);
            outState.putSerializable(LIST_MOVIES_KEY, arrayList);
        }
        super.onSaveInstanceState(outState);
    }

    private void getSharedPreferences() {
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        String filter = sharedPreferences.getString(getString(R.string.pref_filter_key),
                getString(R.string.most_popular_value));
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
            //TODO get favorite movies list
        }
    }

    private void showListMovies() {
        GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
        mListMovies.setLayoutManager(layoutManager);
        mListMovies.setHasFixedSize(true);
        ListMoviesAdapter listMoviesAdapter = new ListMoviesAdapter(listMovies, MainActivity.this);
        mListMovies.setAdapter(listMoviesAdapter);
    }

    private void showEmptyListMovies() {
        mEmptyMoviesTextView.setVisibility(View.VISIBLE);
    }

    @Override
    protected void onDestroy() {
        cancelTask();
        super.onDestroy();
    }

    private void makeListMovies(SortedMovies sortedMovies) {
        cancelTask();
        listMoviesTask = new ListMoviesTask(this);
        listMoviesTask.execute(sortedMovies);
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
                setTitle(getString(R.string.pop_movies));
                makeListMovies(SortedMovies.Popular);
                return true;
            case R.id.action_filter_by_rate:
                setTitle(getString(R.string.top_movies));
                makeListMovies(SortedMovies.TopRated);
                return true;
            case R.id.action_filter_by_favorite:
                setTitle(getString(R.string.favorite_movies));
                //TODO get favorite movies list
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, MainActivity.this.listMovies.get(position).getId());
        startActivity(intent);
    }

    @Override
    public void onPreExecute() {
        mLoadingProgressBar.setVisibility(View.VISIBLE);
        mEmptyMoviesTextView.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onPostExecute(List<ListMovies> result) {
        mLoadingProgressBar.setVisibility(View.INVISIBLE);
        if (result != null && result.size() > 0 && false) {
            MainActivity.this.listMovies = result;
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

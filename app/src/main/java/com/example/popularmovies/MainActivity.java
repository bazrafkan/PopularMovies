package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
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

import java.util.List;

public class MainActivity extends AppCompatActivity implements ListMoviesAdapter.ListItemClickListener, ListMoviesTask.AsyncListMoviesResult {
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
        makeListMovies(SortedMovies.Popular);


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
                makeListMovies(SortedMovies.Popular);
                return true;
            case R.id.action_filter_by_rate:
                makeListMovies(SortedMovies.TopRated);
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
        if (result != null && result.size() > 0) {
            MainActivity.this.listMovies = result;
            GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, 2);
            mListMovies.setLayoutManager(layoutManager);
            mListMovies.setHasFixedSize(true);
            ListMoviesAdapter listMoviesAdapter = new ListMoviesAdapter(result, MainActivity.this);
            mListMovies.setAdapter(listMoviesAdapter);
        } else {
            mEmptyMoviesTextView.setVisibility(View.VISIBLE);
            AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                    .setMessage(R.string.can_not_find_movies)
                    .setTitle(R.string.error)
                    .setNegativeButton(android.R.string.no, null);
            AlertDialog dialog = builder.create();
            dialog.show();
        }
    }

    private void cancelTask() {
        if (listMoviesTask != null && listMoviesTask.getStatus() == AsyncTask.Status.RUNNING) {
            listMoviesTask.cancel(true);
        }
    }
}

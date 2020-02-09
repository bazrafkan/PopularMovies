package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.popularmovies.models.ListMovies;
import com.example.popularmovies.models.PopularMovies;
import com.example.popularmovies.models.TopRatedMovies;
import com.google.android.material.chip.Chip;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private TextView mEmptyMoviesTextView;
    private Chip mFilterChip;
    private ProgressBar mLoadingProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEmptyMoviesTextView = findViewById(R.id.tv_empty_message);
        mFilterChip = findViewById(R.id.cp_filter);
        mLoadingProgressBar = findViewById(R.id.pb_loading_indicator);

        makeListMovies(Sorted.Popular);


    }

    private void makeListMovies(Sorted sorted) {
        new ListMoviesTask().execute(sorted);
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
                mFilterChip.setText(R.string.most_popular);
                makeListMovies(Sorted.Popular);
                return true;
            case R.id.action_filter_by_rate:
                mFilterChip.setText(R.string.top_rate);
                makeListMovies(Sorted.TopRated);
                return true;

        }
        return super.onOptionsItemSelected(item);
    }

    public class ListMoviesTask extends AsyncTask<Sorted, Void, List<ListMovies>> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mLoadingProgressBar.setVisibility(View.VISIBLE);
            mEmptyMoviesTextView.setVisibility(View.INVISIBLE);
        }

        @Override
        protected List<ListMovies> doInBackground(Sorted... sorteds) {
            switch (sorteds[0]) {
                case TopRated:
                    return TopRatedMovies.getTopRatedMovies();
                case Popular:
                    return PopularMovies.getPopularMovies();
                default:
                    return null;
            }
        }

        @Override
        protected void onPostExecute(List<ListMovies> listMovies) {
            mLoadingProgressBar.setVisibility(View.INVISIBLE);
            if (listMovies != null && listMovies.size() > 0) {
                //TODO show list of movies
            } else {
                mEmptyMoviesTextView.setVisibility(View.VISIBLE);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this)
                        .setMessage("Can not find movies")
                        .setTitle("Error")
                        .setNegativeButton(android.R.string.no, null);
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        }

    }

    enum Sorted {
        Popular,
        TopRated
    }
}

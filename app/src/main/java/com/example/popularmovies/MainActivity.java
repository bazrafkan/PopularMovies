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
import com.example.popularmovies.models.PopularMovies;
import com.example.popularmovies.models.TopRatedMovies;
import com.google.android.material.chip.Chip;

import java.util.List;

public class MainActivity extends AppCompatActivity implements ListMoviesAdapter.ListItemClickListener {
    private TextView mEmptyMoviesTextView;
    private Chip mFilterChip;
    private ProgressBar mLoadingProgressBar;
    private RecyclerView mListMovies;
    private ListMoviesAdapter listMoviesAdapter;
    private List<ListMovies> listMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mEmptyMoviesTextView = findViewById(R.id.tv_empty_message);
        mFilterChip = findViewById(R.id.cp_filter);
        mLoadingProgressBar = findViewById(R.id.pb_loading_indicator);
        mListMovies = findViewById(R.id.rv_list_movies);
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
                MainActivity.this.listMovies = listMovies;
                GridLayoutManager layoutManager = new GridLayoutManager(MainActivity.this, getSpanCount(100));
                mListMovies.setLayoutManager(layoutManager);
                mListMovies.setHasFixedSize(true);
                listMoviesAdapter = new ListMoviesAdapter(listMovies, MainActivity.this);
                mListMovies.setAdapter(listMoviesAdapter);
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

    @Override
    public void onListItemClick(int position) {
        Intent intent = new Intent(MainActivity.this, DetailActivity.class);
        intent.putExtra(Intent.EXTRA_TEXT, MainActivity.this.listMovies.get(position).getId());
        startActivity(intent);
    }

    enum Sorted {
        Popular,
        TopRated
    }

    private int getSpanCount(int widthDP) {
        final float density = getResources().getDisplayMetrics().density;
        int spanCount = (int) Math.floor(mListMovies.getWidth() / (widthDP * density));
        return spanCount;
    }
}

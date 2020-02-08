package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.chip.Chip;

public class MainActivity extends AppCompatActivity {
    private TextView mDisplayTextView;
    private Toast toast;
    private Chip mFilterChip;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDisplayTextView = (TextView) findViewById(R.id.tv_display);
        mDisplayTextView.setText(BuildConfig.tmdb_api_key);
        mFilterChip = (Chip) findViewById(R.id.cp_filter);
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
                mFilterChip.setText("Popular");
                //TODO call sort by popular
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(this, "Filter by popular", Toast.LENGTH_LONG);
                toast.show();
                return true;
            case R.id.action_filter_by_rate:
                mFilterChip.setText("Rate");
                //TODO call sort by rate
                if (toast != null) {
                    toast.cancel();
                }
                toast = Toast.makeText(this, "Filter by rate", Toast.LENGTH_LONG);
                toast.show();
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}

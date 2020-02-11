package com.example.popularmovies.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.popularmovies.R;
import com.example.popularmovies.models.Genres;
import com.google.android.material.chip.Chip;

import java.util.List;

public class ListChipAdapter extends RecyclerView.Adapter<ListChipAdapter.ChipViewHolder> {
    private static final String TAG = ListMoviesAdapter.class.getSimpleName();
    private List<Genres> listGenres;

    public ListChipAdapter(List<Genres> listGenres) {
        this.listGenres = listGenres;
    }

    @NonNull
    @Override
    public ChipViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.chip_list_view_item, parent, false);
        ListChipAdapter.ChipViewHolder chipViewHolder = new ListChipAdapter.ChipViewHolder(view);
        return chipViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ChipViewHolder holder, int position) {
        holder.bind(listGenres.get(position).getName());
    }

    @Override
    public int getItemCount() {
        if (listGenres != null) {
            return listGenres.size();
        } else {
            return 0;

        }
    }

    class ChipViewHolder extends RecyclerView.ViewHolder {
        Chip mItemGenreChip;

        public ChipViewHolder(@NonNull View itemView) {
            super(itemView);
            mItemGenreChip = itemView.findViewById(R.id.cp_item_genre);
        }

        void bind(String name) {
            Log.d(TAG, "name: " + name);
            mItemGenreChip.setText(name);
        }

    }
}

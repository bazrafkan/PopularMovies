package com.example.popularmovies.adapters;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.example.popularmovies.R;
import com.example.popularmovies.databse.entry.Review;

import java.util.List;

public class ListReviewsAdapter extends RecyclerView.Adapter<ListReviewsAdapter.ItemViewHolder> {
    private static final String TAG = ListReviewsAdapter.class.getSimpleName();
    private List<Review> reviews;

    public ListReviewsAdapter(List<Review> reviews) {
        this.reviews = reviews;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);
        View view = layoutInflater.inflate(R.layout.reviews_list_item, parent, false);
        ItemViewHolder itemViewHolder = new ItemViewHolder(view);
        return itemViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Review review = reviews.get(position);
        holder.bind(review.getAuthor(), review.getContent());
    }

    @Override
    public int getItemCount() {
        if (reviews != null) {
            return reviews.size();
        } else {
            return 0;
        }
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        TextView mAuthorText;
        TextView mContentText;

        public ItemViewHolder(@NonNull View itemView) {
            super(itemView);
            mAuthorText = itemView.findViewById(R.id.tv_reviews_author);
            mContentText = itemView.findViewById(R.id.tv_reviews_content);
        }

        void bind(String author, String content) {
            Log.d(TAG, "author: " + author);
            mAuthorText.setText(author);
            mContentText.setText(content);
        }
    }
}

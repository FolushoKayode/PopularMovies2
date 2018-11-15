package com.mofoluwashokayode.popularmovies2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mofoluwashokayode.popularmovies2.movie_model.Review;

import java.util.ArrayList;

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private final ArrayList<Review> reviews;

    public ReviewAdapter(ArrayList<Review> reviews) {
        this.reviews = reviews;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.review_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        String author = reviews.get(position).getAuthor();
        holder.textViewAuthor.setText(String.format("Author: %s", author));
        holder.textViewContent.setText(reviews.get(position).getContent());
    }

    @Override
    public int getItemCount() {
        return reviews.size();
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        final TextView textViewAuthor;
        final TextView textViewContent;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewAuthor = itemView.findViewById(R.id.review_author);
            textViewContent = itemView.findViewById(R.id.review_content);
        }
    }
}

package com.mofoluwashokayode.popularmovies2;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.mofoluwashokayode.popularmovies2.callbacks.TrailerAdapterCallback;
import com.mofoluwashokayode.popularmovies2.movie_model.Trailer;

import java.util.ArrayList;

public class TrailerAdapter extends RecyclerView.Adapter<TrailerAdapter.ViewHolder> {

    private final ArrayList<Trailer> trailers;
    private final TrailerAdapterCallback adapterCallback;

    public TrailerAdapter(ArrayList<Trailer> trailers, TrailerAdapterCallback callback) {
        this.trailers = trailers;
        this.adapterCallback = callback;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.trailer_item, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Trailer trailer = trailers.get(position);
        holder.trailer = trailer;
        holder.trailerName.setText(trailer.getName());
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        final TextView trailerName;
        Trailer trailer;

        ViewHolder(View itemView) {
            super(itemView);
            trailerName = itemView.findViewById(R.id.trailer_name);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    adapterCallback.onItemClickListener(trailer.getKey());
                }
            });
        }

    }
}

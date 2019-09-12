package com.dicoding.laitnis.mtcg.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.dicoding.laitnis.mtcg.MovieDetailActivity;
import com.dicoding.laitnis.mtcg.R;
import com.dicoding.laitnis.mtcg.entity.Movie;

import java.util.ArrayList;

public class FavoriteMovieAdapter extends RecyclerView.Adapter<FavoriteMovieAdapter.FavoriteMovieViewHolder> {

    private ArrayList<Movie> listMovies = new ArrayList<>();
    private Context mContext;

    public void setListMovies(Context context, ArrayList<Movie> items) {
        mContext = context;
        listMovies.clear();
        listMovies.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteMovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_movie_item, parent, false);
        return new FavoriteMovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteMovieViewHolder holder, final int position) {

        Glide.with(mContext)
                .load(listMovies.get(position).getMovPoster())
                .placeholder(R.color.colorPrimaryDark)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(holder.ivPoster);

        holder.tvTitle.setText(listMovies.get(position).getMovTitle());
        holder.tvYear.setText(listMovies.get(position).getMovYear());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, listMovies.get(position));
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return listMovies.size();
    }

    class FavoriteMovieViewHolder extends RecyclerView.ViewHolder {

        final ImageView ivPoster;
        final TextView tvTitle, tvYear;

        FavoriteMovieViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            tvTitle = itemView.findViewById(R.id.tv_movie_title);
            tvYear = itemView.findViewById(R.id.tv_movie_year);
        }
    }
}

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
import com.dicoding.laitnis.mtcg.entity.MoviePopular;

import java.util.ArrayList;

public class MoviePAdapter extends RecyclerView.Adapter<MoviePAdapter.CardViewViewHolder> {

    private Context mpContext;

    private ArrayList<MoviePopular> mpData = new ArrayList<>();

    public void setMoviesPData(Context context, ArrayList<MoviePopular> items) {
        mpContext = context;
        mpData.clear();
        mpData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MoviePAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_movie_item, parent, false);
        return new MoviePAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MoviePAdapter.CardViewViewHolder holder, final int position) {

        MoviePopular movie = mpData.get(position);

        Glide.with(mpContext)
                .load("https://image.tmdb.org/t/p/w500" + movie.getMov_poster())
                .placeholder(R.color.colorPrimaryDark)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(holder.ivPoster);

        holder.tvTitle.setText(movie.getMov_title());

        //set Date Format

        String RDate = "";

        String d = movie.getMov_year().substring(8, 10);
        String y = movie.getMov_year().substring(0, 4);

        char m1 = movie.getMov_year().charAt(5);
        char m2 = movie.getMov_year().charAt(6);
        String month1 = String.valueOf(m1);
        String month2 = String.valueOf(m2);
        String monthFinal = month1 + month2;

        switch (monthFinal) {
            case "01":
                RDate = mpContext.getResources().getString(R.string.jan) + " " + d + ", " + y;
                break;
            case "02":
                RDate = mpContext.getResources().getString(R.string.feb) + " " + d + ", " + y;
                break;
            case "03":
                RDate = mpContext.getResources().getString(R.string.mar) + " " + d + ", " + y;
                break;
            case "04":
                RDate = mpContext.getResources().getString(R.string.apr) + " " + d + ", " + y;
                break;
            case "05":
                RDate = mpContext.getResources().getString(R.string.may) + " " + d + ", " + y;
                break;
            case "06":
                RDate = mpContext.getResources().getString(R.string.jun) + " " + d + ", " + y;
                break;
            case "07":
                RDate = mpContext.getResources().getString(R.string.jul) + " " + d + ", " + y;
                break;
            case "08":
                RDate = mpContext.getResources().getString(R.string.aug) + " " + d + ", " + y;
                break;
            case "09":
                RDate = mpContext.getResources().getString(R.string.sep) + " " + d + ", " + y;
                break;
            case "10":
                RDate = mpContext.getResources().getString(R.string.oct) + " " + d + ", " + y;
                break;
            case "11":
                RDate = mpContext.getResources().getString(R.string.nov) + " " + d + ", " + y;
                break;
            case "12":
                RDate = mpContext.getResources().getString(R.string.des) + " " + d + ", " + y;
                break;
        }

        final String rDate = RDate;

        holder.tvYear.setText(rDate);

        //set Rating

        final double rating_d = movie.getMov_rating() * 10;
        final int rating = (int) rating_d;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Movie movie = new Movie();

                movie.setMovId(mpData.get(position).getMov_id());
                movie.setMovTitle(mpData.get(position).getMov_title());
                movie.setMovYear(rDate);
                movie.setMovRating(rating);
                movie.setMovDesc(mpData.get(position).getMov_desc());
                movie.setMovPoster("https://image.tmdb.org/t/p/w500" + mpData.get(position).getMov_poster());
                movie.setMovPosterBg("https://image.tmdb.org/t/p/w780" + mpData.get(position).getMov_poster_bg());

                Intent intent = new Intent(mpContext, MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
                mpContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mpData.size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle, tvYear;

        public CardViewViewHolder(View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            tvTitle = itemView.findViewById(R.id.tv_movie_title);
            tvYear = itemView.findViewById(R.id.tv_movie_year);
        }
    }
}

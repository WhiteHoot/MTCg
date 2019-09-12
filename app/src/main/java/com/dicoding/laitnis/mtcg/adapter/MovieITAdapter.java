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
import com.dicoding.laitnis.mtcg.entity.MovieInTheater;

import java.util.ArrayList;

public class MovieITAdapter extends RecyclerView.Adapter<MovieITAdapter.CardViewViewHolder> {

    private Context mtContext;

    private ArrayList<MovieInTheater> mtData = new ArrayList<>();

    public void setMoviesITData(Context context, ArrayList<MovieInTheater> items) {
        mtContext = context;
        mtData.clear();
        mtData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MovieITAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_movie_item, parent, false);
        return new MovieITAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieITAdapter.CardViewViewHolder holder, final int position) {

        MovieInTheater movie = mtData.get(position);

        Glide.with(mtContext)
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
                RDate = mtContext.getResources().getString(R.string.jan) + " " + d + ", " + y;
                break;
            case "02":
                RDate = mtContext.getResources().getString(R.string.feb) + " " + d + ", " + y;
                break;
            case "03":
                RDate = mtContext.getResources().getString(R.string.mar) + " " + d + ", " + y;
                break;
            case "04":
                RDate = mtContext.getResources().getString(R.string.apr) + " " + d + ", " + y;
                break;
            case "05":
                RDate = mtContext.getResources().getString(R.string.may) + " " + d + ", " + y;
                break;
            case "06":
                RDate = mtContext.getResources().getString(R.string.jun) + " " + d + ", " + y;
                break;
            case "07":
                RDate = mtContext.getResources().getString(R.string.jul) + " " + d + ", " + y;
                break;
            case "08":
                RDate = mtContext.getResources().getString(R.string.aug) + " " + d + ", " + y;
                break;
            case "09":
                RDate = mtContext.getResources().getString(R.string.sep) + " " + d + ", " + y;
                break;
            case "10":
                RDate = mtContext.getResources().getString(R.string.oct) + " " + d + ", " + y;
                break;
            case "11":
                RDate = mtContext.getResources().getString(R.string.nov) + " " + d + ", " + y;
                break;
            case "12":
                RDate = mtContext.getResources().getString(R.string.des) + " " + d + ", " + y;
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

                movie.setMovId(mtData.get(position).getMov_id());
                movie.setMovTitle(mtData.get(position).getMov_title());
                movie.setMovYear(rDate);
                movie.setMovRating(rating);
                movie.setMovDesc(mtData.get(position).getMov_desc());
                movie.setMovPoster("https://image.tmdb.org/t/p/w500" + mtData.get(position).getMov_poster());
                movie.setMovPosterBg("https://image.tmdb.org/t/p/w780" + mtData.get(position).getMov_poster_bg());

                Intent intent = new Intent(mtContext, MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
                mtContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mtData.size();
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

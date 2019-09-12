package com.dicoding.laitnis.mtcg.adapter;

import android.annotation.SuppressLint;
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

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.CardViewViewHolder> {

    private Context mContext;

    private ArrayList<Movie> mData = new ArrayList<>();

    public void setMoviesData(Context context, ArrayList<Movie> items) {
        mContext = context;
        mData.clear();
        mData.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_movieupcoming_item, parent, false);
        return new CardViewViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final CardViewViewHolder holder, final int position) {

        Movie movie = mData.get(position);

        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w500" + movie.getMovPoster())
                .placeholder(R.color.colorPrimaryDark)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(holder.ivPoster);

        holder.tvTitle.setText(movie.getMovTitle());

        //set Date Format

        String RDate = "";

        String d = movie.getMovYear().substring(8, 10);
        String y = movie.getMovYear().substring(0, 4);

        char m1 = movie.getMovYear().charAt(5);
        char m2 = movie.getMovYear().charAt(6);
        String month1 = String.valueOf(m1);
        String month2 = String.valueOf(m2);
        String monthFinal = month1 + month2;

        switch (monthFinal) {
            case "01":
                RDate = mContext.getResources().getString(R.string.jan) + " " + d + ", " + y;
                break;
            case "02":
                RDate = mContext.getResources().getString(R.string.feb) + " " + d + ", " + y;
                break;
            case "03":
                RDate = mContext.getResources().getString(R.string.mar) + " " + d + ", " + y;
                break;
            case "04":
                RDate = mContext.getResources().getString(R.string.apr) + " " + d + ", " + y;
                break;
            case "05":
                RDate = mContext.getResources().getString(R.string.may) + " " + d + ", " + y;
                break;
            case "06":
                RDate = mContext.getResources().getString(R.string.jun) + " " + d + ", " + y;
                break;
            case "07":
                RDate = mContext.getResources().getString(R.string.jul) + " " + d + ", " + y;
                break;
            case "08":
                RDate = mContext.getResources().getString(R.string.aug) + " " + d + ", " + y;
                break;
            case "09":
                RDate = mContext.getResources().getString(R.string.sep) + " " + d + ", " + y;
                break;
            case "10":
                RDate = mContext.getResources().getString(R.string.oct) + " " + d + ", " + y;
                break;
            case "11":
                RDate = mContext.getResources().getString(R.string.nov) + " " + d + ", " + y;
                break;
            case "12":
                RDate = mContext.getResources().getString(R.string.des) + " " + d + ", " + y;
                break;
        }

        final String rDate = RDate;

        holder.tvYear.setText(rDate);

        //set Rating

        final double rating_d = movie.getMovRating() * 10;
        final int rating = (int) rating_d;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Movie movie = new Movie();

                movie.setMovId(mData.get(position).getMovId());
                movie.setMovTitle(mData.get(position).getMovTitle());
                movie.setMovYear(rDate);
                movie.setMovRating(rating);
                movie.setMovDesc(mData.get(position).getMovDesc());
                movie.setMovPoster("https://image.tmdb.org/t/p/w500" + mData.get(position).getMovPoster());
                movie.setMovPosterBg("https://image.tmdb.org/t/p/w780" + mData.get(position).getMovPosterBg());

                Intent intent = new Intent(mContext, MovieDetailActivity.class);
                intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mData.size();
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

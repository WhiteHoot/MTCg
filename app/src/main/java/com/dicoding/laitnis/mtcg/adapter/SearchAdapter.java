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
import com.dicoding.laitnis.mtcg.TVShowDetailActivity;
import com.dicoding.laitnis.mtcg.entity.Movie;
import com.dicoding.laitnis.mtcg.entity.SearchData;
import com.dicoding.laitnis.mtcg.entity.TVShow;

import java.util.ArrayList;

public class SearchAdapter extends RecyclerView.Adapter<SearchAdapter.CardViewViewHolder> {

    private Context mContext;

    private ArrayList<SearchData> sData = new ArrayList<>();
    private String releaseDate;

    public void setResultData(Context context, ArrayList<SearchData> items) {
        mContext = context;
        sData.clear();
        sData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public SearchAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.search_item, parent, false);
        return new SearchAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchAdapter.CardViewViewHolder holder, final int position) {

        SearchData searchData = sData.get(position);

        String mediaType = searchData.getMediaType();

        switch (mediaType) {
            case "movie":
                holder.tvTitle.setText(searchData.getMovieTitle());

                Glide.with(mContext)
                        .load("https://image.tmdb.org/t/p/w500" + searchData.getMoviePoster())
                        .placeholder(R.color.colorPrimaryDark)
                        .transition(DrawableTransitionOptions.withCrossFade(300))
                        .into(holder.ivPoster);

                holder.tvDesc.setText(searchData.getMovieDesc());

                //set Movie Date Format
                String initRDate = searchData.getMovieYear();

                if (initRDate.length() > 0) {
                    String d = searchData.getMovieYear().substring(8, 10);
                    String y = searchData.getMovieYear().substring(0, 4);

                    char m1 = searchData.getMovieYear().charAt(5);
                    char m2 = searchData.getMovieYear().charAt(6);
                    String month1 = String.valueOf(m1);
                    String month2 = String.valueOf(m2);
                    String monthFinal = month1 + month2;

                    switch (monthFinal) {
                        case "01":
                            releaseDate = mContext.getResources().getString(R.string.jan) + " " + d + ", " + y;
                            break;
                        case "02":
                            releaseDate = mContext.getResources().getString(R.string.feb) + " " + d + ", " + y;
                            break;
                        case "03":
                            releaseDate = mContext.getResources().getString(R.string.mar) + " " + d + ", " + y;
                            break;
                        case "04":
                            releaseDate = mContext.getResources().getString(R.string.apr) + " " + d + ", " + y;
                            break;
                        case "05":
                            releaseDate = mContext.getResources().getString(R.string.may) + " " + d + ", " + y;
                            break;
                        case "06":
                            releaseDate = mContext.getResources().getString(R.string.jun) + " " + d + ", " + y;
                            break;
                        case "07":
                            releaseDate = mContext.getResources().getString(R.string.jul) + " " + d + ", " + y;
                            break;
                        case "08":
                            releaseDate = mContext.getResources().getString(R.string.aug) + " " + d + ", " + y;
                            break;
                        case "09":
                            releaseDate = mContext.getResources().getString(R.string.sep) + " " + d + ", " + y;
                            break;
                        case "10":
                            releaseDate = mContext.getResources().getString(R.string.oct) + " " + d + ", " + y;
                            break;
                        case "11":
                            releaseDate = mContext.getResources().getString(R.string.nov) + " " + d + ", " + y;
                            break;
                        case "12":
                            releaseDate = mContext.getResources().getString(R.string.des) + " " + d + ", " + y;
                            break;
                    }
                }

                //set Rating

                final double rating_d = searchData.getMovieRating() * 10;
                final int rating = (int) rating_d;

                holder.tvDesc.setText(searchData.getMovieDesc());


                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Movie movie = new Movie();

                        movie.setMovId(sData.get(position).getMovieId());
                        movie.setMovTitle(sData.get(position).getMovieTitle());
                        movie.setMovYear(releaseDate);
                        movie.setMovRating(rating);
                        movie.setMovDesc(sData.get(position).getMovieDesc());
                        movie.setMovPoster("https://image.tmdb.org/t/p/w500" + sData.get(position).getMoviePoster());
                        movie.setMovPosterBg("https://image.tmdb.org/t/p/w780" + sData.get(position).getMoviePosterbg());

                        Intent intent = new Intent(mContext, MovieDetailActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(MovieDetailActivity.EXTRA_MOVIE, movie);
                        mContext.startActivity(intent);
                    }
                });

                break;
            case "tv":
                holder.tvTitle.setText(searchData.getSeriesName());

                Glide.with(mContext)
                        .load("https://image.tmdb.org/t/p/w500" + searchData.getSeriesPoster())
                        .placeholder(R.color.colorPrimaryDark)
                        .transition(DrawableTransitionOptions.withCrossFade(300))
                        .into(holder.ivPoster);

                holder.tvDesc.setText(searchData.getSeriesDesc());

                //set TV Date Format

                if (searchData.getSeriesYear().length() >= 4) {
                    releaseDate = searchData.getSeriesYear().substring(0, 4);
                } else {
                    releaseDate = "";
                }

                final double rating_t = searchData.getSeriesRating() * 10;
                final int ratingTvshow = (int) rating_t;

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        TVShow tvShow = new TVShow();

                        tvShow.setSeriesId(sData.get(position).getSeriesId());
                        tvShow.setSeriesTitle(sData.get(position).getSeriesName());
                        tvShow.setSeriesYear(releaseDate);
                        tvShow.setSeriesRating(ratingTvshow);
                        tvShow.setSeriesDesc(sData.get(position).getSeriesDesc());
                        tvShow.setSeriesPoster("https://image.tmdb.org/t/p/w500" + sData.get(position).getSeriesPoster());
                        tvShow.setSeriesPosterBg("https://image.tmdb.org/t/p/w780" + sData.get(position).getSeriesPosterbg());

                        Intent intent = new Intent(mContext, TVShowDetailActivity.class);
                        intent.setFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                        intent.putExtra(TVShowDetailActivity.EXTRA_TVSHOW, tvShow);
                        mContext.startActivity(intent);
                    }
                });

                break;

            case "person":

                holder.tvTitle.setText(searchData.getPersonName());

                Glide.with(mContext)
                        .load("https://image.tmdb.org/t/p/w500" + searchData.getProfilePath())
                        .placeholder(R.color.colorPrimaryDark)
                        .transition(DrawableTransitionOptions.withCrossFade(300))
                        .into(holder.ivPoster);

                holder.tvDesc.setText("");

                break;
        }
    }

    @Override
    public int getItemCount() {
        return sData.size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView ivPoster;
        TextView tvTitle, tvDesc;

        public CardViewViewHolder(View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.img_photo);
            tvTitle = itemView.findViewById(R.id.txt_title);
            tvDesc = itemView.findViewById(R.id.txt_description);
        }
    }
}

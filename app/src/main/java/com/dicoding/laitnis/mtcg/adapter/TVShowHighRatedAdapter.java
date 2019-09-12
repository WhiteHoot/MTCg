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
import com.dicoding.laitnis.mtcg.R;
import com.dicoding.laitnis.mtcg.TVShowDetailActivity;
import com.dicoding.laitnis.mtcg.entity.TVShow;
import com.dicoding.laitnis.mtcg.entity.TVShowHighRated;

import java.util.ArrayList;

public class TVShowHighRatedAdapter extends RecyclerView.Adapter<TVShowHighRatedAdapter.CardViewViewHolder> {

    private Context thContext;

    private ArrayList<TVShowHighRated> thData = new ArrayList<>();

    public void setTvshowHighRatedData(Context context, ArrayList<TVShowHighRated> items) {
        thContext = context;
        thData.clear();
        thData.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public TVShowHighRatedAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_tvshow_item, parent, false);
        return new TVShowHighRatedAdapter.CardViewViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TVShowHighRatedAdapter.CardViewViewHolder holder, final int position) {

        TVShowHighRated tvShow = thData.get(position);

        Glide.with(thContext)
                .load("https://image.tmdb.org/t/p/w500" + tvShow.getSeries_poster())
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(holder.seriesPoster);

        holder.seriesTitle.setText(tvShow.getSeries_title());

        final double rating_d = tvShow.getSeries_rating() * 10;
        final int rating = (int) rating_d;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TVShow tvShow = new TVShow();
                tvShow.setSeriesId(thData.get(position).getSeries_id());
                tvShow.setSeriesTitle(thData.get(position).getSeries_title());
                tvShow.setSeriesYear(thData.get(position).getSeries_year().substring(0, 4));
                tvShow.setSeriesRating((double) rating);
                tvShow.setSeriesDesc(thData.get(position).getSeries_desc());
                tvShow.setSeriesPoster("https://image.tmdb.org/t/p/w500" + thData.get(position).getSeries_poster());
                tvShow.setSeriesPosterBg("https://image.tmdb.org/t/p/w780" + thData.get(position).getSeries_poster_bg());

                Intent intent = new Intent(thContext, TVShowDetailActivity.class);
                intent.putExtra(TVShowDetailActivity.EXTRA_TVSHOW, tvShow);
                thContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return thData.size();
    }

    class CardViewViewHolder extends RecyclerView.ViewHolder {
        ImageView seriesPoster;
        TextView seriesTitle;

        public CardViewViewHolder(View itemView) {
            super(itemView);
            seriesPoster = itemView.findViewById(R.id.series_poster);
            seriesTitle = itemView.findViewById(R.id.tv_series_title);

        }
    }
}

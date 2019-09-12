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
import com.dicoding.laitnis.mtcg.R;
import com.dicoding.laitnis.mtcg.TVShowDetailActivity;
import com.dicoding.laitnis.mtcg.entity.TVShow;

import java.util.ArrayList;

public class TVShowAdapter extends RecyclerView.Adapter<TVShowAdapter.CardViewViewHolder> {
    private Context tContext;

    private ArrayList<TVShow> tData = new ArrayList<>();

    public void setTvshowData(Context context, ArrayList<TVShow> items) {
        tContext = context;
        tData.clear();
        tData.addAll(items);
        notifyDataSetChanged();
    }

    @Override
    public TVShowAdapter.CardViewViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_tvshow_item, parent, false);
        return new TVShowAdapter.CardViewViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull final TVShowAdapter.CardViewViewHolder holder, final int position) {

        TVShow tvShow = tData.get(position);

        Glide.with(tContext)
                .load("https://image.tmdb.org/t/p/w500" + tvShow.getSeriesPoster())
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(holder.seriesPoster);

        holder.seriesTitle.setText(tvShow.getSeriesTitle());

        final double rating_d = tvShow.getSeriesRating() * 10;
        final int rating = (int) rating_d;

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                TVShow tvShow = new TVShow();
                tvShow.setSeriesId(tData.get(position).getSeriesId());
                tvShow.setSeriesTitle(tData.get(position).getSeriesTitle());
                tvShow.setSeriesYear(tData.get(position).getSeriesYear().substring(0, 4));
                tvShow.setSeriesRating((double) rating);
                tvShow.setSeriesDesc(tData.get(position).getSeriesDesc());
                tvShow.setSeriesPoster("https://image.tmdb.org/t/p/w500" + tData.get(position).getSeriesPoster());
                tvShow.setSeriesPosterBg("https://image.tmdb.org/t/p/w780" + tData.get(position).getSeriesPosterBg());

                Intent intent = new Intent(tContext, TVShowDetailActivity.class);
                intent.putExtra(TVShowDetailActivity.EXTRA_TVSHOW, tvShow);
                tContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return tData.size();
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


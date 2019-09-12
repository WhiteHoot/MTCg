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

import java.util.ArrayList;

public class FavoriteTvshowAdapter extends RecyclerView.Adapter<FavoriteTvshowAdapter.FavoriteTvshowViewHolder> {

    private ArrayList<TVShow> listTvshows = new ArrayList<>();
    private Context tContext;

    public void setListTvshows(Context context, ArrayList<TVShow> items) {
        tContext = context;
        listTvshows.clear();
        listTvshows.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public FavoriteTvshowViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_tvshow_item, parent, false);
        return new FavoriteTvshowAdapter.FavoriteTvshowViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavoriteTvshowViewHolder holder, final int position) {

        Glide.with(tContext)
                .load(listTvshows.get(position).getSeriesPoster())
                .placeholder(R.color.colorPrimaryDark)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(holder.seriesPoster);

        holder.seriesTitle.setText(listTvshows.get(position).getSeriesTitle());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(tContext, TVShowDetailActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra(TVShowDetailActivity.EXTRA_TVSHOW, listTvshows.get(position));
                tContext.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return listTvshows.size();
    }

    class FavoriteTvshowViewHolder extends RecyclerView.ViewHolder {

        ImageView seriesPoster;
        TextView seriesTitle;

        FavoriteTvshowViewHolder(View itemView) {
            super(itemView);
            seriesPoster = itemView.findViewById(R.id.series_poster);
            seriesTitle = itemView.findViewById(R.id.tv_series_title);

        }
    }
}

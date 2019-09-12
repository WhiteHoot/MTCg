package com.dicoding.laitnis.mtcg.adapter;

import android.content.Context;
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
import com.dicoding.laitnis.mtcg.entity.Cast;

import java.util.ArrayList;

public class CastAdapter extends RecyclerView.Adapter<CastAdapter.CastViewHolder> {

    private ArrayList<Cast> listCasts = new ArrayList<>();
    private Context mContext;

    public void setListCasts(Context context, ArrayList<Cast> items) {
        mContext = context;
        listCasts.clear();
        listCasts.addAll(items);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public CastViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rv_cast_item, parent, false);
        return new CastViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CastViewHolder holder, int position) {

        Glide.with(mContext)
                .load("https://image.tmdb.org/t/p/w500" + listCasts.get(position).getCastPhoto())
                .placeholder(R.color.colorPrimaryDark)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(holder.ivPoster);

        holder.tvName.setText(listCasts.get(position).getCastName());
        holder.tvChar.setText(listCasts.get(position).getCastCharacter());

    }

    @Override
    public int getItemCount() {
        return listCasts.size();
    }

    public class CastViewHolder extends RecyclerView.ViewHolder {

        final ImageView ivPoster;
        final TextView tvName, tvChar;

        public CastViewHolder(@NonNull View itemView) {
            super(itemView);
            ivPoster = itemView.findViewById(R.id.iv_poster);
            tvName = itemView.findViewById(R.id.tv_cast_name);
            tvChar = itemView.findViewById(R.id.tv_cast_character);
        }
    }
}

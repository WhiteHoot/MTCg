package com.dicoding.laitnis.mtcg;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.laitnis.mtcg.adapter.FavoriteMovieAdapter;
import com.dicoding.laitnis.mtcg.adapter.FavoriteTvshowAdapter;
import com.dicoding.laitnis.mtcg.entity.Movie;
import com.dicoding.laitnis.mtcg.entity.TVShow;

import java.util.ArrayList;

import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.MOVIE_URI;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.TVSHOW_URI;
import static com.dicoding.laitnis.mtcg.helper.MovieMappingHelper.mapMovieCursorToArrayList;
import static com.dicoding.laitnis.mtcg.helper.TvshowMappingHelper.mapTvshowCursorToArrayList;

public class FavoriteActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView rvFavoriteMovies, rvFavoriteTvshow;
    private FavoriteMovieAdapter movieAdapter;
    private FavoriteTvshowAdapter tvshowAdapter;
    private TextView tvNomoviefav, tvNotvfav;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favorite);

        //Get the default actionbar instance

        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Initializes the custom action bar layout
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_favoriteactionbar, null);
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setElevation(0);


        Button btnBack = findViewById(R.id.btn_back);
        Button btnSetting = findViewById(R.id.btn_setting);

        tvNomoviefav = findViewById(R.id.tv_nomoviefav);
        tvNotvfav = findViewById(R.id.tv_notvfav);

        btnBack.setOnClickListener(this);
        btnSetting.setOnClickListener(this);

        movieAdapter = new FavoriteMovieAdapter();
        movieAdapter.notifyDataSetChanged();

        tvshowAdapter = new FavoriteTvshowAdapter();
        tvshowAdapter.notifyDataSetChanged();

        getFavoriteData();

        rvFavoriteMovies = findViewById(R.id.rv_favorite_movies);
        rvFavoriteMovies.setHasFixedSize(true);

        rvFavoriteTvshow = findViewById(R.id.rv_favorite_tvshow);
        rvFavoriteTvshow.setHasFixedSize(true);

        showFavMoviesRecyclerView();
        showFavTvshowRecyclerView();

    }

    private void getFavoriteData() {
        tvNomoviefav.setVisibility(View.VISIBLE);
        tvNotvfav.setVisibility(View.VISIBLE);

        Cursor tvshows = getContentResolver().query(TVSHOW_URI, null, null, null, null);
        Cursor movies = getContentResolver().query(MOVIE_URI, null, null, null, null);

        ArrayList<Movie> movieList = mapMovieCursorToArrayList(movies);
        movieAdapter.setListMovies(getApplicationContext(), movieList);

        ArrayList<TVShow> tvshowList = mapTvshowCursorToArrayList(tvshows);
        tvshowAdapter.setListTvshows(getApplicationContext(), tvshowList);


        if (movieList != null && !movieList.isEmpty()) {
            tvNomoviefav.setVisibility(View.GONE);
        }

        if (tvshowList != null && !tvshowList.isEmpty()) {
            tvNotvfav.setVisibility(View.GONE);
        }
    }

    private void showFavMoviesRecyclerView() {
        rvFavoriteMovies.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        rvFavoriteMovies.setAdapter(movieAdapter);
    }

    private void showFavTvshowRecyclerView() {
        rvFavoriteTvshow.setLayoutManager(new LinearLayoutManager(getApplicationContext(), LinearLayoutManager.HORIZONTAL, false));
        rvFavoriteTvshow.setAdapter(tvshowAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();
        getFavoriteData();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_back:
                onBackPressed();
                break;
            case R.id.btn_setting:
                Intent sIntent = new Intent(this, SettingsActivity.class);
                startActivity(sIntent);
                break;
        }
    }
}

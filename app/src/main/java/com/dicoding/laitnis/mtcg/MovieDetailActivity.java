package com.dicoding.laitnis.mtcg;

import android.annotation.SuppressLint;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.ContentValues;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.andrognito.flashbar.Flashbar;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions;
import com.dicoding.laitnis.mtcg.adapter.CastAdapter;
import com.dicoding.laitnis.mtcg.db.FavoriteMovieHelper;
import com.dicoding.laitnis.mtcg.entity.Cast;
import com.dicoding.laitnis.mtcg.entity.Genre;
import com.dicoding.laitnis.mtcg.entity.Movie;
import com.dicoding.laitnis.mtcg.entity.Video;
import com.dicoding.laitnis.mtcg.viewmodel.MovieDetailViewModel;
import com.dicoding.laitnis.mtcg.widget.FavoriteMovieWidget;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.provider.BaseColumns._ID;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.DATE;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.DESCRIPTION;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.POSTER;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.POSTERBG;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.RATING;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.TITLE;

public class MovieDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_MOVIE = "extra_movie";
    private static final String UPDATE_ACTION = "com.dicoding.laitnis.mtcg.UPDATE_ACTION";

    private FavoriteMovieHelper favoriteMovieHelper;
    private FloatingActionButton btnFavorite, btnRemoveFav;
    private String cueString, site, genre0, genre1, genre2, genre3, genre4;
    private Flashbar flashbarAdd = null;
    private ProgressBar progressBar;
    private Flashbar flashbarRemove = null;
    private CastAdapter castAdapter;
    private RecyclerView rvCast;
    private Boolean genreLoaded, castLoaded, videoLoaded;

    private ArrayList<Video> video = new ArrayList<>();
    private ArrayList<Genre> genre = new ArrayList<>();

    private Observer<ArrayList<Video>> getVideos = new Observer<ArrayList<Video>>() {
        @Override
        public void onChanged(ArrayList<Video> videos) {
            if (videos != null) {
                video.addAll(videos);

                if (!video.isEmpty()) {
                    Video mVideo = video.get(0);
                    cueString = mVideo.getVideoKey();
                    site = mVideo.getVideoSite();
                    Log.e("VideoKey", cueString);
                    Log.e("Site", site);

                    if (site.equals("YouTube")) {
                        initYoutubePlayer();
                    }
                } else {
                    YouTubePlayerFragment youtubeFragment = (YouTubePlayerFragment)
                            getFragmentManager().findFragmentById(R.id.youtubeFragment1);
                    getFragmentManager().beginTransaction().hide(youtubeFragment).commit();

                    Log.e("Hide Player", "Player is hidden");
                }
            }
        }
    };

    private Observer<ArrayList<Genre>> getGenres = new Observer<ArrayList<Genre>>() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onChanged(ArrayList<Genre> genres) {
            if (genres != null) {
                genre.addAll(genres);
                initGenre();
            }
            genreLoaded = true;
        }
    };

    private Observer<ArrayList<Cast>> getCasts = new Observer<ArrayList<Cast>>() {
        @SuppressLint("SetTextI18n")
        @Override
        public void onChanged(ArrayList<Cast> casts) {
            if (casts != null) {
                castAdapter.setListCasts(getApplicationContext(), casts);
                showLoading(false);

            }
            castLoaded = true;
        }
    };

    @SuppressLint({"SetTextI18n", "RestrictedApi"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_detail);

        genreLoaded = false;
        castLoaded = false;
        videoLoaded = false;

        progressBar = findViewById(R.id.progressBar);

        showLoading(true);

        chkLoadedData();

        favoriteMovieHelper = FavoriteMovieHelper.getInstance(getApplicationContext());
        favoriteMovieHelper.open();

        final Movie movie = getIntent().getParcelableExtra(EXTRA_MOVIE);

        initRecyclerView();

        assert movie != null;
        final int id = movie.getMovId();
        final String title = movie.getMovTitle();
        final String desc = movie.getMovDesc();
        final String year = movie.getMovYear();

        final double rating = movie.getMovRating();
        final int rating_r = (int) rating;

        final String poster = movie.getMovPoster();
        final String poster_bg = movie.getMovPosterBg();

        TextView tvTitle = findViewById(R.id.movie_title);
        TextView tvYear = findViewById(R.id.movie_year);
        TextView tvRating = findViewById(R.id.movie_rating);
        ImageView ivPoster = findViewById(R.id.movie_poster);
        ImageView ivPosterBg = findViewById(R.id.poster_bg);
        TextView tvDesc = findViewById(R.id.movie_desc);
        CircleImageView cardView = findViewById(R.id.cd_rating);
        Button btnBack = findViewById(R.id.btn_back);
        btnFavorite = findViewById(R.id.fav_add);
        btnRemoveFav = findViewById(R.id.fav_remove);

        btnBack.setOnClickListener(this);

        tvTitle.setText(title);

        if (rating_r > 70) {
            cardView.setBackgroundColor(getColor(R.color.green));
            tvRating.setText(String.valueOf(rating_r));
        } else if (rating_r < 70 && rating_r > 50) {
            cardView.setBackgroundColor(getColor(R.color.yellow));
            tvRating.setText(String.valueOf(rating_r));
        } else if (rating_r < 50 && rating_r > 1) {
            cardView.setBackgroundColor(getColor(R.color.red));
            tvRating.setText(String.valueOf(rating_r));
        } else {
            cardView.setBackgroundColor(getColor(R.color.gray));
            tvRating.setText("NR");
        }

        tvYear.setText(year);
        tvDesc.setText(desc);
        Glide.with(this)
                .load(poster)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(ivPoster);

        if (poster_bg == null) {
            Glide.with(this)
                    .load(poster)
                    .transition(DrawableTransitionOptions.withCrossFade(300))
                    .into(ivPosterBg);
        } else {
            Glide.with(this)
                    .load(poster_bg)
                    .transition(DrawableTransitionOptions.withCrossFade(300))
                    .into(ivPosterBg);
        }
        Movie favoriteMovie = favoriteMovieHelper.getMoviesFavoritesById(id);

        if (favoriteMovie.getMovId() != 0) {
            setRemovebtnVisible();
        } else {
            setFavbtnVisible();

        }

        btnFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ContentValues args = new ContentValues();
                args.put(_ID, id);
                args.put(TITLE, title);
                args.put(DESCRIPTION, desc);
                args.put(DATE, year);
                args.put(RATING, String.valueOf(rating_r));
                args.put(POSTER, poster);
                args.put(POSTERBG, poster_bg);

                long result = favoriteMovieHelper.insertMovie(movie);

                if (result > 0) {
                    setRemovebtnVisible();
                    Log.e("Succes", "Success");
                    if (flashbarAdd == null) {
                        flashbarAdd = overlay("Added to favorite");
                    }
                    flashbarAdd.show();
                } else {
                    Log.e("Failed", "Failed");
                }

                UpdateWidgetIntent();

            }
        });

        btnRemoveFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteMovieHelper.deleteFavorite(id);
                Log.e("Remove", "Removed");
                if (flashbarRemove == null) {
                    flashbarRemove = overlay("Removed from favorite");
                }
                flashbarRemove.show();
                setFavbtnVisible();

                UpdateWidgetIntent();

            }
        });

        MovieDetailViewModel movieDetailViewModel = ViewModelProviders.of(this).get(MovieDetailViewModel.class);

        //set Video
        movieDetailViewModel.getVideo().observe(this, getVideos);
        movieDetailViewModel.setVideo(String.valueOf(id));

        //set Genre
        movieDetailViewModel.getGenre().observe(this, getGenres);
        movieDetailViewModel.setGenre(String.valueOf(id));

        //set Cast
        movieDetailViewModel.getCast().observe(this, getCasts);
        movieDetailViewModel.setCast(String.valueOf(id));
    }

    private void chkLoadedData() {

        if (genreLoaded && castLoaded && videoLoaded) {
            showLoading(false);
        }

    }

    private void initRecyclerView() {

        castAdapter = new CastAdapter();
        castAdapter.notifyDataSetChanged();

        rvCast = findViewById(R.id.rv_cast);
        rvCast.setHasFixedSize(true);

        rvCast.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        rvCast.setAdapter(castAdapter);

    }

    private void initYoutubePlayer() {

        YouTubePlayerFragment youtubeFragment = (YouTubePlayerFragment)
                getFragmentManager().findFragmentById(R.id.youtubeFragment1);
        youtubeFragment.initialize("Insert your Google API key here!",
                new YouTubePlayer.OnInitializedListener() {
                    @Override
                    public void onInitializationSuccess(YouTubePlayer.Provider provider,
                                                        YouTubePlayer youTubePlayer, boolean b) {

                        youTubePlayer.cueVideo(cueString);
                        videoLoaded = true;
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {
                        videoLoaded = true;
                    }
                });

    }

    @SuppressLint("SetTextI18n")
    private void initGenre() {

        TextView tvGenre = findViewById(R.id.movie_genre);

        if (!genre.isEmpty()) {
            for (int i = 0; i < genre.size(); i++) {
                Genre mGenre;
                if (i == 0) {
                    mGenre = genre.get(i);
                    if (!mGenre.getGenreName().isEmpty()) {
                        genre0 = mGenre.getGenreName();
                        tvGenre.setText(genre0);
                    }
                } else if (i == 1) {
                    mGenre = genre.get(i);
                    if (!mGenre.getGenreName().isEmpty()) {
                        genre1 = mGenre.getGenreName();
                        tvGenre.setText(genre0 + ", " + genre1);
                    }
                } else if (i == 2) {
                    mGenre = genre.get(i);
                    if (!mGenre.getGenreName().isEmpty()) {
                        genre2 = mGenre.getGenreName();
                        tvGenre.setText(genre0 + ", " + genre1 + ", " + genre2);
                    }
                } else if (i == 3) {
                    mGenre = genre.get(i);
                    if (!mGenre.getGenreName().isEmpty()) {
                        genre3 = mGenre.getGenreName();
                        tvGenre.setText(genre0 + ", " + genre1 + ", " + genre2 + ", " + genre3);
                    }
                } else if (i == 4) {
                    mGenre = genre.get(i);
                    if (!mGenre.getGenreName().isEmpty()) {
                        genre4 = mGenre.getGenreName();
                        tvGenre.setText(genre0 + ", " + genre1 + ", " + genre2 + ", " + genre3 + ", " + genre4);
                    }
                }
            }
        }
    }

    private void UpdateWidgetIntent() {
        Intent updateIntent = new Intent(MovieDetailActivity.this, FavoriteMovieWidget.class);
        updateIntent.setAction(MovieDetailActivity.UPDATE_ACTION);
        int[] ids = AppWidgetManager.getInstance(getApplication()).getAppWidgetIds(new ComponentName(getApplication(), FavoriteMovieWidget.class));
        updateIntent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids);
        sendBroadcast(updateIntent);
    }

    private Flashbar overlay(String message) {
        return new Flashbar.Builder(this)
                .gravity(Flashbar.Gravity.BOTTOM)
                .titleSizeInSp(28)
                .message(message)
                .backgroundColorRes(R.color.colorPrimaryDark)
                .duration(1500)
                .showOverlay()
                .build();
    }

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

    @SuppressLint("RestrictedApi")
    private void setRemovebtnVisible() {
        btnFavorite.setVisibility(View.GONE);
        btnRemoveFav.setVisibility(View.VISIBLE);
    }

    @SuppressLint("RestrictedApi")
    private void setFavbtnVisible() {
        btnFavorite.setVisibility(View.VISIBLE);
        btnRemoveFav.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        favoriteMovieHelper.close();
    }

    @Override
    public void onClick(View view) {
        onBackPressed();
    }
}

package com.dicoding.laitnis.mtcg;

import android.annotation.SuppressLint;
import android.content.ContentValues;
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
import com.dicoding.laitnis.mtcg.db.FavoriteTvshowHelper;
import com.dicoding.laitnis.mtcg.entity.Cast;
import com.dicoding.laitnis.mtcg.entity.Genre;
import com.dicoding.laitnis.mtcg.entity.TVShow;
import com.dicoding.laitnis.mtcg.entity.Video;
import com.dicoding.laitnis.mtcg.viewmodel.TvshowDetailViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerFragment;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.provider.BaseColumns._ID;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.DATE;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.DESCRIPTION;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.POSTER;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.POSTERBG;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.RATING;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.TITLE;

public class TVShowDetailActivity extends AppCompatActivity implements View.OnClickListener {

    public static final String EXTRA_TVSHOW = "extra_tvshow";
    private FavoriteTvshowHelper favoriteHelper;
    private FloatingActionButton btnFavorite, btnRemoveFav;
    private ProgressBar progressBar;

    private Flashbar flashbarAdd = null;
    private Flashbar flashbarRemove = null;
    private CastAdapter castAdapter;
    private RecyclerView rvCast;
    private Boolean genreLoaded, castLoaded, videoLoaded;
    private String cueString, site, genre0, genre1, genre2, genre3, genre4;

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

                videoLoaded = true;

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


    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tvshow_detail);

        genreLoaded = false;
        castLoaded = false;
        videoLoaded = false;

        progressBar = findViewById(R.id.progressBar);

        showLoading(true);

        chkLoadedData();

        initRecyclerView();

        favoriteHelper = FavoriteTvshowHelper.getInstance(getApplicationContext());
        favoriteHelper.open();

        ImageView seriesPoster = findViewById(R.id.series_poster);
        ImageView seriesPosterBg = findViewById(R.id.series_poster_bg);
        TextView seriesTitle = findViewById(R.id.series_title);
        TextView seriesRating = findViewById(R.id.series_rating);
        TextView seriesDesc = findViewById(R.id.series_desc);
        CircleImageView cardView = findViewById(R.id.cd_rating);
        btnFavorite = findViewById(R.id.fav_add);
        btnRemoveFav = findViewById(R.id.fav_remove);
        Button btnBack = findViewById(R.id.btn_back);
        TextView seriesYear = findViewById(R.id.series_year);

        btnBack.setOnClickListener(this);

        final TVShow tvShow = getIntent().getParcelableExtra(EXTRA_TVSHOW);

        assert tvShow != null;
        final int id = tvShow.getSeriesId();
        final String title = tvShow.getSeriesTitle();
        final String desc = tvShow.getSeriesDesc();
        final String year = tvShow.getSeriesYear();

        final double rating = tvShow.getSeriesRating();
        final int rating_r = (int) rating;

        final String poster = tvShow.getSeriesPoster();
        final String poster_bg = tvShow.getSeriesPosterBg();

        seriesTitle.setText(title);

        if (rating_r > 70) {
            cardView.setBackgroundColor(getColor(R.color.green));
            seriesRating.setText(String.valueOf(rating_r));
        } else if (rating_r < 70 && rating_r > 50) {
            cardView.setBackgroundColor(getColor(R.color.yellow));
            seriesRating.setText(String.valueOf(rating_r));
        } else if (rating_r < 50 && rating_r > 1) {
            cardView.setBackgroundColor(getColor(R.color.red));
            seriesRating.setText(String.valueOf(rating_r));
        } else {
            cardView.setBackgroundColor(getColor(R.color.gray));
            seriesRating.setText("NR");
        }

        seriesYear.setText(year);
        seriesDesc.setText(desc);
        Glide.with(this)
                .load(poster)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(seriesPoster);
        Glide.with(this)
                .load(poster_bg)
                .transition(DrawableTransitionOptions.withCrossFade(300))
                .into(seriesPosterBg);

        TVShow favoriteTvshow = favoriteHelper.getTVshowsFavoritesById(id);

        if (favoriteTvshow.getSeriesId() != 0) {
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

                long result = favoriteHelper.insertTvshow(tvShow);

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
            }
        });

        btnRemoveFav.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                favoriteHelper.deleteFavorite(id);
                Log.e("Remove", "Removed");
                if (flashbarRemove == null) {
                    flashbarRemove = overlay("Removed from favorite");
                }
                flashbarRemove.show();
                setFavbtnVisible();
            }
        });


        TvshowDetailViewModel tvshowDetailViewModel = ViewModelProviders.of(this).get(TvshowDetailViewModel.class);

        //set Video
        tvshowDetailViewModel.getVideo().observe(this, getVideos);
        tvshowDetailViewModel.setVideo(String.valueOf(id));

        //set Genre
        tvshowDetailViewModel.getGenre().observe(this, getGenres);
        tvshowDetailViewModel.setGenre(String.valueOf(id));

        //set Cast
        tvshowDetailViewModel.getCast().observe(this, getCasts);
        tvshowDetailViewModel.setCast(String.valueOf(id));
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
                    }

                    @Override
                    public void onInitializationFailure(YouTubePlayer.Provider provider,
                                                        YouTubeInitializationResult youTubeInitializationResult) {

                    }
                });

    }

    @SuppressLint("SetTextI18n")
    private void initGenre() {

        TextView tvGenre = findViewById(R.id.series_genre);

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

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
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
        favoriteHelper.close();
    }

    @Override
    public void onClick(View view) {
        onBackPressed();
    }
}

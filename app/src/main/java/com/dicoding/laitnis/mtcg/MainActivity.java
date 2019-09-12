package com.dicoding.laitnis.mtcg;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.dicoding.laitnis.mtcg.fragment.MoviesFragment;
import com.dicoding.laitnis.mtcg.fragment.TvshowsFragment;

import pro.midev.expandedmenulibrary.ExpandedMenuClickListener;
import pro.midev.expandedmenulibrary.ExpandedMenuItem;
import pro.midev.expandedmenulibrary.ExpandedMenuView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, ExpandedMenuClickListener {

    private long backPressedTime;
    private ExpandedMenuView navView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        navView = findViewById(R.id.nav_view);

        //Get the default actionbar instance

        getSupportActionBar().setDisplayShowHomeEnabled(false);
        getSupportActionBar().setDisplayShowTitleEnabled(false);

        //Initializes the custom action bar layout
        LayoutInflater mInflater = LayoutInflater.from(this);
        View mCustomView = mInflater.inflate(R.layout.custom_actionbar, null);
        getSupportActionBar().setCustomView(mCustomView);
        getSupportActionBar().setDisplayShowCustomEnabled(true);
        getSupportActionBar().setElevation(0);

        Button btnSetting = findViewById(R.id.btn_setting);

        btnSetting.setOnClickListener(this);

        navView.setIcons(
                new ExpandedMenuItem(R.drawable.ic_movie_dark, "Movies"),
                new ExpandedMenuItem(R.drawable.ic_tv_dark, "TV Show"),
                new ExpandedMenuItem(R.drawable.ic_search_dark, "Search"),
                new ExpandedMenuItem(R.drawable.ic_favorite_dark, "Favorite"));

        navView.setOnItemClickListener(this);

        if (savedInstanceState == null) {
            Fragment fragment = new MoviesFragment();
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                    .commit();
        }

    }

    @Override
    public void onClick(View view) {

        Intent sIntent = new Intent(this, SettingsActivity.class);
        startActivity(sIntent);

    }

    @Override
    public void onBackPressed() {
        if (backPressedTime + 2000 > System.currentTimeMillis()) {
            finishAffinity();
        } else {
            Toast.makeText(this, getResources().getString(R.string.onExit), Toast.LENGTH_SHORT).show();
        }

        backPressedTime = System.currentTimeMillis();
    }

    @Override
    public void onItemClick(int i) {

        Fragment fragment;

        switch (i) {
            case 0:
                fragment = new MoviesFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                        .commit();
                break;
            case 1:
                fragment = new TvshowsFragment();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.container_layout, fragment, fragment.getClass().getSimpleName())
                        .commit();
                break;
            case 2:
                Intent sIntent = new Intent(this, SearchActivity.class);
                startActivity(sIntent);
                break;
            case 3:
                Intent fIntent = new Intent(this, FavoriteActivity.class);
                startActivity(fIntent);
                break;
        }

    }
}

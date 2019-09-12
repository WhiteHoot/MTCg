package com.dicoding.laitnis.mtcg;

import android.app.SearchManager;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.laitnis.mtcg.adapter.SearchAdapter;
import com.dicoding.laitnis.mtcg.entity.SearchData;
import com.dicoding.laitnis.mtcg.viewmodel.SearchViewModel;

import java.util.ArrayList;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener {

    private SearchAdapter searchAdapter;
    private SearchView searchView;
    private ImageView icSearch;
    private TextView tvSearch;
    private Observer<ArrayList<SearchData>> getResult = new Observer<ArrayList<SearchData>>() {
        @Override
        public void onChanged(ArrayList<SearchData> search) {
            if (search != null) {
                searchAdapter.setResultData(getApplicationContext(), search);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

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
        btnBack.setOnClickListener(this);

        icSearch = findViewById(R.id.ic_search);
        tvSearch = findViewById(R.id.tv_search);

        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);

        searchAdapter = new SearchAdapter();
        searchAdapter.notifyDataSetChanged();

        RecyclerView rvSearch = findViewById(R.id.rv_search);
        rvSearch.setHasFixedSize(true);

        rvSearch.setLayoutManager(new LinearLayoutManager(this));
        rvSearch.setAdapter(searchAdapter);

        final SearchViewModel searchViewModel = ViewModelProviders.of(this).get(SearchViewModel.class);
        searchViewModel.getResults().observe(this, getResult);

        if (searchManager != null) {
            searchView = findViewById(R.id.searchview);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
            searchView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    searchView.setIconified(false);
                }
            });
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    icSearch.setVisibility(View.GONE);
                    tvSearch.setVisibility(View.GONE);
                    searchViewModel.setResults(query);
                    searchView.clearFocus();
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    return false;
                }
            });
        }
    }

    @Override
    public void onClick(View view) {
        onBackPressed();
    }
}

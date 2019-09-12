package com.dicoding.laitnis.mtcg.fragment;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.dicoding.laitnis.mtcg.R;
import com.dicoding.laitnis.mtcg.adapter.TVShowAdapter;
import com.dicoding.laitnis.mtcg.adapter.TVShowHighRatedAdapter;
import com.dicoding.laitnis.mtcg.adapter.TVShowLatestAdapter;
import com.dicoding.laitnis.mtcg.entity.TVShow;
import com.dicoding.laitnis.mtcg.entity.TVShowHighRated;
import com.dicoding.laitnis.mtcg.entity.TVShowLatest;
import com.dicoding.laitnis.mtcg.viewmodel.MainViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class TvshowsFragment extends Fragment {

    private RecyclerView rvTvshowsPopular, rvTvshowsHighRated, rvTvshowsLatest;
    private TVShowAdapter adapter;
    private TVShowHighRatedAdapter tvShowHighRatedAdapter;
    private TVShowLatestAdapter tvShowLatestAdapter;
    private ProgressBar progressBar;
    private MainViewModel mainViewModel;
    private Observer<ArrayList<TVShow>> getTvshowPopular = new Observer<ArrayList<TVShow>>() {
        @Override
        public void onChanged(ArrayList<TVShow> tvshows) {
            if (tvshows != null) {
                adapter.setTvshowData(getContext(), tvshows);
                showLoading(false);
            }
        }
    };

    private Observer<ArrayList<TVShowHighRated>> getTvshowHighRated = new Observer<ArrayList<TVShowHighRated>>() {
        @Override
        public void onChanged(ArrayList<TVShowHighRated> tvshowsHighRated) {
            if (tvshowsHighRated != null) {
                tvShowHighRatedAdapter.setTvshowHighRatedData(getContext(), tvshowsHighRated);
                showLoading(false);
            }
        }
    };

    private Observer<ArrayList<TVShowLatest>> getTvshowLatest = new Observer<ArrayList<TVShowLatest>>() {
        @Override
        public void onChanged(ArrayList<TVShowLatest> tvshowsLatest) {
            if (tvshowsLatest != null) {
                tvShowLatestAdapter.setTvshowLatestData(getContext(), tvshowsLatest);
                showLoading(false);
            }
        }
    };


    public TvshowsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_tvshows, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        setTvshowData();
        showLoading(true);

        adapter = new TVShowAdapter();
        tvShowHighRatedAdapter = new TVShowHighRatedAdapter();
        tvShowLatestAdapter = new TVShowLatestAdapter();

        adapter.notifyDataSetChanged();
        tvShowHighRatedAdapter.notifyDataSetChanged();
        tvShowLatestAdapter.notifyDataSetChanged();

        rvTvshowsPopular = view.findViewById(R.id.rv_series_popular);
        rvTvshowsHighRated = view.findViewById(R.id.rv_series_highestrated);
        rvTvshowsLatest = view.findViewById(R.id.rv_series_latest);

        rvTvshowsPopular.setHasFixedSize(true);
        rvTvshowsHighRated.setHasFixedSize(true);
        rvTvshowsLatest.setHasFixedSize(true);

        showRecyclerView();
    }

    private void showRecyclerView() {

        rvTvshowsPopular.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvTvshowsPopular.setAdapter(adapter);

        rvTvshowsHighRated.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvTvshowsHighRated.setAdapter(tvShowHighRatedAdapter);

        rvTvshowsLatest.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvTvshowsLatest.setAdapter(tvShowLatestAdapter);

    }


    private void setTvshowData() {

        mainViewModel.getTvshowPopular().observe(this, getTvshowPopular);
        mainViewModel.setTVShowPopular();

        mainViewModel.getTvshowHighRated().observe(this, getTvshowHighRated);
        mainViewModel.setTVShowHighRated();

        mainViewModel.getTvshowLatest().observe(this, getTvshowLatest);
        mainViewModel.setTVShowLatest();

    }

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}
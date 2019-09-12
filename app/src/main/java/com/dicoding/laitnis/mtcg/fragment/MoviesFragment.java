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
import com.dicoding.laitnis.mtcg.adapter.MovieAdapter;
import com.dicoding.laitnis.mtcg.adapter.MovieITAdapter;
import com.dicoding.laitnis.mtcg.adapter.MoviePAdapter;
import com.dicoding.laitnis.mtcg.entity.Movie;
import com.dicoding.laitnis.mtcg.entity.MovieInTheater;
import com.dicoding.laitnis.mtcg.entity.MoviePopular;
import com.dicoding.laitnis.mtcg.viewmodel.MainViewModel;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class MoviesFragment extends Fragment {

    private RecyclerView rvMoviesUpcoming, rvMoviesInTheater, rvMoviesPopular;
    private MovieAdapter adapter;
    private MainViewModel mainViewModel;
    private MovieITAdapter mIAdapter;
    private MoviePAdapter mPAdapter;
    private ProgressBar progressBar;
    private Observer<ArrayList<Movie>> getMovieUpcoming = new Observer<ArrayList<Movie>>() {
        @Override
        public void onChanged(ArrayList<Movie> movies) {
            if (movies != null) {
                adapter.setMoviesData(getContext(), movies);
                showLoading(false);
            }
        }
    };

    private Observer<ArrayList<MovieInTheater>> getMovieInTheater = new Observer<ArrayList<MovieInTheater>>() {
        @Override
        public void onChanged(ArrayList<MovieInTheater> movieInTheaters) {
            if (movieInTheaters != null) {
                mIAdapter.setMoviesITData(getContext(), movieInTheaters);
                showLoading(false);
            }
        }
    };

    private Observer<ArrayList<MoviePopular>> getMoviePopular = new Observer<ArrayList<MoviePopular>>() {
        @Override
        public void onChanged(ArrayList<MoviePopular> moviePopulars) {
            if (moviePopulars != null) {
                mPAdapter.setMoviesPData(getContext(), moviePopulars);
                showLoading(false);
            }
        }
    };


    public MoviesFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_movies, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        progressBar = view.findViewById(R.id.progressBar);

        mainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);

        setMoviesData();

        showLoading(true);

        adapter = new MovieAdapter();
        adapter.notifyDataSetChanged();

        mIAdapter = new MovieITAdapter();
        mIAdapter.notifyDataSetChanged();

        mPAdapter = new MoviePAdapter();
        mPAdapter.notifyDataSetChanged();

        rvMoviesUpcoming = view.findViewById(R.id.rv_movies_upcoming);
        rvMoviesUpcoming.setHasFixedSize(true);

        rvMoviesInTheater = view.findViewById(R.id.rv_movies_intheater);
        rvMoviesInTheater.setHasFixedSize(true);

        rvMoviesPopular = view.findViewById(R.id.rv_movies_popular);
        rvMoviesPopular.setHasFixedSize(true);

        showRecyclerView();
    }

    private void setMoviesData() {
        mainViewModel.getMoviesUpcoming().observe(this, getMovieUpcoming);
        mainViewModel.setMoviesUpcoming();

        mainViewModel.getMoviesInTheater().observe(this, getMovieInTheater);
        mainViewModel.setMoviesInTheater();

        mainViewModel.getMoviesPopular().observe(this, getMoviePopular);
        mainViewModel.setMoviesPopular();
    }

    private void showRecyclerView() {

        rvMoviesUpcoming.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvMoviesUpcoming.setAdapter(adapter);

        rvMoviesInTheater.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvMoviesInTheater.setAdapter(mIAdapter);

        rvMoviesPopular.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        rvMoviesPopular.setAdapter(mPAdapter);

    }

    private void showLoading(boolean state) {
        if (state) {
            progressBar.setVisibility(View.VISIBLE);
        } else {
            progressBar.setVisibility(View.GONE);
        }
    }

}

package com.dicoding.laitnis.mtcg.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.laitnis.mtcg.entity.Movie;
import com.dicoding.laitnis.mtcg.entity.MovieInTheater;
import com.dicoding.laitnis.mtcg.entity.MoviePopular;
import com.dicoding.laitnis.mtcg.entity.TVShow;
import com.dicoding.laitnis.mtcg.entity.TVShowHighRated;
import com.dicoding.laitnis.mtcg.entity.TVShowLatest;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class MainViewModel extends ViewModel {
    private static final String API_KEY = "Insert yout TMDB API key here!";
    private MutableLiveData<ArrayList<Movie>> listMoviesUpcoming = new MutableLiveData<>();
    private MutableLiveData<ArrayList<MovieInTheater>> listMoviesInTheater = new MutableLiveData<>();
    private MutableLiveData<ArrayList<MoviePopular>> listMoviesPopular = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TVShow>> listTvshowsPopular = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TVShowHighRated>> listTvshowsHighRated = new MutableLiveData<>();
    private MutableLiveData<ArrayList<TVShowLatest>> listTvshowsLatest = new MutableLiveData<>();

    private String locale, lang;

    private void setLang() {
        locale = Locale.getDefault().getDisplayLanguage();

        if (locale.contains("English")) {
            lang = "en";
        } else if (locale.contains("Indonesia")) {
            lang = "id";
        }
    }

    public void setMoviesUpcoming() {

        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Movie> listMovieUpcoming = new ArrayList<>();

        setLang();

        String url = "https://api.themoviedb.org/3/movie/upcoming?api_key=" + API_KEY + "&language=" + lang + "&page=1";
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray results = responseObject.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject movie = results.getJSONObject(i);
                        Movie movies = new Movie(movie);
                        listMovieUpcoming.add(movies);
                    }
                    listMoviesUpcoming.postValue(listMovieUpcoming);
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<Movie>> getMoviesUpcoming() {
        return listMoviesUpcoming;
    }

    public void setMoviesInTheater() {

        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MovieInTheater> listMovieInTheater = new ArrayList<>();

        setLang();

        String url = "https://api.themoviedb.org/3/movie/now_playing?api_key=" + API_KEY + "&language=" + lang + "&page=1";
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray results = responseObject.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject movie = results.getJSONObject(i);
                        MovieInTheater movies = new MovieInTheater(movie);
                        listMovieInTheater.add(movies);
                    }
                    listMoviesInTheater.postValue(listMovieInTheater);
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<MovieInTheater>> getMoviesInTheater() {
        return listMoviesInTheater;
    }

    public void setMoviesPopular() {

        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<MoviePopular> listMoviePopular = new ArrayList<>();

        setLang();

        String url = "https://api.themoviedb.org/3/movie/popular?api_key=" + API_KEY + "&language=" + lang + "&page=1";
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray results = responseObject.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject movie = results.getJSONObject(i);
                        MoviePopular movies = new MoviePopular(movie);
                        listMoviePopular.add(movies);
                    }
                    listMoviesPopular.postValue(listMoviePopular);
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<MoviePopular>> getMoviesPopular() {
        return listMoviesPopular;
    }

    public void setTVShowPopular() {

        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TVShow> listTvshowPopular = new ArrayList<>();

        setLang();

        String url = "https://api.themoviedb.org/3/tv/popular?api_key=" + API_KEY + "&language=" + lang + "&page=1";
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray results = responseObject.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject tvshow = results.getJSONObject(i);
                        TVShow tvshows = new TVShow(tvshow);
                        listTvshowPopular.add(tvshows);
                    }
                    listTvshowsPopular.postValue(listTvshowPopular);
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<TVShow>> getTvshowPopular() {
        return listTvshowsPopular;
    }

    public void setTVShowHighRated() {

        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TVShowHighRated> listTvshowHighRated = new ArrayList<>();

        setLang();

        String url = "https://api.themoviedb.org/3/tv/top_rated?api_key=" + API_KEY + "&language=" + lang + "&page=1";
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray results = responseObject.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject tvshow = results.getJSONObject(i);
                        TVShowHighRated tvshows = new TVShowHighRated(tvshow);
                        listTvshowHighRated.add(tvshows);
                    }
                    listTvshowsHighRated.postValue(listTvshowHighRated);
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<TVShowHighRated>> getTvshowHighRated() {
        return listTvshowsHighRated;
    }

    public void setTVShowLatest() {

        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<TVShowLatest> listTvshowLatest = new ArrayList<>();

        setLang();

        String url = "https://api.themoviedb.org/3/tv/on_the_air?api_key=" + API_KEY + "&language=" + lang + "&page=1";
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray results = responseObject.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject tvshow = results.getJSONObject(i);
                        TVShowLatest tvshows = new TVShowLatest(tvshow);
                        listTvshowLatest.add(tvshows);
                    }
                    listTvshowsLatest.postValue(listTvshowLatest);
                } catch (Exception e) {
                    Log.e("Exception", e.getMessage());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Log.e("onFailure", error.getMessage());
            }
        });
    }

    public LiveData<ArrayList<TVShowLatest>> getTvshowLatest() {
        return listTvshowsLatest;
    }

}

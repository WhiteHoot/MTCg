package com.dicoding.laitnis.mtcg.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.laitnis.mtcg.entity.Cast;
import com.dicoding.laitnis.mtcg.entity.Genre;
import com.dicoding.laitnis.mtcg.entity.Video;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TvshowDetailViewModel extends ViewModel {


    private static final String API_KEY = "Insert yout TMDB API key here!";
    private MutableLiveData<ArrayList<Video>> listVideos = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Genre>> listGenres = new MutableLiveData<>();
    private MutableLiveData<ArrayList<Cast>> listCasts = new MutableLiveData<>();


    public LiveData<ArrayList<Video>> getVideo() {
        return listVideos;
    }

    public void setVideo(final String seriesId) {

        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Video> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/tv/" + seriesId + "/videos?api_key=" + API_KEY + "&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray results = responseObject.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject videos = results.getJSONObject(i);
                        Video video = new Video(videos);
                        listItems.add(video);
                    }
                    listVideos.postValue(listItems);
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

    public LiveData<ArrayList<Genre>> getGenre() {
        return listGenres;
    }

    public void setGenre(final String movId) {

        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Genre> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/tv/" + movId + "?api_key=" + API_KEY + "&language=en-US";
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray results = responseObject.getJSONArray("genres");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject genres = results.getJSONObject(i);
                        Genre genre = new Genre(genres);
                        listItems.add(genre);
                    }
                    listGenres.postValue(listItems);
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

    public LiveData<ArrayList<Cast>> getCast() {
        return listCasts;
    }

    public void setCast(final String movId) {

        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<Cast> listItems = new ArrayList<>();
        String url = "https://api.themoviedb.org/3/tv/" + movId + "/credits?api_key=" + API_KEY;
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray results = responseObject.getJSONArray("cast");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject casts = results.getJSONObject(i);
                        Cast cast = new Cast(casts);
                        listItems.add(cast);
                    }
                    listCasts.postValue(listItems);
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
}

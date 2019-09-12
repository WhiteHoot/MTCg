package com.dicoding.laitnis.mtcg.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.dicoding.laitnis.mtcg.entity.SearchData;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

import cz.msebera.android.httpclient.Header;

public class SearchViewModel extends ViewModel {

    private static final String API_KEY = "d2dfaf3ce527b24d57d284fe5bf270f5";
    private MutableLiveData<ArrayList<SearchData>> listResults = new MutableLiveData<>();

    private String locale, lang;

    private void setLang() {
        locale = Locale.getDefault().getDisplayLanguage();

        if (locale.contains("English")) {
            lang = "en";
        } else if (locale.contains("Indonesia")) {
            lang = "id";
        }
    }

    public LiveData<ArrayList<SearchData>> getResults() {
        return listResults;
    }

    public void setResults(final String query) {

        AsyncHttpClient client = new AsyncHttpClient();
        final ArrayList<SearchData> listItems = new ArrayList<>();

        setLang();

        String url = "https://api.themoviedb.org/3/search/multi?api_key=" + API_KEY + "&language=" + lang + "&query=" + query + "&page=1&include_adult=true";
        client.get(url, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    String result = new String(responseBody);
                    JSONObject responseObject = new JSONObject(result);
                    JSONArray results = responseObject.getJSONArray("results");
                    for (int i = 0; i < results.length(); i++) {
                        JSONObject search = results.getJSONObject(i);
                        SearchData searches = new SearchData(search);
                        listItems.add(searches);
                    }
                    listResults.postValue(listItems);
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

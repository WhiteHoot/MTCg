package com.dicoding.laitnis.mtcg.helper;

import android.database.Cursor;

import com.dicoding.laitnis.mtcg.entity.TVShow;

import java.util.ArrayList;

import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.DATE;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.DESCRIPTION;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.POSTER;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.POSTERBG;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.RATING;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.TITLE;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns._ID;

public class TvshowMappingHelper {

    public static ArrayList<TVShow> mapTvshowCursorToArrayList(Cursor cursor) {
        ArrayList<TVShow> tvShows = new ArrayList<>();

        while (cursor.moveToNext()) {
            String seriesTitle = cursor.getString(cursor.getColumnIndexOrThrow(TITLE));
            String seriesDesc = cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION));
            String seriesYear = cursor.getString(cursor.getColumnIndexOrThrow(DATE));
            String seriesPoster = cursor.getString(cursor.getColumnIndexOrThrow(POSTER));
            String seriesPosterBg = cursor.getString(cursor.getColumnIndexOrThrow(POSTERBG));
            int seriesId = cursor.getInt(cursor.getColumnIndexOrThrow(_ID));
            double seriesRating = cursor.getDouble(cursor.getColumnIndexOrThrow(RATING));
            tvShows.add(new TVShow(seriesTitle, seriesDesc, seriesYear, seriesPoster, seriesPosterBg, seriesId, seriesRating));
        }
        cursor.close();
        return tvShows;
    }

}

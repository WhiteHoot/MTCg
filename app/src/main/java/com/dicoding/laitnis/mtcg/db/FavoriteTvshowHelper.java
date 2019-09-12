package com.dicoding.laitnis.mtcg.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dicoding.laitnis.mtcg.entity.TVShow;

import java.util.ArrayList;

import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.DATE;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.DESCRIPTION;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.POSTER;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.POSTERBG;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.RATING;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.TITLE;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns._ID;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.TABLE_FAVORITE_TVSHOW;

public class FavoriteTvshowHelper {

    private static final String DATABASE_TABLE = TABLE_FAVORITE_TVSHOW;
    private static DatabaseHelper databaseHelper;
    private static FavoriteTvshowHelper INSTANCE;

    private static SQLiteDatabase database;

    private FavoriteTvshowHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteTvshowHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteTvshowHelper(context);
                }
            }
        }
        return INSTANCE;
    }

    //Open db connection
    public void open() throws SQLException {
        database = databaseHelper.getWritableDatabase();
    }

    //Close db connection
    public void close() {
        databaseHelper.close();

        if (database.isOpen())
            database.close();
    }

    public ArrayList<TVShow> getTVshowsFavorites() {
        ArrayList<TVShow> TvshowArrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        TVShow tvShow;
        if (cursor.getCount() > 0) {
            do {
                tvShow = new TVShow();
                tvShow.setSeriesId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                tvShow.setSeriesTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                tvShow.setSeriesDesc(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                tvShow.setSeriesYear(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                tvShow.setSeriesRating(cursor.getDouble(cursor.getColumnIndexOrThrow(RATING)));
                tvShow.setSeriesPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                tvShow.setSeriesPosterBg(cursor.getString(cursor.getColumnIndexOrThrow(POSTERBG)));

                TvshowArrayList.add(tvShow);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return TvshowArrayList;
    }

    public TVShow getTVshowsFavoritesById(int id) {
        Cursor cursor = database.query(DATABASE_TABLE, null,
                _ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        TVShow tvShow = new TVShow();
        if (cursor.getCount() > 0) {
            do {
                tvShow.setSeriesId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                tvShow.setSeriesTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                tvShow.setSeriesDesc(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                tvShow.setSeriesYear(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                tvShow.setSeriesRating(cursor.getDouble(cursor.getColumnIndexOrThrow(RATING)));
                tvShow.setSeriesPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                tvShow.setSeriesPosterBg(cursor.getString(cursor.getColumnIndexOrThrow(POSTERBG)));

                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return tvShow;
    }

    public long insertTvshow(TVShow tvShow) {
        ContentValues args = new ContentValues();
        args.put(_ID, tvShow.getSeriesId());
        args.put(TITLE, tvShow.getSeriesTitle());
        args.put(DESCRIPTION, tvShow.getSeriesDesc());
        args.put(DATE, tvShow.getSeriesYear());
        args.put(RATING, tvShow.getSeriesRating());
        args.put(POSTER, tvShow.getSeriesPoster());
        args.put(POSTERBG, tvShow.getSeriesPosterBg());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteFavorite(int id) {
        return database.delete(TABLE_FAVORITE_TVSHOW, _ID + " = '" + id + "'", null);
    }

    public Cursor queryByIdProvider(String id) {
        return database.query(DATABASE_TABLE, null
                , _ID + " = ?"
                , new String[]{id}
                , null
                , null
                , null
                , null);
    }

    public Cursor queryProvider() {
        return database.query(DATABASE_TABLE
                , null
                , null
                , null
                , null
                , null
                , _ID + " ASC");
    }
}

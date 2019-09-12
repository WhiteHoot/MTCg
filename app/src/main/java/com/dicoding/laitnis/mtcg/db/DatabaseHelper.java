package com.dicoding.laitnis.mtcg.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 2;
    private static final String SQL_CREATE_TABLE_FAVORITE_MOVIE = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s DOUBLE NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_FAVORITE_MOVIE,
            DatabaseContract.FavoriteMovieColumns._ID,
            DatabaseContract.FavoriteMovieColumns.TITLE,
            DatabaseContract.FavoriteMovieColumns.DESCRIPTION,
            DatabaseContract.FavoriteMovieColumns.DATE,
            DatabaseContract.FavoriteMovieColumns.RATING,
            DatabaseContract.FavoriteMovieColumns.POSTER,
            DatabaseContract.FavoriteMovieColumns.POSTERBG
    );
    private static final String SQL_CREATE_TABLE_FAVORITE_TVSHOW = String.format("CREATE TABLE %s"
                    + " (%s INTEGER PRIMARY KEY AUTOINCREMENT," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s DOUBLE NOT NULL," +
                    " %s TEXT NOT NULL," +
                    " %s TEXT NOT NULL)",
            DatabaseContract.TABLE_FAVORITE_TVSHOW,
            DatabaseContract.FavoriteTvshowColumns._ID,
            DatabaseContract.FavoriteTvshowColumns.TITLE,
            DatabaseContract.FavoriteTvshowColumns.DESCRIPTION,
            DatabaseContract.FavoriteTvshowColumns.DATE,
            DatabaseContract.FavoriteTvshowColumns.RATING,
            DatabaseContract.FavoriteTvshowColumns.POSTER,
            DatabaseContract.FavoriteTvshowColumns.POSTERBG
    );
    public static String DATABASE_NAME = "db_mtcg";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE_FAVORITE_MOVIE);
        db.execSQL(SQL_CREATE_TABLE_FAVORITE_TVSHOW);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_FAVORITE_MOVIE);
        db.execSQL("DROP TABLE IF EXISTS " + DatabaseContract.TABLE_FAVORITE_TVSHOW);
        onCreate(db);
    }
}

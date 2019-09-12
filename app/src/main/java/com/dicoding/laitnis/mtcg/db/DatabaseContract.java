package com.dicoding.laitnis.mtcg.db;

import android.database.Cursor;
import android.net.Uri;
import android.provider.BaseColumns;

public class DatabaseContract {

    public static final String AUTHORITY1 = "com.dicoding.laitnis.mtcg.provider.FavoriteMovieProvider";
    public static final String AUTHORITY2 = "com.dicoding.laitnis.mtcg.provider.FavoriteTvshowProvider";
    public static final String SCHEME = "content";

    public static String TABLE_FAVORITE_MOVIE = "favorite_movie";
    public static String TABLE_FAVORITE_TVSHOW = "favorite_tvshow";

    public static String getColumnString(Cursor cursor, String columnName) {
        return cursor.getString(cursor.getColumnIndex(columnName));
    }

    public static int getColumnInt(Cursor cursor, String columnName) {
        return cursor.getInt(cursor.getColumnIndex(columnName));
    }

    public static long getColumnLong(Cursor cursor, String columnName) {
        return cursor.getLong(cursor.getColumnIndex(columnName));
    }

    public static final class FavoriteMovieColumns implements BaseColumns {

        public static final Uri MOVIE_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY1)
                .appendPath(TABLE_FAVORITE_MOVIE)
                .build();
        public static String _ID = "id";
        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String DATE = "date";
        public static String RATING = "rating";
        public static String POSTER = "poster";
        public static String POSTERBG = "posterbg";

    }

    public static final class FavoriteTvshowColumns implements BaseColumns {

        public static final Uri TVSHOW_URI = new Uri.Builder().scheme(SCHEME)
                .authority(AUTHORITY2)
                .appendPath(TABLE_FAVORITE_TVSHOW)
                .build();
        public static String _ID = "id";
        public static String TITLE = "title";
        public static String DESCRIPTION = "description";
        public static String DATE = "date";
        public static String RATING = "rating";
        public static String POSTER = "poster";
        public static String POSTERBG = "posterbg";

    }

}

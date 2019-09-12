package com.dicoding.laitnis.mtcg.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.dicoding.laitnis.mtcg.entity.Movie;

import java.util.ArrayList;

import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.DATE;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.DESCRIPTION;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.POSTER;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.POSTERBG;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.RATING;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.TITLE;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns._ID;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.TABLE_FAVORITE_MOVIE;

public class FavoriteMovieHelper {

    private static final String DATABASE_TABLE = TABLE_FAVORITE_MOVIE;
    private static DatabaseHelper databaseHelper;
    private static FavoriteMovieHelper INSTANCE;

    private static SQLiteDatabase database;

    private FavoriteMovieHelper(Context context) {
        databaseHelper = new DatabaseHelper(context);
    }

    public static FavoriteMovieHelper getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (SQLiteOpenHelper.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FavoriteMovieHelper(context);
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

    public ArrayList<Movie> getMoviesFavorites() {
        ArrayList<Movie> movieArrayList = new ArrayList<>();
        Cursor cursor = database.query(DATABASE_TABLE, null,
                null,
                null,
                null,
                null,
                _ID + " ASC",
                null);
        cursor.moveToFirst();
        Movie movie;
        if (cursor.getCount() > 0) {
            do {
                movie = new Movie();
                movie.setMovId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setMovTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setMovDesc(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movie.setMovYear(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                movie.setMovRating(cursor.getDouble(cursor.getColumnIndexOrThrow(RATING)));
                movie.setMovPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                movie.setMovPosterBg(cursor.getString(cursor.getColumnIndexOrThrow(POSTERBG)));

                movieArrayList.add(movie);
                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();
        return movieArrayList;
    }

    public Movie getMoviesFavoritesById(int id) {
        Cursor cursor = database.query(DATABASE_TABLE, null,
                _ID + " = ?",
                new String[]{String.valueOf(id)},
                null,
                null,
                null,
                null);
        cursor.moveToFirst();
        Movie movie = new Movie();
        if (cursor.getCount() > 0) {
            do {
                movie.setMovId(cursor.getInt(cursor.getColumnIndexOrThrow(_ID)));
                movie.setMovTitle(cursor.getString(cursor.getColumnIndexOrThrow(TITLE)));
                movie.setMovDesc(cursor.getString(cursor.getColumnIndexOrThrow(DESCRIPTION)));
                movie.setMovYear(cursor.getString(cursor.getColumnIndexOrThrow(DATE)));
                movie.setMovRating(cursor.getDouble(cursor.getColumnIndexOrThrow(RATING)));
                movie.setMovPoster(cursor.getString(cursor.getColumnIndexOrThrow(POSTER)));
                movie.setMovPosterBg(cursor.getString(cursor.getColumnIndexOrThrow(POSTERBG)));

                cursor.moveToNext();
            } while (!cursor.isAfterLast());
        }
        cursor.close();

        return movie;
    }

    public long insertMovie(Movie movie) {
        ContentValues args = new ContentValues();
        args.put(_ID, movie.getMovId());
        args.put(TITLE, movie.getMovTitle());
        args.put(DESCRIPTION, movie.getMovDesc());
        args.put(DATE, movie.getMovYear());
        args.put(RATING, movie.getMovRating());
        args.put(POSTER, movie.getMovPoster());
        args.put(POSTERBG, movie.getMovPosterBg());
        return database.insert(DATABASE_TABLE, null, args);
    }

    public int deleteFavorite(int id) {
        return database.delete(TABLE_FAVORITE_MOVIE, _ID + " = '" + id + "'", null);
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

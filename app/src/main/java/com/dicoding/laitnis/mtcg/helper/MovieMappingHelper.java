package com.dicoding.laitnis.mtcg.helper;

import android.database.Cursor;

import com.dicoding.laitnis.mtcg.entity.Movie;

import java.util.ArrayList;

import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.DATE;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.DESCRIPTION;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.POSTER;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.POSTERBG;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.RATING;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.TITLE;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns._ID;

public class MovieMappingHelper {

    public static ArrayList<Movie> mapMovieCursorToArrayList(Cursor movieCursor) {
        ArrayList<Movie> movies = new ArrayList<>();

        while (movieCursor.moveToNext()) {
            String movTitle = movieCursor.getString(movieCursor.getColumnIndexOrThrow(TITLE));
            String movDesc = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DESCRIPTION));
            String movYear = movieCursor.getString(movieCursor.getColumnIndexOrThrow(DATE));
            String movPoster = movieCursor.getString(movieCursor.getColumnIndexOrThrow(POSTER));
            String movPosterBg = movieCursor.getString(movieCursor.getColumnIndexOrThrow(POSTERBG));
            int movId = movieCursor.getInt(movieCursor.getColumnIndexOrThrow(_ID));
            double movRating = movieCursor.getDouble(movieCursor.getColumnIndexOrThrow(RATING));
            movies.add(new Movie(movTitle, movDesc, movYear, movPoster, movPosterBg, movId, movRating));
        }
        movieCursor.close();
        return movies;
    }

}

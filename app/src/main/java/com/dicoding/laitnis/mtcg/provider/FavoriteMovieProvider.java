package com.dicoding.laitnis.mtcg.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.dicoding.laitnis.mtcg.db.FavoriteMovieHelper;

import static com.dicoding.laitnis.mtcg.db.DatabaseContract.AUTHORITY1;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.TABLE_FAVORITE_MOVIE;

public class FavoriteMovieProvider extends ContentProvider {

    private static final int MOVIE = 1;
    private static final int MOVIE_ID = 2;
    private static final UriMatcher mUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        mUriMatcher.addURI(AUTHORITY1, TABLE_FAVORITE_MOVIE, MOVIE);
        mUriMatcher.addURI(AUTHORITY1, TABLE_FAVORITE_MOVIE + "/#", MOVIE_ID);
    }

    private FavoriteMovieHelper favoriteMovieHelper;

    @Override
    public boolean onCreate() {
        favoriteMovieHelper = FavoriteMovieHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        favoriteMovieHelper.open();
        Cursor cursor;
        switch (mUriMatcher.match(uri)) {
            case MOVIE:
                cursor = favoriteMovieHelper.queryProvider();
                break;
            case MOVIE_ID:
                cursor = favoriteMovieHelper.queryByIdProvider(uri.getLastPathSegment());
                break;
            default:
                cursor = null;
                break;
        }
        return cursor;
    }

    @Override
    public String getType(Uri uri) {
        return null;
    }

    @Override
    public Uri insert(Uri uri, ContentValues contentValues) {
        return null;
    }

    @Override
    public int delete(Uri uri, String s, String[] strings) {
        return 0;
    }

    @Override
    public int update(Uri uri, ContentValues contentValues, String s, String[] strings) {
        return 0;
    }
}

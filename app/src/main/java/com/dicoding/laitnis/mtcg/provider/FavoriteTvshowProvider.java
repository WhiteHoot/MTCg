package com.dicoding.laitnis.mtcg.provider;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.UriMatcher;
import android.database.Cursor;
import android.net.Uri;

import com.dicoding.laitnis.mtcg.db.FavoriteTvshowHelper;

import static com.dicoding.laitnis.mtcg.db.DatabaseContract.AUTHORITY2;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.TABLE_FAVORITE_TVSHOW;

public class FavoriteTvshowProvider extends ContentProvider {

    private static final int TVSHOW = 1;
    private static final int TVSHOW_ID = 2;
    private static final UriMatcher tUriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

    static {
        tUriMatcher.addURI(AUTHORITY2, TABLE_FAVORITE_TVSHOW, TVSHOW);
        tUriMatcher.addURI(AUTHORITY2, TABLE_FAVORITE_TVSHOW + "/#", TVSHOW_ID);
    }

    private FavoriteTvshowHelper favoriteTvshowHelper;

    @Override
    public boolean onCreate() {
        favoriteTvshowHelper = FavoriteTvshowHelper.getInstance(getContext());
        return true;
    }

    @Override
    public Cursor query(Uri uri, String[] strings, String s, String[] strings1, String s1) {
        favoriteTvshowHelper.open();
        Cursor cursor;
        switch (tUriMatcher.match(uri)) {
            case TVSHOW:
                cursor = favoriteTvshowHelper.queryProvider();
                break;
            case TVSHOW_ID:
                cursor = favoriteTvshowHelper.queryByIdProvider(uri.getLastPathSegment());
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

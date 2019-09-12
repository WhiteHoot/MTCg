package com.dicoding.laitnis.mtcg.widget;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Binder;
import android.os.Bundle;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.bumptech.glide.Glide;
import com.dicoding.laitnis.mtcg.R;
import com.dicoding.laitnis.mtcg.entity.Movie;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.MOVIE_URI;
import static com.dicoding.laitnis.mtcg.helper.MovieMappingHelper.mapMovieCursorToArrayList;

public class StackRemoteViewsFactory implements RemoteViewsService.RemoteViewsFactory {

    private final Context mContext;
    private ArrayList<Movie> listMovies = new ArrayList<>();
    private Cursor cursor;

    StackRemoteViewsFactory(Context context) {
        mContext = context;
    }

    @Override
    public void onCreate() {
    }

    @Override
    public void onDataSetChanged() {
        if (cursor != null) {
            cursor.close();
        }
        final long identityToken = Binder.clearCallingIdentity();
        cursor = mContext.getContentResolver().query(MOVIE_URI, null, null, null, null);
        Binder.restoreCallingIdentity(identityToken);
        listMovies = mapMovieCursorToArrayList(cursor);
    }

    @Override
    public void onDestroy() {
        cursor.close();
    }

    @Override
    public int getCount() {
        return listMovies.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        final RemoteViews rv = new RemoteViews(mContext.getPackageName(), R.layout.widget_item);

        String movPoster = listMovies.get(position).getMovPoster();

        try {
            Bitmap poster = Glide.with(mContext)
                    .asBitmap()
                    .load(movPoster)
                    .submit()
                    .get();
            rv.setImageViewBitmap(R.id.imageView, poster);
        } catch (ExecutionException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        Bundle extras = new Bundle();
        extras.putInt(FavoriteMovieWidget.EXTRA_ITEM, position);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);

        rv.setOnClickFillInIntent(R.id.imageView, fillIntent);
        return rv;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}

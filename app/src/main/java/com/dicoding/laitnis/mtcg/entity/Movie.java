package com.dicoding.laitnis.mtcg.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.DATE;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.DESCRIPTION;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.POSTER;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.POSTERBG;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.RATING;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns.TITLE;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteMovieColumns._ID;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.getColumnInt;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.getColumnString;

public class Movie implements Parcelable {

    public static final Creator<Movie> CREATOR = new Creator<Movie>() {
        @Override
        public Movie createFromParcel(Parcel source) {
            return new Movie(source);
        }

        @Override
        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
    private String movTitle, movDesc, movYear, movPoster, movPosterBg;
    private int movId;
    private double movRating;

    public Movie(JSONObject object) {
        try {
            int id = object.getInt("id");
            String title = object.getString("title");
            String description = object.getString("overview");
            String date = object.getString("release_date");
            double rating = object.getDouble("vote_average");
            String poster = object.getString("poster_path");
            String backdrop = object.getString("backdrop_path");

            this.movId = id;
            this.movTitle = title;
            this.movDesc = description;
            this.movYear = date;
            this.movRating = rating;
            this.movPoster = poster;
            this.movPosterBg = backdrop;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Movie() {

    }

    protected Movie(Parcel in) {
        this.movTitle = in.readString();
        this.movDesc = in.readString();
        this.movYear = in.readString();
        this.movPoster = in.readString();
        this.movPosterBg = in.readString();
        this.movId = in.readInt();
        this.movRating = in.readDouble();
    }

    public Movie(String movTitle, String movDesc, String movYear, String movPoster, String movPosterBg, int movId, double movRating) {
        this.movTitle = movTitle;
        this.movDesc = movDesc;
        this.movYear = movYear;
        this.movPoster = movPoster;
        this.movPosterBg = movPosterBg;
        this.movId = movId;
        this.movRating = movRating;
    }

    public Movie(Cursor cursor) {
        this.movTitle = getColumnString(cursor, TITLE);
        this.movDesc = getColumnString(cursor, DESCRIPTION);
        this.movYear = getColumnString(cursor, DATE);
        this.movPoster = getColumnString(cursor, POSTER);
        this.movPosterBg = getColumnString(cursor, POSTERBG);
        this.movId = getColumnInt(cursor, _ID);
        this.movRating = getColumnInt(cursor, RATING);
    }

    public String getMovTitle() {
        return movTitle;
    }

    public void setMovTitle(String movTitle) {
        this.movTitle = movTitle;
    }

    public String getMovDesc() {
        return movDesc;
    }

    public void setMovDesc(String movDesc) {
        this.movDesc = movDesc;
    }

    public String getMovYear() {
        return movYear;
    }

    public void setMovYear(String movYear) {
        this.movYear = movYear;
    }

    public String getMovPoster() {
        return movPoster;
    }

    public void setMovPoster(String movPoster) {
        this.movPoster = movPoster;
    }

    public String getMovPosterBg() {
        return movPosterBg;
    }

    public void setMovPosterBg(String movPosterBg) {
        this.movPosterBg = movPosterBg;
    }

    public int getMovId() {
        return movId;
    }

    public void setMovId(int movId) {
        this.movId = movId;
    }

    public double getMovRating() {
        return movRating;
    }

    public void setMovRating(double movRating) {
        this.movRating = movRating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.movTitle);
        dest.writeString(this.movDesc);
        dest.writeString(this.movYear);
        dest.writeString(this.movPoster);
        dest.writeString(this.movPosterBg);
        dest.writeInt(this.movId);
        dest.writeDouble(this.movRating);
    }
}


package com.dicoding.laitnis.mtcg.entity;

import android.database.Cursor;
import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.DATE;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.DESCRIPTION;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.POSTER;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.POSTERBG;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.RATING;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns.TITLE;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.FavoriteTvshowColumns._ID;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.getColumnInt;
import static com.dicoding.laitnis.mtcg.db.DatabaseContract.getColumnString;

public class TVShow implements Parcelable {

    public static final Creator<TVShow> CREATOR = new Creator<TVShow>() {
        @Override
        public TVShow createFromParcel(Parcel source) {
            return new TVShow(source);
        }

        @Override
        public TVShow[] newArray(int size) {
            return new TVShow[size];
        }
    };
    private String seriesTitle, seriesDesc, seriesYear, seriesPoster, seriesPosterBg;
    private int seriesId;
    private double seriesRating;

    public TVShow(JSONObject object) {
        try {
            int id = object.getInt("id");
            String title = object.getString("name");
            String description = object.getString("overview");
            String date = object.getString("first_air_date");
            double rating = object.getDouble("vote_average");
            String poster = object.getString("poster_path");
            String backdrop = object.getString("backdrop_path");

            this.seriesId = id;
            this.seriesTitle = title;
            this.seriesDesc = description;
            this.seriesYear = date;
            this.seriesRating = rating;
            this.seriesPoster = poster;
            this.seriesPosterBg = backdrop;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TVShow() {

    }

    protected TVShow(Parcel in) {
        this.seriesTitle = in.readString();
        this.seriesDesc = in.readString();
        this.seriesYear = in.readString();
        this.seriesPoster = in.readString();
        this.seriesPosterBg = in.readString();
        this.seriesId = in.readInt();
        this.seriesRating = in.readDouble();
    }

    public TVShow(String seriesTitle, String seriesDesc, String seriesYear, String seriesPoster, String seriesPosterBg, int seriesId, double seriesRating) {
        this.seriesTitle = seriesTitle;
        this.seriesDesc = seriesDesc;
        this.seriesYear = seriesYear;
        this.seriesPoster = seriesPoster;
        this.seriesPosterBg = seriesPosterBg;
        this.seriesId = seriesId;
        this.seriesRating = seriesRating;
    }

    public TVShow(Cursor cursor) {
        this.seriesTitle = getColumnString(cursor, TITLE);
        this.seriesDesc = getColumnString(cursor, DESCRIPTION);
        this.seriesYear = getColumnString(cursor, DATE);
        this.seriesPoster = getColumnString(cursor, POSTER);
        this.seriesPosterBg = getColumnString(cursor, POSTERBG);
        this.seriesId = getColumnInt(cursor, _ID);
        this.seriesRating = getColumnInt(cursor, RATING);
    }

    public String getSeriesTitle() {
        return seriesTitle;
    }

    public void setSeriesTitle(String seriesTitle) {
        this.seriesTitle = seriesTitle;
    }

    public String getSeriesDesc() {
        return seriesDesc;
    }

    public void setSeriesDesc(String seriesDesc) {
        this.seriesDesc = seriesDesc;
    }

    public String getSeriesYear() {
        return seriesYear;
    }

    public void setSeriesYear(String seriesYear) {
        this.seriesYear = seriesYear;
    }

    public String getSeriesPoster() {
        return seriesPoster;
    }

    public void setSeriesPoster(String seriesPoster) {
        this.seriesPoster = seriesPoster;
    }

    public String getSeriesPosterBg() {
        return seriesPosterBg;
    }

    public void setSeriesPosterBg(String seriesPosterBg) {
        this.seriesPosterBg = seriesPosterBg;
    }

    public int getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    public double getSeriesRating() {
        return seriesRating;
    }

    public void setSeriesRating(double seriesRating) {
        this.seriesRating = seriesRating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.seriesTitle);
        dest.writeString(this.seriesDesc);
        dest.writeString(this.seriesYear);
        dest.writeString(this.seriesPoster);
        dest.writeString(this.seriesPosterBg);
        dest.writeInt(this.seriesId);
        dest.writeDouble(this.seriesRating);
    }
}

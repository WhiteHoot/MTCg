package com.dicoding.laitnis.mtcg.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class TVShowLatest implements Parcelable {

    public static final Parcelable.Creator<TVShowLatest> CREATOR = new Parcelable.Creator<TVShowLatest>() {
        @Override
        public TVShowLatest createFromParcel(Parcel source) {
            return new TVShowLatest(source);
        }

        @Override
        public TVShowLatest[] newArray(int size) {
            return new TVShowLatest[size];
        }
    };
    private String series_title, series_desc, series_year, series_poster, series_poster_bg;
    private int series_id;
    private double series_rating;

    public TVShowLatest(JSONObject object) {
        try {
            int id = object.getInt("id");
            String title = object.getString("name");
            String description = object.getString("overview");
            String date = object.getString("first_air_date");
            double rating = object.getDouble("vote_average");
            String poster = object.getString("poster_path");
            String backdrop = object.getString("backdrop_path");

            this.series_id = id;
            this.series_title = title;
            this.series_desc = description;
            this.series_year = date;
            this.series_rating = rating;
            this.series_poster = poster;
            this.series_poster_bg = backdrop;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public TVShowLatest() {

    }

    protected TVShowLatest(Parcel in) {
        this.series_title = in.readString();
        this.series_desc = in.readString();
        this.series_year = in.readString();
        this.series_poster = in.readString();
        this.series_poster_bg = in.readString();
        this.series_id = in.readInt();
        this.series_rating = in.readDouble();
    }

    public String getSeries_title() {
        return series_title;
    }

    public void setSeries_title(String series_title) {
        this.series_title = series_title;
    }

    public String getSeries_desc() {
        return series_desc;
    }

    public void setSeries_desc(String series_desc) {
        this.series_desc = series_desc;
    }

    public String getSeries_year() {
        return series_year;
    }

    public void setSeries_year(String series_year) {
        this.series_year = series_year;
    }

    public String getSeries_poster() {
        return series_poster;
    }

    public void setSeries_poster(String series_poster) {
        this.series_poster = series_poster;
    }

    public String getSeries_poster_bg() {
        return series_poster_bg;
    }

    public void setSeries_poster_bg(String series_poster_bg) {
        this.series_poster_bg = series_poster_bg;
    }

    public int getSeries_id() {
        return series_id;
    }

    public void setSeries_id(int series_id) {
        this.series_id = series_id;
    }

    public double getSeries_rating() {
        return series_rating;
    }

    public void setSeries_rating(double series_rating) {
        this.series_rating = series_rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.series_title);
        dest.writeString(this.series_desc);
        dest.writeString(this.series_year);
        dest.writeString(this.series_poster);
        dest.writeString(this.series_poster_bg);
        dest.writeInt(this.series_id);
        dest.writeDouble(this.series_rating);
    }

}

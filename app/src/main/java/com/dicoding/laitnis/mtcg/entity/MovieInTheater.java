package com.dicoding.laitnis.mtcg.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class MovieInTheater implements Parcelable {

    public static final Creator<MovieInTheater> CREATOR = new Creator<MovieInTheater>() {
        @Override
        public MovieInTheater createFromParcel(Parcel source) {
            return new MovieInTheater(source);
        }

        @Override
        public MovieInTheater[] newArray(int size) {
            return new MovieInTheater[size];
        }
    };
    private String mov_title, mov_desc, mov_year, mov_poster, mov_poster_bg;
    private int mov_id;
    private double mov_rating;

    public MovieInTheater(JSONObject object) {
        try {
            int id = object.getInt("id");
            String title = object.getString("title");
            String description = object.getString("overview");
            String date = object.getString("release_date");
            double rating = object.getDouble("vote_average");
            String poster = object.getString("poster_path");
            String backdrop = object.getString("backdrop_path");

            this.mov_id = id;
            this.mov_title = title;
            this.mov_desc = description;
            this.mov_year = date;
            this.mov_rating = rating;
            this.mov_poster = poster;
            this.mov_poster_bg = backdrop;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public MovieInTheater() {
    }

    protected MovieInTheater(Parcel in) {
        this.mov_title = in.readString();
        this.mov_desc = in.readString();
        this.mov_year = in.readString();
        this.mov_poster = in.readString();
        this.mov_poster_bg = in.readString();
        this.mov_id = in.readInt();
        this.mov_rating = in.readDouble();
    }

    public String getMov_title() {
        return mov_title;
    }

    public void setMov_title(String mov_title) {
        this.mov_title = mov_title;
    }

    public String getMov_desc() {
        return mov_desc;
    }

    public void setMov_desc(String mov_desc) {
        this.mov_desc = mov_desc;
    }

    public String getMov_year() {
        return mov_year;
    }

    public void setMov_year(String mov_year) {
        this.mov_year = mov_year;
    }

    public String getMov_poster() {
        return mov_poster;
    }

    public void setMov_poster(String mov_poster) {
        this.mov_poster = mov_poster;
    }

    public String getMov_poster_bg() {
        return mov_poster_bg;
    }

    public void setMov_poster_bg(String mov_poster_bg) {
        this.mov_poster_bg = mov_poster_bg;
    }

    public int getMov_id() {
        return mov_id;
    }

    public void setMov_id(int mov_id) {
        this.mov_id = mov_id;
    }

    public double getMov_rating() {
        return mov_rating;
    }

    public void setMov_rating(double mov_rating) {
        this.mov_rating = mov_rating;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.mov_title);
        dest.writeString(this.mov_desc);
        dest.writeString(this.mov_year);
        dest.writeString(this.mov_poster);
        dest.writeString(this.mov_poster_bg);
        dest.writeInt(this.mov_id);
        dest.writeDouble(this.mov_rating);
    }
}

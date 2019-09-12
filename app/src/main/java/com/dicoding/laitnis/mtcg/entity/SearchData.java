package com.dicoding.laitnis.mtcg.entity;

import android.os.Parcel;
import android.os.Parcelable;

import org.json.JSONObject;

public class SearchData implements Parcelable {

    public static final Creator<SearchData> CREATOR = new Creator<SearchData>() {
        @Override
        public SearchData createFromParcel(Parcel source) {
            return new SearchData(source);
        }

        @Override
        public SearchData[] newArray(int size) {
            return new SearchData[size];
        }
    };
    private String movieTitle, seriesName, movieDesc, seriesDesc,
            movieYear, seriesYear, moviePoster, seriesPoster,
            moviePosterbg, seriesPosterbg, profilePath, mediaType, personName;
    private int personId, movieId, seriesId;
    private double movieRating, seriesRating;


    public SearchData(JSONObject object) {
        try {
            int mid = object.getInt("id");
            String mtitle = object.getString("title");
            String mdescription = object.getString("overview");
            String mdate = object.getString("release_date");
            String itempType = object.getString("media_type");
            double mrating = object.getDouble("vote_average");
            String mposter = object.getString("poster_path");
            String mbackdrop = object.getString("backdrop_path");

            this.movieId = mid;
            this.movieTitle = mtitle;
            this.movieDesc = mdescription;
            this.movieYear = mdate;
            this.mediaType = itempType;
            this.movieRating = mrating;
            this.moviePoster = mposter;
            this.moviePosterbg = mbackdrop;
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            int sid = object.getInt("id");
            String sname = object.getString("name");
            String sdescription = object.getString("overview");
            String itemType = object.getString("media_type");
            String sdate = object.getString("first_air_date");
            double srating = object.getDouble("vote_average");
            String sposter = object.getString("poster_path");
            String sbackdrop = object.getString("backdrop_path");

            this.seriesId = sid;
            this.seriesName = sname;
            this.seriesDesc = sdescription;
            this.mediaType = itemType;
            this.seriesYear = sdate;
            this.seriesRating = srating;
            this.seriesPoster = sposter;
            this.seriesPosterbg = sbackdrop;
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            int pId = object.getInt("id");
            String itemType = object.getString("media_type");
            String pPoster = object.getString("profile_path");
            String pName = object.getString("name");

            this.personId = pId;
            this.mediaType = itemType;
            this.profilePath = pPoster;
            this.personName = pName;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected SearchData(Parcel in) {
        this.movieTitle = in.readString();
        this.seriesName = in.readString();
        this.movieDesc = in.readString();
        this.seriesDesc = in.readString();
        this.movieYear = in.readString();
        this.seriesYear = in.readString();
        this.moviePoster = in.readString();
        this.seriesPoster = in.readString();
        this.moviePosterbg = in.readString();
        this.seriesPosterbg = in.readString();
        this.profilePath = in.readString();
        this.mediaType = in.readString();
        this.personName = in.readString();
        this.personId = in.readInt();
        this.movieId = in.readInt();
        this.seriesId = in.readInt();
        this.movieRating = in.readDouble();
        this.seriesRating = in.readDouble();
    }

    public String getMovieTitle() {
        return movieTitle;
    }

    public void setMovieTitle(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getSeriesName() {
        return seriesName;
    }

    public void setSeriesName(String seriesName) {
        this.seriesName = seriesName;
    }

    public String getMovieDesc() {
        return movieDesc;
    }

    public void setMovieDesc(String movieDesc) {
        this.movieDesc = movieDesc;
    }

    public String getSeriesDesc() {
        return seriesDesc;
    }

    public void setSeriesDesc(String seriesDesc) {
        this.seriesDesc = seriesDesc;
    }

    public String getMovieYear() {
        return movieYear;
    }

    public void setMovieYear(String movieYear) {
        this.movieYear = movieYear;
    }

    public String getSeriesYear() {
        return seriesYear;
    }

    public void setSeriesYear(String seriesYear) {
        this.seriesYear = seriesYear;
    }

    public String getMoviePoster() {
        return moviePoster;
    }

    public void setMoviePoster(String moviePoster) {
        this.moviePoster = moviePoster;
    }

    public String getSeriesPoster() {
        return seriesPoster;
    }

    public void setSeriesPoster(String seriesPoster) {
        this.seriesPoster = seriesPoster;
    }

    public String getMoviePosterbg() {
        return moviePosterbg;
    }

    public void setMoviePosterbg(String moviePosterbg) {
        this.moviePosterbg = moviePosterbg;
    }

    public String getSeriesPosterbg() {
        return seriesPosterbg;
    }

    public void setSeriesPosterbg(String seriesPosterbg) {
        this.seriesPosterbg = seriesPosterbg;
    }

    public String getProfilePath() {
        return profilePath;
    }

    public void setProfilePath(String profilePath) {
        this.profilePath = profilePath;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public int getPersonId() {
        return personId;
    }

    public void setPersonId(int personId) {
        this.personId = personId;
    }

    public int getMovieId() {
        return movieId;
    }

    public void setMovieId(int movieId) {
        this.movieId = movieId;
    }

    public int getSeriesId() {
        return seriesId;
    }

    public void setSeriesId(int seriesId) {
        this.seriesId = seriesId;
    }

    public double getMovieRating() {
        return movieRating;
    }

    public void setMovieRating(double movieRating) {
        this.movieRating = movieRating;
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
        dest.writeString(this.movieTitle);
        dest.writeString(this.seriesName);
        dest.writeString(this.movieDesc);
        dest.writeString(this.seriesDesc);
        dest.writeString(this.movieYear);
        dest.writeString(this.seriesYear);
        dest.writeString(this.moviePoster);
        dest.writeString(this.seriesPoster);
        dest.writeString(this.moviePosterbg);
        dest.writeString(this.seriesPosterbg);
        dest.writeString(this.profilePath);
        dest.writeString(this.mediaType);
        dest.writeString(this.personName);
        dest.writeInt(this.personId);
        dest.writeInt(this.movieId);
        dest.writeInt(this.seriesId);
        dest.writeDouble(this.movieRating);
        dest.writeDouble(this.seriesRating);
    }
}

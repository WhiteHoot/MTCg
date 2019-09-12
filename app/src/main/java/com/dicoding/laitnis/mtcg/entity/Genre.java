package com.dicoding.laitnis.mtcg.entity;

import org.json.JSONObject;

public class Genre {

    private int genreId;
    private String genreName;

    public Genre(JSONObject object) {
        try {
            int id = object.getInt("id");
            String name = object.getString("name");

            this.genreId = id;
            this.genreName = name;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getGenreId() {
        return genreId;
    }

    public void setGenreId(int genreId) {
        this.genreId = genreId;
    }

    public String getGenreName() {
        return genreName;
    }

    public void setGenreName(String genreName) {
        this.genreName = genreName;
    }
}

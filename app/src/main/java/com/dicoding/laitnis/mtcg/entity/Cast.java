package com.dicoding.laitnis.mtcg.entity;

import org.json.JSONObject;

public class Cast {

    private String castCharacter, castName, castPhoto;

    public Cast(JSONObject object) {
        try {
            String name = object.getString("name");
            String character = object.getString("character");
            String photo = object.getString("profile_path");

            this.castName = name;
            this.castCharacter = character;
            this.castPhoto = photo;

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getCastCharacter() {
        return castCharacter;
    }

    public void setCastCharacter(String castCharacter) {
        this.castCharacter = castCharacter;
    }

    public String getCastName() {
        return castName;
    }

    public void setCastName(String castName) {
        this.castName = castName;
    }

    public String getCastPhoto() {
        return castPhoto;
    }

    public void setCastPhoto(String castPhoto) {
        this.castPhoto = castPhoto;
    }
}

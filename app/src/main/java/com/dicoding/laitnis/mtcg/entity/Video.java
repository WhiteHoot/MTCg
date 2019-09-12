package com.dicoding.laitnis.mtcg.entity;

import org.json.JSONObject;

public class Video {

    private String videoKey, videoSite;

    public Video(JSONObject object) {
        try {
            String key = object.getString("key");
            String site = object.getString("site");

            this.videoKey = key;
            this.videoSite = site;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public String getVideoKey() {
        return videoKey;
    }

    public void setVideoKey(String videoKey) {
        this.videoKey = videoKey;
    }

    public String getVideoSite() {
        return videoSite;
    }

    public void setVideoSite(String videoSite) {
        this.videoSite = videoSite;
    }
}

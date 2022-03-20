package com.example.fragments.Model.List;

public class AddMovieToList {
    String media_id;

    public AddMovieToList() {}

    public AddMovieToList(String media_id) {
        this.media_id = media_id;
    }

    public String getMedia_id() {
        return media_id;
    }

    public void setMedia_id(String media_id) {
        this.media_id = media_id;
    }
}

package com.example.musicgame.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Track {
    private String id;
    private String name;
    private String uri;
    private Album album;
    private Artist[] artists;

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Artist[] getArtists() {
        return artists;
    }

    public void setArtists(Artist[] artists) {
        this.artists = artists;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Album {
        private String release_date;

        // Getters and setters
        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Artist {
        private String name;

        // Getters and setters
        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}

package com.example.musicgame.model;

import javax.persistence.*;

@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String songName;
    private String artist;
    private String previewUrl;
    private String spotifyCode;
    private int year;

    public Card() {
    }

    public Card(String songName, String artist, int year, String spotifyCode, String previewUrl) {
        this.songName = songName;
        this.artist = artist;
        this.year = year;
        this.spotifyCode = spotifyCode;
        this.previewUrl = previewUrl;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSongName() {
        return songName;
    }

    public void setSongName(String songName) {
        this.songName = songName;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getSpotifyCode() {
        return spotifyCode;
    }

    public void setSpotifyCode(String spotifyCode) {
        this.spotifyCode = spotifyCode;
    }

    public String getPreviewUrl() {
        return previewUrl;
    }

    public void setPreviewUrl(String previewUrl) {
        this.previewUrl = previewUrl;
    }
}

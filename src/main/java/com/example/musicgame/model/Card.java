package com.example.musicgame.model;

import javax.persistence.*;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String songName;
    private String artist;
    private int year;
    private String spotifyCode;
    private String previewUrl;
    private boolean isFaceUp;

    public Card() {
    }

    public Card(String songName, String artist, int year, String spotifyCode, String previewUrl) {
        this.songName = songName;
        this.artist = artist;
        this.year = year;
        this.spotifyCode = spotifyCode;
        this.previewUrl = previewUrl;
        this.isFaceUp = true;
    }

    // Getters and setters
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

    public boolean isFaceUp() {
        return isFaceUp;
    }

    public void setFaceUp(boolean faceUp) {
        isFaceUp = faceUp;
    }
}

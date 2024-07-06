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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "deck_id")
    private Deck deck;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "timeline_id")
    private TimeLine timeline;

    // Constructors
    public Card() {}

    public Card(String songName, String artist, int year, String spotifyCode, String previewUrl) {
        this.songName = songName;
        this.artist = artist;
        this.year = year;
        this.spotifyCode = spotifyCode;
        this.previewUrl = previewUrl;
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

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }

    public TimeLine getTimeline() {
        return timeline;
    }

    public void setTimeline(TimeLine timeline) {
        this.timeline = timeline;
    }
}

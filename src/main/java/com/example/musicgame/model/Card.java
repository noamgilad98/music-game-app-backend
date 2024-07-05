package com.example.musicgame.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Card {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String songName;
    private String artist;
    private int year;
    private String spotifyCode;
    private boolean isFaceUp;

    @ManyToMany(mappedBy = "cards", fetch = FetchType.LAZY)
    @JsonManagedReference // Allow serialization of decks' reference in card
    private Set<Deck> decks = new HashSet<>();

    @ManyToMany(mappedBy = "cards", fetch = FetchType.LAZY)
    @JsonManagedReference // Allow serialization of timelines' reference in card
    private Set<Timeline> timelines = new HashSet<>();

    // Default constructor
    public Card() {
    }

    // Constructor with parameters
    public Card(String songName, String artist, int year, String spotifyCode) {
        this.songName = songName;
        this.artist = artist;
        this.year = year;
        this.spotifyCode = spotifyCode;
        this.isFaceUp = false;
    }

    // Getters and Setters
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

    public boolean isFaceUp() {
        return isFaceUp;
    }

    public void setFaceUp(boolean isFaceUp) {
        this.isFaceUp = isFaceUp;
    }

    public Set<Deck> getDecks() {
        return decks;
    }

    public void setDecks(Set<Deck> decks) {
        this.decks = decks;
    }

    public Set<Timeline> getTimelines() {
        return timelines;
    }

    public void setTimelines(Set<Timeline> timelines) {
        this.timelines = timelines;
    }
}

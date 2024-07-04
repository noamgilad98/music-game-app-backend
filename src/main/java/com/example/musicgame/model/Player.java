package com.example.musicgame.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany
    private List<Card> timeline = new ArrayList<>();

    @OneToMany
    private List<Card> deck = new ArrayList<>();

    public Player() {
    }

    public Player(String name) {
        this.name = name;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Card> getTimeline() {
        return timeline;
    }

    public void setTimeline(List<Card> timeline) {
        this.timeline = timeline;
    }

    public List<Card> getDeck() {
        return deck;
    }

    public void setDeck(List<Card> deck) {
        this.deck = deck;
    }
}

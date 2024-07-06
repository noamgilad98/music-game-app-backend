package com.example.musicgame.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Player {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id", nullable = false)
    @JsonBackReference(value = "game-players")
    private Game game;

    @OneToMany(mappedBy = "playerTimeline", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "player-timeline")
    private List<Card> timeline;

    public Player() {
        this.timeline = new ArrayList<>();
    }

    public Player(String name, Game game) {
        this.name = name;
        this.game = game;
        this.timeline = new ArrayList<>();
    }

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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public List<Card> getTimeline() {
        return timeline;
    }

    public void setTimeline(List<Card> timeline) {
        this.timeline = timeline;
    }
}

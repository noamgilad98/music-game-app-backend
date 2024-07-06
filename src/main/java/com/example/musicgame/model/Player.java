package com.example.musicgame.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    @JsonBackReference(value = "game-players")
    private Game game;

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @JsonManagedReference(value = "player-timeline")
    private TimeLine timeLine;

    public Player() {
    }

    public Player(String name, Game game) {
        this.name = name;
        this.game = game;
        this.timeLine = new TimeLine(this);
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

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public TimeLine getTimeLine() {
        return timeLine;
    }

    public void setTimeLine(TimeLine timeLine) {
        this.timeLine = timeLine;
    }

    public List<Card> getTimeline() {
        return timeLine.getCards();
    }

    public void addCardToTimeline(Card card, int position) {
        timeLine.addCardAtPosition(card, position);
    }
}

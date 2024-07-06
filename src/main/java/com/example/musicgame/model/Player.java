package com.example.musicgame.model;

import javax.persistence.*;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_id")
    private Game game;

    @OneToOne(mappedBy = "player", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    private TimeLine timeLine;

    public Player() {}

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

    public void setTimeline(TimeLine timeLine) {
        this.timeLine = timeLine;
    }

    public void addCardToTimeline(Card card, int position) {
        timeLine.addCardAtPosition(card, position);
    }
}

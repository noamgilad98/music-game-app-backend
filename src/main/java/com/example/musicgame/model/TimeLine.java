package com.example.musicgame.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TimeLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "player_id")
    private Player player;

    @OneToMany(mappedBy = "timeLine", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference(value = "timeline-cards")
    private List<Card> cards = new ArrayList<>();

    public TimeLine() {
    }

    public TimeLine(Player player) {
        this.player = player;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }


    public void addCard(Card card) {
        cards.add(card);
        card.setTimeLine(this);
    }

    public void removeCard(Card card) {
        cards.remove(card);
        card.setTimeLine(null);
    }

    public void addCardAtPosition(Card card, int position) {
        if (position < 0 || position > cards.size()) {
            throw new IndexOutOfBoundsException("Position out of bounds");
        }
        cards.add(position, card);
        card.setTimeLine(this);
    }
}
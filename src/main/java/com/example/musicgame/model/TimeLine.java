package com.example.musicgame.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class TimeLine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToMany
    @JoinTable(
            name = "timeline_card",
            joinColumns = @JoinColumn(name = "timeline_id"),
            inverseJoinColumns = @JoinColumn(name = "card_id")
    )
    private List<Card> cards = new ArrayList<>();


    public TimeLine() {
    }

    public TimeLine(List<Card> cards) {
        this.cards = cards;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }


    public void addCardAtPosition(Card card, int position) {
        if (position < 0 || position > cards.size()) {
            throw new IndexOutOfBoundsException("Position out of bounds");
        }
        cards.add(position, card);
    }
}
package com.example.musicgame.model;

import javax.persistence.*;
import java.util.Collections;
import java.util.List;

@Entity
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Card> cards;

    public Deck() {
    }

    public Deck(List<Card> cards) {
        this.cards = cards;
    }

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

    public Card drawCard() {
        return cards.remove(0);
    }

    public void shuffle() {
        Collections.shuffle(cards);
    }
}

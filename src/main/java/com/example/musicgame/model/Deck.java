package com.example.musicgame.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

@Entity
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Card> cards;

    // Default constructor
    public Deck() {}

    // Constructor with cards
    public Deck(Set<Card> cards) {
        this.cards = cards;
    }

    // Getter and Setter for cards
    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public Card pullCard() {
        isEmpty();
        Card pulledCard = cards.iterator().next();
        cards.remove(pulledCard);
        return pulledCard;
    }

    public void shuffleDeck() {
        isEmpty();
        List<Card> cardList = new ArrayList<>(cards);
        Collections.shuffle(cardList);
        cards.clear();
        cards.addAll(cardList);
    }

    public boolean isEmpty() {
        if (cards == null) {
            throw new IllegalStateException("Deck is not initialized");
        }
        return cards.isEmpty();
    }
}

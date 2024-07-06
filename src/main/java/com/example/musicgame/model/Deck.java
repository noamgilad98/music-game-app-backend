package com.example.musicgame.model;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
public class Deck {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "deck")
    private Game game;

    @OneToMany(mappedBy = "deck", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Card> cards = new HashSet<>();

    // Constructors
    public Deck() {}

    public Deck(Set<Card> cards) {
        this.cards = cards;
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Set<Card> getCards() {
        return cards;
    }

    public void setCards(Set<Card> cards) {
        this.cards = cards;
    }

    public void addCard(Card card) {
        cards.add(card);
        card.setDeck(this);
    }

    public void removeCard(Card card) {
        cards.remove(card);
        card.setDeck(null);
    }

    public Card drawCard() {
        Card card = cards.iterator().next();
        cards.remove(card);
        return card;
    }
}

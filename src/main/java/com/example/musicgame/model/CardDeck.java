//package com.example.musicgame.model;
//
//import javax.persistence.*;
//import java.util.HashSet;
//import java.util.Set;
//
//@Entity
//public class CardDeck {
//
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long id;
//
//    @ManyToMany
//    @JoinTable(
//            name = "card_deck",
//            joinColumns = @JoinColumn(name = "card_id"),
//            inverseJoinColumns = @JoinColumn(name = "deck_id")
//    )
//    private Set<Card> cards = new HashSet<>();
//
//    @ManyToMany
//    @JoinTable(
//            name = "card_deck",
//            joinColumns = @JoinColumn(name = "deck_id"),
//            inverseJoinColumns = @JoinColumn(name = "card_id")
//    )
//    private Set<Deck> decks = new HashSet<>();
//
//    // Constructors, Getters and Setters
//}

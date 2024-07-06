package com.example.musicgame.model;
import com.example.musicgame.model.Card;
import com.example.musicgame.model.GameState;
import com.example.musicgame.model.Player;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import javax.persistence.*;
import java.util.List;

@Entity
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;


    @Enumerated(EnumType.STRING)
    private GameState gameState;

    @OneToOne
    private Deck deck;

    private Long currentTurnPlayerId;


    // Default constructor
    public Game() {
    }

    // Constructor with parameters
    public Game(Deck deck, GameState gameState) {
        this.deck = deck;
        this.gameState = gameState;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public Deck getDeck() {
        return deck;
    }

    public void setDeck(Deck deck) {
        this.deck = deck;
    }


    public Card pullCardFromDeck(Long playerId) {
        if (playerId.equals(currentTurnPlayerId)) {
            return deck.pullCard();
        } else {
            throw new IllegalArgumentException("It is not your turn");
        }
    }
}

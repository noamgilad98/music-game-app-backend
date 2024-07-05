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

    @OneToOne
    @JoinColumn(name = "player_id")
    @JsonManagedReference
    private Player player;

    @Enumerated(EnumType.STRING)
    private GameState gameState;

    @ManyToMany
    @JoinTable(
            name = "game_cards",
            joinColumns = @JoinColumn(name = "game_id"),
            inverseJoinColumns = @JoinColumn(name = "cards_id")
    )
    private List<Card> cards;

    // Default constructor
    public Game() {
    }

    // Constructor with parameters
    public Game(Player player, List<Card> cards, GameState gameState) {
        this.player = player;
        this.cards = cards;
        this.gameState = gameState;
    }

    // Getters and Setters
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

    public GameState getGameState() {
        return gameState;
    }

    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }
}

package com.example.musicgame.dto;

import com.example.musicgame.model.Card;
import com.example.musicgame.model.Player;

public class PlaceCardRequest {
    private Player player;
    private Card card;
    private int position;

    // Default constructor
    public PlaceCardRequest() {}

    // Parameterized constructor
    public PlaceCardRequest(Player player, Card card, int position) {
        this.player = player;
        this.card = card;
        this.position = position;
    }

    // Getters and setters
    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public String toString() {
        return "PlaceCardRequest{" +
                "player=" + player +
                ", card=" + card +
                ", position=" + position +
                '}';
    }
}

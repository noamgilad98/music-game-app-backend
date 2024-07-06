package com.example.musicgame.service;

import com.example.musicgame.model.Deck;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.example.musicgame.model.Card;

import java.util.Collections;
import java.util.List;

@Service
public class DeckService {

    @Autowired
    private CardService cardService;

    public Deck createDeck() {
        List<Card> cards = cardService.getAllCards();
        Collections.shuffle(cards);
        return new Deck(cards);
    }
}

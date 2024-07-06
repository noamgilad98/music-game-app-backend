package com.example.musicgame.service;

import com.example.musicgame.model.Card;
import com.example.musicgame.model.Deck;
import com.example.musicgame.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class DeckService {

    @Autowired
    private CardRepository cardRepository;

    public Deck createDeck() {
        List<Card> cards = cardRepository.findAll();
        Collections.shuffle(cards);
        return new Deck(cards);
    }
}

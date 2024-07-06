package com.example.musicgame.service;

import com.example.musicgame.model.Card;
import com.example.musicgame.model.Deck;
import com.example.musicgame.repository.CardRepository;
import com.example.musicgame.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DeckService {

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private CardRepository cardRepository;

    public Deck createDeck(List<Card> cards) {
        Deck deck = new Deck(cards);
        return deckRepository.save(deck); // Save the deck along with the cards
    }
}

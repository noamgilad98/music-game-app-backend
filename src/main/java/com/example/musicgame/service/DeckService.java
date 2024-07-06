package com.example.musicgame.service;

import com.example.musicgame.model.Card;
import com.example.musicgame.model.Deck;
import com.example.musicgame.repository.CardRepository;
import com.example.musicgame.repository.DeckRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DeckService {

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private CardService cardService;

    public Deck createDeck() {
        List<Card> cards = cardRepository.findAll();
        List<Card> newCards = cards.stream()
                .map(card -> {
                    Card newCard = new Card();
                    newCard.setSongName(card.getSongName());
                    newCard.setArtist(card.getArtist());
                    newCard.setPreviewUrl(card.getPreviewUrl());
                    newCard.setSpotifyCode(card.getSpotifyCode());
                    newCard.setYear(card.getYear());
                    return cardRepository.save(newCard);
                })
                .collect(Collectors.toList());
        Deck deck = new Deck(newCards);
        return deckRepository.save(deck);
    }

}

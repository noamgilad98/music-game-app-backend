package com.example.musicgame.service;

import com.example.musicgame.model.Card;
import com.example.musicgame.model.Deck;
import com.example.musicgame.model.Player;
import com.example.musicgame.repository.CardRepository;
import com.example.musicgame.repository.DeckRepository;
import com.example.musicgame.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Random;
import java.util.Set;

@Service
public class DeckService {

    @Autowired
    private DeckRepository deckRepository;
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @Transactional
    public Deck createDeck(Player player) {
        Deck deck = new Deck();
        deck.setPlayer(player);
        return deckRepository.save(deck);
    }

    @Transactional
    public Card addUniqueRandomCardToDeck(Long deckId) {
        Optional<Deck> deckOpt = deckRepository.findById(deckId);
        if (deckOpt.isPresent()) {
            Deck deck = deckOpt.get();
            Set<Card> availableCards = new HashSet<>(cardRepository.findAll());
            availableCards.removeAll(deck.getCards());
            if (availableCards.isEmpty()) {
                throw new IllegalStateException("No unique cards available");
            }
            Card newCard = getUniqueRandomCard(deck.getCards(), availableCards);
            deck.getCards().add(newCard);
            deckRepository.save(deck);
            return newCard;
        } else {
            throw new IllegalArgumentException("Invalid deck ID");
        }
    }

    private Card getUniqueRandomCard(Set<Card> deckCards, Set<Card> availableCards) {
        Random random = new Random();
        int index = random.nextInt(availableCards.size());
        return (Card) availableCards.toArray()[index];
    }
}

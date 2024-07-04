package com.example.musicgame.service;

import com.example.musicgame.model.Card;
import com.example.musicgame.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public List<Card> getAllCards() {
        return cardRepository.findAll();
    }

    public Card saveCard(Card card) {
        return cardRepository.save(card);
    }

    public void deleteCard(Long id) {
        cardRepository.deleteById(id);
    }

    @Transactional
    public void resetCardIdSequence() {
        cardRepository.deleteAll();
        cardRepository.resetSequence();
    }
}

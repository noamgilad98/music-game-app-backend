package com.example.musicgame.service;

import com.example.musicgame.model.Card;
import com.example.musicgame.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CardService {

    @Autowired
    private CardRepository cardRepository;

    public Card createCard(Card card) {
        return cardRepository.save(card);
    }

    public Card getCardById(Long cardId) {
        return cardRepository.findById(cardId).orElseThrow(() -> new RuntimeException("Card not found"));
    }

    public void deleteCard(Long cardId) {
        cardRepository.deleteById(cardId);
    }
}

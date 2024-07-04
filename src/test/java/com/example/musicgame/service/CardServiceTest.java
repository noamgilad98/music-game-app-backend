package com.example.musicgame.service;

import com.example.musicgame.model.Card;
import com.example.musicgame.repository.CardRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CardServiceTest {

    @InjectMocks
    private CardService cardService;

    @Mock
    private CardRepository cardRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllCards() {
        Card card = new Card("Song", "Artist", 2020, "spotifyCode");
        when(cardRepository.findAll()).thenReturn(Collections.singletonList(card));

        List<Card> cards = cardService.getAllCards();

        assertNotNull(cards);
        assertFalse(cards.isEmpty());
        assertEquals("Song", cards.get(0).getSongName());
    }

    @Test
    void testSaveCard() {
        Card card = new Card("Song", "Artist", 2020, "spotifyCode");
        when(cardRepository.save(any(Card.class))).thenReturn(card);

        Card savedCard = cardService.saveCard(card);

        assertNotNull(savedCard);
        assertEquals("Song", savedCard.getSongName());
    }

    @Test
    void testDeleteCard() {
        cardService.deleteCard(1L);
        verify(cardRepository, times(1)).deleteById(1L);
    }
}

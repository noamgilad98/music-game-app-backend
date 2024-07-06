package com.example.musicgame.test;

import com.example.musicgame.model.Card;
import com.example.musicgame.repository.CardRepository;
import com.example.musicgame.service.CardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class CardServiceTest {

    @InjectMocks
    private CardService cardService;

    @Mock
    private CardRepository cardRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateCard_Success() {
        Card card = new Card("Song", "Artist", 2020, "spotifyCode", "previewUrl");
        when(cardRepository.save(card)).thenReturn(card);

        Card createdCard = cardService.createCard(card);
        assertNotNull(createdCard);
    }
}

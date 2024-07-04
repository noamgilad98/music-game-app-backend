package com.example.musicgame.service;

import com.example.musicgame.model.Card;
import com.example.musicgame.model.Player;
import com.example.musicgame.repository.CardRepository;
import com.example.musicgame.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Mock
    private CardRepository cardRepository;

    @Mock
    private PlayerRepository playerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSavePlayer() {
        Player player = new Player();
        player.setName("Test Player");
        when(playerRepository.save(any(Player.class))).thenReturn(player);

        Player savedPlayer = gameService.savePlayer(player);

        assertNotNull(savedPlayer);
        assertEquals("Test Player", savedPlayer.getName());
    }

    @Test
    void testStartGame() {
        Player player = new Player();
        when(cardRepository.findAll()).thenReturn(Collections.singletonList(new Card("Song", "Artist", 2020, "spotifyCode")));

        Card card = gameService.startGame(player);

        assertNotNull(card);
        assertEquals("Song", card.getSongName());
    }

    @Test
    void testGetCard() {
        Player player = new Player();
        player.setId(1L);
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));
        when(cardRepository.findAll()).thenReturn(Collections.singletonList(new Card("Song", "Artist", 2020, "spotifyCode")));

        Card card = gameService.getCard(1L);

        assertNotNull(card);
        assertEquals("Song", card.getSongName());
    }
}

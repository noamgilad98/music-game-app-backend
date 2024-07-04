package com.example.musicgame.service;

import com.example.musicgame.model.Player;
import com.example.musicgame.repository.PlayerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerServiceTest {

    @InjectMocks
    private PlayerService playerService;

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

        Player savedPlayer = playerService.savePlayer(player);

        assertNotNull(savedPlayer);
        assertEquals("Test Player", savedPlayer.getName());
    }

    @Test
    void testGetPlayerById() {
        Player player = new Player();
        player.setId(1L);
        when(playerRepository.findById(1L)).thenReturn(Optional.of(player));

        Optional<Player> foundPlayer = playerService.getPlayerById(1L);

        assertTrue(foundPlayer.isPresent());
        assertEquals(1L, foundPlayer.get().getId());
    }

    @Test
    void testDeletePlayer() {
        playerService.deletePlayer(1L);
        verify(playerRepository, times(1)).deleteById(1L);
    }
}

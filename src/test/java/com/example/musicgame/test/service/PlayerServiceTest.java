package com.example.musicgame.test.service;

import com.example.musicgame.model.Game;
import com.example.musicgame.model.Player;
import com.example.musicgame.repository.GameRepository;
import com.example.musicgame.repository.PlayerRepository;
import com.example.musicgame.service.PlayerService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

public class PlayerServiceTest {

    @InjectMocks
    private PlayerService playerService;

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private GameRepository gameRepository;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testJoinGame_Success() {
        Game game = new Game();
        Player player = new Player();
        player.setName("Player1");

        when(gameRepository.findById(1L)).thenReturn(java.util.Optional.of(game));
        when(playerRepository.save(player)).thenReturn(player);

        Player joinedPlayer = playerService.joinGame(1L, player);
        assertNotNull(joinedPlayer);
    }
}

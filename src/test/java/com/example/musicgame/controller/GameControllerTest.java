package com.example.musicgame.controller;

import com.example.musicgame.model.Card;
import com.example.musicgame.model.Player;
import com.example.musicgame.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

class GameControllerTest {

    @InjectMocks
    private GameController gameController;

    @Mock
    private GameService gameService;

    private MockMvc mockMvc;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
    }

    @Test
    void testStartGame() throws Exception {
        Player player = new Player();
        player.setName("Test Player");
        Card card = new Card("Song", "Artist", 2020, "spotifyCode");

        when(gameService.savePlayer(any(Player.class))).thenReturn(player);
        when(gameService.startGame(any(Player.class))).thenReturn(card);

        Map<String, Object> response = new HashMap<>();
        response.put("player", player);
        response.put("card", card);

        mockMvc.perform(post("/api/game/start")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"name\":\"Test Player\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.player.name").value("Test Player"))
                .andExpect(jsonPath("$.card.songName").value("Song"));
    }

    @Test
    void testGetCard() throws Exception {
        Card card = new Card("Song", "Artist", 2020, "spotifyCode");
        when(gameService.getCard(1L)).thenReturn(card);

        mockMvc.perform(get("/api/game/get-card")
                        .param("playerId", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.songName").value("Song"));
    }
}

package com.example.musicgame.test;

import com.example.musicgame.controller.GameController;
import com.example.musicgame.model.Game;
import com.example.musicgame.model.GameState;
import com.example.musicgame.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class GameControllerTest {

    private MockMvc mockMvc;

    @Mock
    private GameService gameService;

    @InjectMocks
    private GameController gameController;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(gameController).build();
    }

    @Test
    public void testCreateGame_Success() throws Exception {
        Game game = new Game();
        when(gameService.createGame(game)).thenReturn(game);

        mockMvc.perform(post("/game/create")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}"))
                .andExpect(status().isOk());
    }

    @Test
    public void testGetGame_Success() throws Exception {
        Game game = new Game();
        game.setGameState(GameState.CREATED);
        when(gameService.getGameById(1L)).thenReturn(game);

        mockMvc.perform(get("/game/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void testStartGame_Success() throws Exception {
        Game game = new Game();
        game.setGameState(GameState.STARTED);
        when(gameService.startGame(1L)).thenReturn(game);

        mockMvc.perform(post("/game/1/start"))
                .andExpect(status().isOk());
    }
}

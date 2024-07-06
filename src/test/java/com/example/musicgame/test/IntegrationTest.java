package com.example.musicgame.test;

import com.example.musicgame.model.Game;
import com.example.musicgame.model.User;
import com.example.musicgame.model.GameState;
import com.example.musicgame.repository.GameRepository;
import com.example.musicgame.repository.UserRepository;
import com.example.musicgame.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.Assertions.assertThat;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Map;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserService userService;

    private RestTemplate restTemplate;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        gameRepository.deleteAll();
        this.restTemplate = new RestTemplate();
    }

    @Test
    public void testUserRegistrationAndGameFlow() {
        // Register users
        User user1 = new User("user1", "password1");
        User user2 = new User("user2", "password2");
        ResponseEntity<String> response1 = registerUser(user1);
        ResponseEntity<String> response2 = registerUser(user2);

        assertThat(response1.getStatusCodeValue()).isEqualTo(200);
        assertThat(response2.getStatusCodeValue()).isEqualTo(200);

        assertThat(response1.getBody()).isEqualTo("User registered successfully");
        assertThat(response2.getBody()).isEqualTo("User registered successfully");

        // Login users
        String token1 = loginUser(user1);
        String token2 = loginUser(user2);

        assertThat(token1).isNotNull();
        assertThat(token2).isNotNull();

        // Log tokens for debugging
        System.out.println("Token1: " + token1);
        System.out.println("Token2: " + token2);

        // Start a game
        Game game = new Game();
        game.setGameState(GameState.CREATED);  // Set initial state
        HttpEntity<Game> gameRequest = new HttpEntity<>(game, createHeaders(token1));
        ResponseEntity<Game> gameResponse = restTemplate.postForEntity("http://localhost:" + port + "/game/create", gameRequest, Game.class);

        assertThat(gameResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(gameResponse.getBody()).isNotNull();

        Long gameId = gameResponse.getBody().getId();

        // Add players to game
        ResponseEntity<Game> addUserResponse1 = addPlayerToGame(gameId, token1, user1);
        ResponseEntity<Game> addUserResponse2 = addPlayerToGame(gameId, token2, user2);

        assertThat(addUserResponse1.getStatusCodeValue()).isEqualTo(200);
        assertThat(addUserResponse2.getStatusCodeValue()).isEqualTo(200);

        // Verify game status
        ResponseEntity<Game> gameStatusResponse = restTemplate.exchange("http://localhost:" + port + "/game/" + gameId, HttpMethod.GET, new HttpEntity<>(createHeaders(token1)), Game.class);

        assertThat(gameStatusResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(gameStatusResponse.getBody().getPlayers().size()).isEqualTo(2);
    }

    private ResponseEntity<String> registerUser(User user) {
        return restTemplate.postForEntity("http://localhost:" + port + "/auth/register", user, String.class);
    }

    private String loginUser(User user) {
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/auth/login", user, String.class);
        try {
            Map<String, String> result = new ObjectMapper().readValue(response.getBody(), Map.class);
            return "Bearer " + result.get("token");
        } catch (Exception e) {
            throw new RuntimeException("Failed to parse login response", e);
        }
    }

    private ResponseEntity<Game> addPlayerToGame(Long gameId, String token, User user) {
        HttpHeaders headers = createHeaders(token);
        headers.set("Content-Type", "application/json");
        HttpEntity<User> addUserRequest = new HttpEntity<>(user, headers);
        return restTemplate.postForEntity("http://localhost:" + port + "/game/" + gameId + "/addPlayer", addUserRequest, Game.class);
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);  // Token already prefixed with "Bearer "
        return headers;
    }
}

package com.example.musicgame.test.integration;


import com.example.musicgame.dto.PlaceCardRequest;
import com.example.musicgame.model.*;
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
import java.util.Objects;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FullGameFlowTest {

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
    public void testFullGameFlowForTwoPlayers() {
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
        HttpHeaders headers = createHeaders(token1);
        HttpEntity<Void> gameRequest = new HttpEntity<>(headers);
        ResponseEntity<Game> gameResponse = restTemplate.postForEntity("http://localhost:" + port + "/game/create", gameRequest, Game.class);

        assertThat(gameResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(gameResponse.getBody()).isNotNull();

        Long gameId = gameResponse.getBody().getId();

        // Add player 2 to game
        ResponseEntity<Game> addUserResponse2 = addPlayerToGame(gameId, token2, user2);

        assertThat(addUserResponse2.getStatusCodeValue()).isEqualTo(200);

        // Start the game
        ResponseEntity<Game> startGameResponse = startGame(gameId, token1);
        assertThat(startGameResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(Objects.requireNonNull(startGameResponse.getBody()).getGameState()).isEqualTo(GameState.STARTED);

        // Verify game status
        ResponseEntity<Game> gameStatusResponse = restTemplate.exchange("http://localhost:" + port + "/game/" + gameId, HttpMethod.GET, new HttpEntity<>(createHeaders(token1)), Game.class);

        assertThat(gameStatusResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(Objects.requireNonNull(gameStatusResponse.getBody()).getPlayers().size()).isEqualTo(2);

        // Play the game
        boolean someoneWon = false;
        while (!someoneWon) {
            // Player 1 draws a card
            ResponseEntity<Card> drawCardResponse = drawCard(gameId, token1, user1);
            assertThat(drawCardResponse.getStatusCodeValue()).isEqualTo(200);
            Card card = drawCardResponse.getBody();
            assertThat(card).isNotNull(); // Ensure the card is not null before placing it

            ResponseEntity<Game> placeCardResponse1 = placeCard(gameId, token1, user1, card);
            assertThat(placeCardResponse1.getStatusCodeValue()).isEqualTo(200);

            // Check for win
            Game currentGame = placeCardResponse1.getBody();
            if (checkWinCondition(currentGame)) {
                someoneWon = true;
                break;
            }

            // Player 2 draws a card
            ResponseEntity<Card> drawCardResponse2 = drawCard(gameId, token2, user2);
            assertThat(drawCardResponse2.getStatusCodeValue()).isEqualTo(200);
            Card card2 = drawCardResponse2.getBody();

            // Player 2 places the card
            ResponseEntity<Game> placeCardResponse2 = placeCard(gameId, token2, user2, card2);
            assertThat(placeCardResponse2.getStatusCodeValue()).isEqualTo(200);

            // Check for win
            currentGame = placeCardResponse2.getBody();
            if (checkWinCondition(currentGame)) {
                someoneWon = true;
            }
        }

        // End the game
        ResponseEntity<Game> endGameResponse = endGame(gameId, token1);
        assertThat(endGameResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(Objects.requireNonNull(endGameResponse.getBody()).getGameState()).isEqualTo(GameState.ENDED);
    }

    private ResponseEntity<String> registerUser(User user) {
        return restTemplate.postForEntity("http://localhost:" + port + "/auth/register", user, String.class);
    }

    private String loginUser(User user) {
        ResponseEntity<String> response = restTemplate.postForEntity("http://localhost:" + port + "/auth/login", user, String.class);
        try {
            Map result = new ObjectMapper().readValue(response.getBody(), Map.class);
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

    private ResponseEntity<Game> startGame(Long gameId, String token) {
        HttpHeaders headers = createHeaders(token);
        headers.set("Content-Type", "application/json");
        return restTemplate.exchange("http://localhost:" + port + "/game/" + gameId + "/start", HttpMethod.POST, new HttpEntity<>(headers), Game.class);
    }

    private ResponseEntity<Game> endGame(Long gameId, String token) {
        HttpHeaders headers = createHeaders(token);
        headers.set("Content-Type", "application/json");
        return restTemplate.exchange("http://localhost:" + port + "/game/" + gameId + "/end", HttpMethod.POST, new HttpEntity<>(headers), Game.class);
    }

    private ResponseEntity<Card> drawCard(Long gameId, String token, User user) {
        HttpHeaders headers = createHeaders(token);
        headers.set("Content-Type", "application/json");
        Player player = findPlayerByUserAndGame(gameId, token, user);
        HttpEntity<Player> drawCardRequest = new HttpEntity<>(player, headers);
        return restTemplate.postForEntity("http://localhost:" + port + "/game/" + gameId + "/drawCard", drawCardRequest, Card.class);
    }

    private ResponseEntity<Game> placeCard(Long gameId, String token, User user, Card card) {
        HttpHeaders headers = createHeaders(token);
        headers.set("Content-Type", "application/json");
        Player player = findPlayerByUserAndGame(gameId, token, user);
        PlaceCardRequest placeCardRequest = new PlaceCardRequest(player, card, 0);
        return restTemplate.postForEntity("http://localhost:" + port + "/game/" + gameId + "/placeCard", new HttpEntity<>(placeCardRequest, headers), Game.class);
    }


    private boolean checkWinCondition(Game game) {
        // Implement your win condition check logic here
        // For example, check if any player's timeline has the required number of cards
        return false; // Replace with actual win condition logic
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", token);  // Token already prefixed with "Bearer "
        return headers;
    }

    private Player findPlayerByUserAndGame(Long gameId, String token, User user) {
        ResponseEntity<Game> gameResponse = restTemplate.exchange("http://localhost:" + port + "/game/" + gameId, HttpMethod.GET, new HttpEntity<>(createHeaders(token)), Game.class);
        Game game = gameResponse.getBody();
        return game.getPlayers().stream().filter(p -> p.getName().equals(user.getUsername())).findFirst().orElseThrow(() -> new RuntimeException("Player not found"));
    }
}

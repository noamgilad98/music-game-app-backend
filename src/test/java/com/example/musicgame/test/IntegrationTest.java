package com.example.musicgame.test;

import com.example.musicgame.model.Game;
import com.example.musicgame.model.User;
import com.example.musicgame.repository.GameRepository;
import com.example.musicgame.repository.UserRepository;
import com.example.musicgame.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private UserService userService;

    @BeforeEach
    void setUp() {
        userRepository.deleteAll();
        gameRepository.deleteAll();
    }

    @Test
    public void testUserRegistrationAndGameFlow() {
        // Register users
        User user1 = new User("user1", "password1");
        User user2 = new User("user2", "password2");
        ResponseEntity<String> response1 = registerUser(user1);
        ResponseEntity<String> response2 = registerUser(user2);

        assertThat(response1.getBody()).isEqualTo("User registered successfully");
        assertThat(response2.getBody()).isEqualTo("User registered successfully");

        // Login users
        String token1 = loginUser(user1);
        String token2 = loginUser(user2);

        assertThat(token1).isNotNull();
        assertThat(token2).isNotNull();

        // Start a game
        Game game = new Game();
        HttpEntity<Game> gameRequest = new HttpEntity<>(game, createHeaders(token1));
        ResponseEntity<Game> gameResponse = restTemplate.postForEntity(createURLWithPort("/game/create"), gameRequest, Game.class);

        assertThat(gameResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(gameResponse.getBody()).isNotNull();

        Long gameId = gameResponse.getBody().getId();

        // Add user to game
        HttpEntity<Void> addUserRequest = new HttpEntity<>(createHeaders(token2));
        ResponseEntity<Void> addUserResponse = restTemplate.postForEntity(createURLWithPort("/game/" + gameId + "/start"), addUserRequest, Void.class);

        assertThat(addUserResponse.getStatusCodeValue()).isEqualTo(200);

        // Verify game status
        ResponseEntity<Game> gameStatusResponse = restTemplate.exchange(createURLWithPort("/game/" + gameId), HttpMethod.GET, new HttpEntity<>(createHeaders(token1)), Game.class);

        assertThat(gameStatusResponse.getStatusCodeValue()).isEqualTo(200);
        assertThat(gameStatusResponse.getBody().getPlayers().size()).isEqualTo(2);
    }

    private ResponseEntity<String> registerUser(User user) {
        return restTemplate.postForEntity(createURLWithPort("/auth/register"), user, String.class);
    }

    private String loginUser(User user) {
        ResponseEntity<String> response = restTemplate.postForEntity(createURLWithPort("/auth/login"), user, String.class);
        return response.getBody();
    }

    private HttpHeaders createHeaders(String token) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "Bearer " + token);
        return headers;
    }

    private String createURLWithPort(String uri) {
        return "http://localhost:" + port + uri;
    }
}

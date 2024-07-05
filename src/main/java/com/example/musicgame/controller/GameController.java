package com.example.musicgame.controller;

import com.example.musicgame.model.Card;
import com.example.musicgame.model.Player;
import com.example.musicgame.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;

@RestController
@RequestMapping("/api/game")
public class GameController {

    @Autowired
    private GameService gameService;

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    @PostMapping("/start")
    public ResponseEntity<Map<String, Object>> startGame(@RequestBody Player player) {
        logger.info("Starting game for player: {}", player.getName());
        Player savedPlayer = gameService.savePlayer(player);
        Card initialCard = gameService.startGame(savedPlayer);
        logger.info("Player saved: {}", savedPlayer);
        logger.info("Initial card: {}", initialCard);

        Map<String, Object> response = new HashMap<>();
        response.put("player", savedPlayer);
        response.put("card", initialCard);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/get-card")
    public ResponseEntity<Card> getCard(@RequestParam Long playerId) {
        logger.info("Getting card for player: {}", playerId);
        Card card = gameService.getCard(playerId);
        logger.info("Card retrieved: {}", card);
        return ResponseEntity.ok(card);
    }

    @PostMapping("/submit-card")
    public ResponseEntity<Boolean> submitCard(@RequestParam Long playerId, @RequestBody Card card) {
        logger.info("Submitting card for player ID: {}", playerId);
        boolean result = gameService.submitCard(playerId, card);
        logger.info("Card submitted: {}", card);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/submit-timeline")
    public ResponseEntity<Boolean> submitTimeline(@RequestParam Long playerId, @RequestBody Set<Card> timeline) {
        logger.info("Submitting timeline for player: {}", playerId);
        boolean result = gameService.submitTimeline(playerId, timeline);
        logger.info("Timeline submitted for player ID: {}", playerId);
        return ResponseEntity.ok(result);
    }

    @GetMapping("/validate-timeline")
    public ResponseEntity<Boolean> validateTimeline(@RequestParam Long playerId) {
        logger.info("Validating timeline for player: {}", playerId);
        boolean result = gameService.validateTimeline(playerId);
        logger.info("Timeline validated for player ID: {}", playerId);
        return ResponseEntity.ok(result);
    }

    @PostMapping("/submit-and-validate")
    public ResponseEntity<Map<String, Object>> submitAndValidate(@RequestParam Long playerId, @RequestBody Card card) {
        logger.info("Submitting and validating card for player: {}", playerId);
        Map<String, Object> response = gameService.submitAndValidate(playerId, card);
        logger.info("Validation response: {}", response);
        return ResponseEntity.ok(response);
    }
}

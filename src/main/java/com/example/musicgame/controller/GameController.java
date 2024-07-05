package com.example.musicgame.controller;

import com.example.musicgame.model.Card;
import com.example.musicgame.model.Player;
import com.example.musicgame.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/api/game")
public class GameController {
    @Autowired
    private GameService gameService;

    private static final Logger logger = LoggerFactory.getLogger(GameController.class);

    @PostMapping("/start-game")
    public Map<String, Object> startGame(@RequestBody Player player) {
        logger.info("Starting game for player: {}", player.getName());
        Map<String, Object> response = new HashMap<>();
        response.put("player", gameService.savePlayer(player));
        response.put("card", gameService.startGame(player));
        return response;
    }

    @GetMapping("/get-card")
    public Card getCard(@RequestParam Long playerId) {
        return gameService.getCard(playerId);
    }

    @PostMapping("/submit-card")
    public boolean submitCard(@RequestParam Long playerId, @RequestBody Card card) {
        return gameService.submitCard(playerId, card);
    }

    @PostMapping("/submit-timeline")
    public boolean submitTimeline(@RequestParam Long playerId, @RequestBody List<Card> timeline) {
        logger.info("Submitting timeline for player: {}", playerId);
        return gameService.submitTimeline(playerId, timeline);
    }

    @GetMapping("/validate-timeline")
    public boolean validateTimeline(@RequestParam Long playerId) {
        logger.info("Validating timeline for player: {}", playerId);
        return gameService.validateTimeline(playerId);
    }

    @PostMapping("/submit-and-validate")
    public Map<String, Object> submitAndValidate(@RequestParam Long playerId, @RequestBody Card card) {
        logger.info("Submitting and validating card for player: {}", playerId);
        return gameService.submitAndValidate(playerId, card);
    }
}

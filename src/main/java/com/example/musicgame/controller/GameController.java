package com.example.musicgame.controller;

import com.example.musicgame.dto.PlaceCardRequest;
import com.example.musicgame.model.Card;
import com.example.musicgame.model.Game;
import com.example.musicgame.model.Player;
import com.example.musicgame.model.User;
import com.example.musicgame.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/game")
public class GameController {

    @Autowired
    private GameService gameService;

    @PostMapping("/create")
    public ResponseEntity<Game> createGame(@RequestBody Game game) {
        Game createdGame = gameService.createGame(game);
        return ResponseEntity.ok(createdGame);
    }

    @GetMapping("/{gameId}")
    public ResponseEntity<Game> getGame(@PathVariable Long gameId) {
        Game game = gameService.getGameById(gameId);
        return ResponseEntity.ok(game);
    }

    @PostMapping("/{gameId}/start")
    public ResponseEntity<Game> startGame(@PathVariable Long gameId) {
        Game startedGame = gameService.startGame(gameId);
        return ResponseEntity.ok(startedGame);
    }

    @PostMapping("/{gameId}/addPlayer")
    public ResponseEntity<Game> addPlayerToGame(@PathVariable Long gameId, @RequestBody User user) {
        Game updatedGame = gameService.addPlayerToGame(gameId, user);
        return ResponseEntity.ok(updatedGame);
    }

    @PostMapping("/{gameId}/end")
    public ResponseEntity<Game> endGame(@PathVariable Long gameId) {
        Game endedGame = gameService.endGame(gameId);
        return ResponseEntity.ok(endedGame);
    }

    @PostMapping("/{gameId}/drawCard")
    public ResponseEntity<Card> drawCard(@PathVariable Long gameId, @RequestBody Player player) {
        System.out.println("DrawCard request received for gameId: " + gameId + " and player: " + player); // Log request
        Card drawnCard = gameService.drawCard(gameId, player.getId());
        System.out.println("DrawCard response: " + drawnCard); // Log response
        return ResponseEntity.ok(drawnCard);
    }


    @PostMapping("/{gameId}/placeCard")
    public ResponseEntity<Game> placeCard(@PathVariable Long gameId, @RequestBody PlaceCardRequest request) {
        if (request.getCard() == null) {
            throw new IllegalArgumentException("Card cannot be null");
        }
        System.out.println("PlaceCardRequest: " + request); // Log request
        Game updatedGame = gameService.placeCard(gameId, request.getPlayer().getId(), request.getCard().getId(), request.getPosition());
        return ResponseEntity.ok(updatedGame);
    }



    @GetMapping("/all")
    public ResponseEntity<List<Game>> getAllGames() {
        List<Game> games = gameService.getAllGames();
        return ResponseEntity.ok(games);
    }
}

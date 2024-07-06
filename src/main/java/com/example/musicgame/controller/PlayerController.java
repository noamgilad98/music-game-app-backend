package com.example.musicgame.controller;

import com.example.musicgame.model.Player;
import com.example.musicgame.service.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/player")
public class PlayerController {

    @Autowired
    private PlayerService playerService;

    @PostMapping("/join/{gameId}")
    public ResponseEntity<Player> joinGame(@PathVariable Long gameId, @RequestBody Player player) {
        Player joinedPlayer = playerService.joinGame(gameId, player);
        return ResponseEntity.ok(joinedPlayer);
    }

    @GetMapping("/{playerId}")
    public ResponseEntity<Player> getPlayer(@PathVariable Long playerId) {
        Player player = playerService.getPlayerById(playerId);
        return ResponseEntity.ok(player);
    }

    @PostMapping("/{playerId}/leave")
    public ResponseEntity<Void> leaveGame(@PathVariable Long playerId) {
        playerService.leaveGame(playerId);
        return ResponseEntity.noContent().build();
    }
}

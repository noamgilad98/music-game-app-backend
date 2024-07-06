package com.example.musicgame.service;

import com.example.musicgame.model.Game;
import com.example.musicgame.model.Player;
import com.example.musicgame.repository.GameRepository;
import com.example.musicgame.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private GameRepository gameRepository;

    public Player joinGame(Long gameId, Player player) {
        Game game = gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));
        return playerRepository.save(player);
    }

    public Player getPlayerById(Long playerId) {
        return playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));
    }

    public void leaveGame(Long playerId) {
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));
        playerRepository.save(player);
    }

    public Player createPlayer(String name, Game game) {
        Player player = new Player(name);
        player.setName(name);

        return playerRepository.save(player);
    }
}

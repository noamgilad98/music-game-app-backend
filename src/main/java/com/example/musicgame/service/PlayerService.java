package com.example.musicgame.service;

import com.example.musicgame.model.Player;
import com.example.musicgame.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }
}

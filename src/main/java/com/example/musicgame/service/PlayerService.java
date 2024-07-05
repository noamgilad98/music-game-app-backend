package com.example.musicgame.service;

import com.example.musicgame.model.Deck;
import com.example.musicgame.model.Player;
import com.example.musicgame.model.Timeline;
import com.example.musicgame.repository.PlayerRepository;
import com.example.musicgame.repository.TimelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PlayerService {

    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private DeckService deckService;
    @Autowired
    private TimelineRepository timelineRepository;

    public Player savePlayer(Player player) {
        if (player.getDeck() == null) {
            Deck deck = deckService.createDeck(player);
            player.setDeck(deck);
        }
        if (player.getTimelines().isEmpty()) {
            Timeline timeline = new Timeline();
            timeline.setPlayer(player);
            player.getTimelines().add(timeline);
            timelineRepository.save(timeline);
        }
        return playerRepository.save(player);
    }

    public Optional<Player> getPlayerById(Long id) {
        return playerRepository.findById(id);
    }

    public void deletePlayer(Long id) {
        playerRepository.deleteById(id);
    }
}

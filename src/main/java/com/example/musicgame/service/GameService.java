package com.example.musicgame.service;

import com.example.musicgame.model.Card;
import com.example.musicgame.model.Player;
import com.example.musicgame.repository.CardRepository;
import com.example.musicgame.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class GameService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private PlayerRepository playerRepository;

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    public Card startGame(Player player) {
        List<Card> cards = cardRepository.findAll();
        if (cards.isEmpty()) {
            throw new IllegalStateException("No cards available");
        }
        return getRandomCard(cards);
    }

    public Card getCard(Long playerId) {
        Optional<Player> player = playerRepository.findById(playerId);
        if (player.isPresent()) {
            List<Card> cards = cardRepository.findAll();
            if (cards.isEmpty()) {
                throw new IllegalStateException("No cards available");
            }
            return getRandomCard(cards);
        } else {
            throw new IllegalArgumentException("Invalid player ID");
        }
    }

    public boolean submitCard(Long playerId, Card card) {
        // Implement logic for submitting card (e.g., updating player timeline)
        return true;
    }

    // Add the following methods to your GameService class

    public boolean submitTimeline(Long playerId, List<Card> timeline) {
        Optional<Player> playerOpt = playerRepository.findById(playerId);
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            player.setTimeline(timeline);
            playerRepository.save(player);
            return true;
        } else {
            throw new IllegalArgumentException("Invalid player ID");
        }
    }

    public boolean validateTimeline(Long playerId) {
        Optional<Player> playerOpt = playerRepository.findById(playerId);
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            List<Card> timeline = player.getTimeline();
            return isTimelineValid(timeline);
        } else {
            throw new IllegalArgumentException("Invalid player ID");
        }
    }

    private boolean isTimelineValid(List<Card> timeline) {
        for (int i = 1; i < timeline.size(); i++) {
            if (timeline.get(i).getYear() < timeline.get(i - 1).getYear()) {
                return false;
            }
        }
        return true;
    }

    private Card getRandomCard(List<Card> cards) {
        Random random = new Random();
        int index = random.nextInt(cards.size());
        return cards.get(index);
    }
}

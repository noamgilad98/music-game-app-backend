package com.example.musicgame.service;

import com.example.musicgame.model.Card;
import com.example.musicgame.model.Game;
import com.example.musicgame.model.GameState;
import com.example.musicgame.model.Player;
import com.example.musicgame.repository.CardRepository;
import com.example.musicgame.repository.GameRepository;
import com.example.musicgame.repository.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class GameService {
    @Autowired
    private CardRepository cardRepository;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private DeckService deckService;

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    public Card startGame(Player player) {

        Game game = new Game(deckService.createDeck(), GameState.NOT_STARTED);
        playerRepository.save(player);
        game.setGameState(GameState.IN_PROGRESS);
        game = gameRepository.save(game);
        return null;
    }

    public Card getCard(Long playerId, Long gameId) {
        Optional<Player> playerOpt = playerRepository.findById(playerId);
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            Game game = gameRepository.findById(gameId).orElse(null);
            if (game == null) {
                throw new IllegalArgumentException("Invalid game ID");
            }
            game.pullCardFromDeck(playerId);
        } else {
            throw new IllegalArgumentException("Invalid player ID");
        }
        return null;
    }

    public boolean submitCard(Long playerId, Card card) {
        return false;
    }

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

    public Map<String, Object> submitAndValidate(Long playerId, Card card) {
        Map<String, Object> response = new HashMap<>();
        boolean isValid = false;
        boolean hasWon = false;

        Optional<Player> playerOpt = playerRepository.findById(playerId);
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            List<Card> timeline = player.getTimeline();
            timeline.add(card);
            player.setTimeline(timeline);
            playerRepository.save(player);

            isValid = isTimelineValid(timeline);
            hasWon = timeline.size() >= 10;
        } else {
            throw new IllegalArgumentException("Invalid player ID");
        }

        response.put("isValid", isValid);
        response.put("hasWon", hasWon);
        return response;
    }
}

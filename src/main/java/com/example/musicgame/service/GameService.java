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

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    public Card startGame(Player player) {
        List<Card> allCards = cardRepository.findAll();
        Collections.shuffle(allCards); // Shuffle cards to randomize the deck
        Game game = new Game(player, allCards, GameState.NOT_STARTED);
        game = gameRepository.save(game); // Save the game to ensure persistence

        player.setGame(game);
        playerRepository.save(player);

        return drawCardFromDeck(game);
    }

    public Card getCard(Long playerId) {
        Optional<Player> playerOpt = playerRepository.findById(playerId);
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            Game game = player.getGame();
            return drawCardFromDeck(game);
        } else {
            throw new IllegalArgumentException("Invalid player ID");
        }
    }

    public boolean submitCard(Long playerId, Card card) {
        Optional<Player> playerOpt = playerRepository.findById(playerId);
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            Game game = player.getGame();
            List<Card> timeline = player.getTimeline();
            timeline.add(card);
            player.setTimeline(timeline);
            playerRepository.save(player);
            return true;
        }
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

    private Card drawCardFromDeck(Game game) {
        List<Card> deck = game.getCards();
        if (deck.isEmpty()) {
            throw new IllegalStateException("No more cards in the deck");
        }
        Card drawnCard = deck.remove(0);
        gameRepository.save(game); // Update the game with the new state of the deck
        return drawnCard;
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

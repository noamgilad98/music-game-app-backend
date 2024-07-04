package com.example.musicgame.service;

import com.example.musicgame.model.Card;
import com.example.musicgame.model.Player;
import com.example.musicgame.repository.CardRepository;
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

    public Player savePlayer(Player player) {
        return playerRepository.save(player);
    }

    public Card startGame(Player player) {
        List<Card> allCards = cardRepository.findAll();
        if (allCards.isEmpty()) {
            throw new IllegalStateException("No cards available");
        }

        // Initialize the player's deck with a shuffled copy of all cards
        List<Card> playerDeck = shuffleDeck(allCards);
        player.setDeck(playerDeck);
        playerRepository.save(player);

        return drawCardFromDeck(player);
    }

    public Card getCard(Long playerId) {
        Optional<Player> playerOpt = playerRepository.findById(playerId);
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            return drawCardFromDeck(player);
        } else {
            throw new IllegalArgumentException("Invalid player ID");
        }
    }

    public boolean submitCard(Long playerId, Card card) {
        Optional<Player> playerOpt = playerRepository.findById(playerId);
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
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

    private Card getRandomCard(List<Card> cards) {
        Random random = new Random();
        int index = random.nextInt(cards.size());
        return cards.get(index);
    }

    private List<Card> shuffleDeck(List<Card> cards) {
        List<Card> shuffledDeck = new ArrayList<>(cards);
        Random random = new Random();
        for (int i = shuffledDeck.size() - 1; i > 0; i--) {
            int j = random.nextInt(i + 1);
            Card temp = shuffledDeck.get(i);
            shuffledDeck.set(i, shuffledDeck.get(j));
            shuffledDeck.set(j, temp);
        }
        return shuffledDeck;
    }

    private Card drawCardFromDeck(Player player) {
        List<Card> deck = player.getDeck();
        if (deck.isEmpty()) {
            throw new IllegalStateException("No more cards in the deck");
        }
        Card drawnCard = deck.remove(0);
        player.setDeck(deck);
        playerRepository.save(player);
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

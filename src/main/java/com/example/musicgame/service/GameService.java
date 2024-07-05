package com.example.musicgame.service;

import com.example.musicgame.model.Card;
import com.example.musicgame.model.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Service
public class GameService {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private DeckService deckService;
    @Autowired
    private TimelineService timelineService;

    @Transactional
    public Player savePlayer(Player player) {
        return playerService.savePlayer(player);
    }

    @Transactional
    public Card startGame(Player player) {
        Player savedPlayer = playerService.savePlayer(player);
        return deckService.addUniqueRandomCardToDeck(savedPlayer.getDeck().getId());
    }

    @Transactional
    public Card getCard(Long playerId) {
        Player player = playerService.getPlayerById(playerId).orElseThrow(() -> new IllegalArgumentException("Invalid player ID"));
        return deckService.addUniqueRandomCardToDeck(player.getDeck().getId());
    }

    @Transactional
    public boolean submitCard(Long playerId, Card card) {
        return timelineService.addCardToTimeline(playerId, card);
    }

    @Transactional
    public boolean submitTimeline(Long playerId, Set<Card> timelineCards) {
        return timelineService.submitTimeline(playerId, timelineCards);
    }

    @Transactional
    public boolean validateTimeline(Long playerId) {
        return timelineService.validateTimeline(playerId);
    }

    @Transactional
    public Map<String, Object> submitAndValidate(Long playerId, Card card) {
        Map<String, Object> response = new HashMap<>();
        boolean isCardSubmitted = submitCard(playerId, card);
        boolean isTimelineValid = validateTimeline(playerId);
        response.put("cardSubmitted", isCardSubmitted);
        response.put("isTimelineValid", isTimelineValid);

        if (isTimelineValid) {
            Player player = playerService.getPlayerById(playerId).orElseThrow(() -> new IllegalArgumentException("Invalid player ID"));
            if (player.getTimelines().iterator().next().getCards().size() >= 10) {
                response.put("gameWon", true);
            } else {
                response.put("gameWon", false);
            }
        } else {
            response.put("gameWon", false);
        }

        return response;
    }
}

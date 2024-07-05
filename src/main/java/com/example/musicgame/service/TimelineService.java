package com.example.musicgame.service;

import com.example.musicgame.model.Card;
import com.example.musicgame.model.Player;
import com.example.musicgame.model.Timeline;
import com.example.musicgame.repository.PlayerRepository;
import com.example.musicgame.repository.TimelineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Service
public class TimelineService {

    @Autowired
    private TimelineRepository timelineRepository;
    @Autowired
    private PlayerRepository playerRepository;

    @Transactional
    public boolean addCardToTimeline(Long playerId, Card card) {
        Optional<Player> playerOpt = playerRepository.findById(playerId);
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            Timeline timeline = player.getTimelines().iterator().next();
            timeline.getCards().add(card);
            timelineRepository.save(timeline);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean submitTimeline(Long playerId, Set<Card> timelineCards) {
        Optional<Player> playerOpt = playerRepository.findById(playerId);
        if (playerOpt.isPresent()) {
            Player player = playerOpt.get();
            Timeline timeline = player.getTimelines().iterator().next();
            timeline.setCards(timelineCards);
            timelineRepository.save(timeline);
            return true;
        }
        return false;
    }

    @Transactional
    public boolean validateTimeline(Long playerId) {
        // Implement your validation logic here
        return true;
    }
}

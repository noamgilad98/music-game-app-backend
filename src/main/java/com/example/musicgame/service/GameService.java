package com.example.musicgame.service;

import com.example.musicgame.model.*;
import com.example.musicgame.repository.GameRepository;
import com.example.musicgame.repository.PlayerRepository;
import com.example.musicgame.repository.CardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private DeckService deckService;

    public Game createGame(Game game) {
        return gameRepository.save(game);
    }

    public Game getGameById(Long gameId) {
        return gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));
    }

    public Game startGame(Long gameId) {
        Game game = getGameById(gameId);
        game.setGameState(GameState.STARTED);
        game.setDeck(deckService.createDeck());
        return gameRepository.save(game);
    }

    public Game endGame(Long gameId) {
        Game game = getGameById(gameId);
        game.setGameState(GameState.ENDED);
        return gameRepository.save(game);
    }

    public Card drawCard(Long gameId, Long playerId) {
        Game game = getGameById(gameId);
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));
        Card drawnCard = game.getDeck().drawCard();
        player.addCardToHand(drawnCard);
        playerRepository.save(player);
        return drawnCard;
    }

    public Game placeCard(Long gameId, Long playerId, Long cardId, int position) {
        Game game = getGameById(gameId);
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new RuntimeException("Card not found"));

        boolean isValidPlacement = validateCardPlacement(player, card, position);
        if (isValidPlacement) {
            player.getTimeline().add(position, card);
        } else {
            player.getHand().remove(card);
        }

        playerRepository.save(player);
        return game;
    }

    private boolean validateCardPlacement(Player player, Card card, int position) {
        List<Card> timeline = player.getTimeline();
        if (position < 0 || position > timeline.size()) {
            return false;
        }

        Card previousCard = (position > 0) ? timeline.get(position - 1) : null;
        Card nextCard = (position < timeline.size()) ? timeline.get(position) : null;

        if (previousCard != null && previousCard.getYear() > card.getYear()) {
            return false;
        }

        if (nextCard != null && nextCard.getYear() < card.getYear()) {
            return false;
        }

        return true;
    }

    public List<Game> getAllGames() {
        return gameRepository.findAll();
    }
}

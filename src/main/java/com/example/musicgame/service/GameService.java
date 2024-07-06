package com.example.musicgame.service;

import com.example.musicgame.model.*;
import com.example.musicgame.repository.GameRepository;
import com.example.musicgame.repository.PlayerRepository;
import com.example.musicgame.repository.CardRepository;
import com.example.musicgame.repository.UserRepository;
import com.example.musicgame.util.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class GameService {

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeckService deckService;

    @Autowired
    private PlayerService playerService;

    @Autowired
    private JwtUtil jwtUtil;

    public Game createGame(String token) {
        String username = jwtUtil.getUsernameFromToken(token.substring(7)); // Remove "Bearer " prefix
        User user = userRepository.findByUsername(username).orElseThrow(() -> new RuntimeException("User not found"));
        List<Card> cards = cardRepository.findAll(); // Or however you generate the cards
        Deck deck = deckService.createDeck(cards); // Ensure the deck is properly initialized
        Game createdGame = new Game(deck, GameState.CREATED);
        gameRepository.save(createdGame);
        addPlayerToGame(createdGame.getId(), user);
        return gameRepository.save(createdGame);
    }

    public Game getGameById(Long gameId) {
        return gameRepository.findById(gameId).orElseThrow(() -> new RuntimeException("Game not found"));
    }

    public Game startGame(Long gameId) {
        Game game = getGameById(gameId);
        List<Card> cards = cardRepository.findAll(); // Or however you generate the cards
        Deck deck = deckService.createDeck(cards); // Ensure the deck is properly initialized
        game.setGameState(GameState.STARTED);
        game.setDeck(deck);
        return gameRepository.save(game);
    }

    public Game addPlayerToGame(Long gameId, User user) {
        Game game = getGameById(gameId);
        Player player = playerService.createPlayer(user.getUsername(), game); // Create player
        player.setGame(game);
        playerRepository.save(player);
        game.getPlayers().add(player);
        return gameRepository.save(game);
    }

    public Game endGame(Long gameId) {
        Game game = getGameById(gameId);
        game.setGameState(GameState.ENDED);
        return gameRepository.save(game);
    }

    public Card drawCard(Long gameId, Long playerId) {
        Game game = gameRepository.findById(gameId)
                .orElseThrow(() -> new RuntimeException("Game not found"));

        Player player = game.getPlayers().stream()
                .filter(p -> p.getId().equals(playerId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Player not found"));

        Optional<Card> card = Optional.ofNullable(game.getDeck().drawCard());
        if (card.isEmpty()) {
            throw new RuntimeException("No card drawn from the deck");
        }

        Card drawnCard = card.get();
        gameRepository.save(game);

        return drawnCard;
    }

    public Game placeCard(Long gameId, Long playerId, Long cardId, int position) {
        Objects.requireNonNull(gameId, "gameId must not be null");
        Objects.requireNonNull(playerId, "playerId must not be null");
        Objects.requireNonNull(cardId, "cardId must not be null");

        Game game = getGameById(gameId);
        Player player = playerRepository.findById(playerId).orElseThrow(() -> new RuntimeException("Player not found"));
        Card card = cardRepository.findById(cardId).orElseThrow(() -> new RuntimeException("Card not found"));

        boolean isValidPlacement = validateCardPlacement(player, card, position);
        if (isValidPlacement) {
            player.addCardToTimeline(card, position);
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

package com.example.musicgame;

import com.example.musicgame.model.*;
import com.example.musicgame.repository.*;
import com.example.musicgame.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TimeLineRepository timeLineRepository;

    @Autowired
    private SpotifyService spotifyService;

    @Autowired
    private CardService cardService;

    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Override
    public void run(String... args) throws Exception {
        // Delete all existing data
        timeLineRepository.deleteAll();
        playerRepository.deleteAll();
        gameRepository.deleteAll();
        deckRepository.deleteAll();
        userRepository.deleteAll();
        cardRepository.deleteAll();

        // Load and save cards
        for (String trackId : TrackIds.TRACK_IDS) {
            try {
                Track track = spotifyService.getTrack(trackId);
                if (track != null && track.getAlbum() != null && track.getArtists() != null && track.getArtists().length > 0) {
                    if (track.getPreview_url() == null || track.getPreview_url().isEmpty()) {
                        logger.warn("Track with ID: {} has no preview URL, skipping...", trackId);
                        continue;
                    }
                    int releaseYear = Integer.parseInt(track.getAlbum().getRelease_date().substring(0, 4));
                    Card card = new Card(
                            track.getName(),
                            track.getArtists()[0].getName(),
                            releaseYear,
                            track.getUri(),
                            track.getPreview_url()
                    );
                    cardRepository.save(card);
                    logger.info("Saved card: {}", card);
                }
            } catch (Exception e) {
                logger.error("Failed to load track with ID: {}", trackId, e);
            }
        }

        // Create and save users
        User user1 = new User("user1", "password1");
        User user2 = new User("user2", "password2");
        userRepository.save(user1);
        userRepository.save(user2);
        logger.info("Saved users: {}, {}", user1, user2);

        // Create and save deck
        Deck deck = new Deck();
        deck = deckRepository.save(deck); // Save and attach the deck to persistence context

        // Create and save game with deck
        Game game = new Game(GameState.CREATED, deck);
        // Create and save players
        Player player1 = new Player("Player1", game);
        Player player2 = new Player("Player2", game);
        game.addPlayer(player1);
        game.addPlayer(player2);
        gameRepository.save(game);
        logger.info("Saved game: {}", game);

        // Create and save players
        playerRepository.save(player1);
        playerRepository.save(player2);
        logger.info("Saved players: {}, {}", player1, player2);

        // Create and save timelines
        TimeLine timeLine1 = new TimeLine(player1);
        TimeLine timeLine2 = new TimeLine(player2);
        timeLineRepository.save(timeLine1);
        timeLineRepository.save(timeLine2);
        logger.info("Saved timelines: {}, {}", timeLine1, timeLine2);

        player1.setTimeline(timeLine1);
        player2.setTimeline(timeLine2);
        playerRepository.save(player1);
        playerRepository.save(player2);

        // Assign cards to deck and timelines
        Set<Card> cards = new HashSet<>(cardRepository.findAll());
        for (Card card : cards) {
            deck.getCards().add(card);
            card.setDeck(deck);
            timeLine1.getCards().add(card);  // Adding cards to player's timeline for demonstration
            card.setTimeline(timeLine1);
            timeLine2.getCards().add(card);
            card.setTimeline(timeLine2);
        }

        deckRepository.save(deck);
        timeLineRepository.save(timeLine1);
        timeLineRepository.save(timeLine2);
        logger.info("Assigned cards to deck and timelines.");
    }
}

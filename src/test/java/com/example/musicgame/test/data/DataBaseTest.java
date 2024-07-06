package com.example.musicgame.test.data;

import com.example.musicgame.model.*;
import com.example.musicgame.repository.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
public class DataBaseTest {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private DeckRepository deckRepository;

    @Autowired
    private GameRepository gameRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TimeLineRepository timeLineRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    @Transactional
    public void setup() {
        // Clean the database
        gameRepository.deleteAll();
        deckRepository.deleteAll();
        playerRepository.deleteAll();
        timeLineRepository.deleteAll();
        cardRepository.deleteAll();
        userRepository.deleteAll();

        // Populate the database with the initial data
        // Add cards
        for (int i = 1; i <= 20; i++) {
            Card card = new Card();
            card.setSongName("Song " + i);
            card.setArtist("Artist " + i);
            card.setYear(2000 + i);
            card.setSpotifyCode("SpotifyCode" + i);
            card.setPreviewUrl("PreviewUrl" + i);
            cardRepository.save(card);
        }

        // Add users
        for (int i = 1; i <= 20; i++) {
            User user = new User();
            user.setUsername("User" + i);
            user.setPassword("Password" + i);
            userRepository.save(user);
        }

        // Add players and timelines
        for (int i = 1; i <= 25; i++) {
            TimeLine timeLine = new TimeLine();
            timeLineRepository.save(timeLine);

            Player player = new Player();
            player.setName("Player" + i);
            player.setTimeLine(timeLine);
            playerRepository.save(player);
        }

        // Add decks
        for (int i = 1; i <= 5; i++) {
            Deck deck = new Deck();
            for (int j = 1; j <= 4; j++) {
                Card card = cardRepository.findById((long) j).orElse(null);
                deck.getCards().add(card);
            }
            deckRepository.save(deck);
        }

        // Add games
        for (int i = 1; i <= 30; i++) {
            Game game = new Game();
            Deck deck = deckRepository.findById((long) ((i % 5) + 1)).orElse(null);
            game.setDeck(deck);
            game.setGameState(GameState.CREATED);
            for (int j = 1; j <= 4; j++) {
                Player player = playerRepository.findById((long) j).orElse(null);
                game.getPlayers().add(player);
            }
            gameRepository.save(game);
        }
    }

    @Test
    @Transactional
    public void testDatabaseInitialization() {
        // Check cards
        List<Card> cards = cardRepository.findAll();
        assertEquals(20, cards.size(), "There should be 20 cards in the database.");

        // Check users
        List<User> users = userRepository.findAll();
        assertEquals(20, users.size(), "There should be 20 users in the database.");

        // Check players
        List<Player> players = playerRepository.findAll();
        assertEquals(25, players.size(), "There should be 25 players in the database.");

        // Check decks
        List<Deck> decks = deckRepository.findAll();
        assertEquals(5, decks.size(), "There should be 5 decks in the database.");

        // Check games
        List<Game> games = gameRepository.findAll();
        assertEquals(30, games.size(), "There should be 30 games in the database.");

        // Check timelines
        List<TimeLine> timelines = timeLineRepository.findAll();
        assertEquals(25, timelines.size(), "There should be 25 timelines in the database.");

        // Check each timeline for correct card count
        for (TimeLine timeLine : timelines) {
            assertEquals(4, timeLine.getCards().size(), "Each timeline should have 4 cards.");
        }

        // Check each game for correct player count
        for (Game game : games) {
            assertEquals(4, game.getPlayers().size(), "Each game should have 4 players.");
        }

        System.out.println("Database validation successful.");
    }

    @Test
    @Transactional
    public void testCardProperties() {
        List<Card> cards = cardRepository.findAll();
        for (Card card : cards) {
            assertNotNull(card.getSongName(), "Card song name should not be null.");
            assertNotNull(card.getArtist(), "Card artist should not be null.");
            assertTrue(card.getYear() >= 2000, "Card year should be valid.");
            assertNotNull(card.getSpotifyCode(), "Card Spotify code should not be null.");
            assertNotNull(card.getPreviewUrl(), "Card preview URL should not be null.");
        }
    }

    @Test
    @Transactional
    public void testUserProperties() {
        List<User> users = userRepository.findAll();
        for (User user : users) {
            assertNotNull(user.getUsername(), "User username should not be null.");
            assertNotNull(user.getPassword(), "User password should not be null.");
        }
    }

    @Test
    @Transactional
    public void testPlayerProperties() {
        List<Player> players = playerRepository.findAll();
        for (Player player : players) {
            assertNotNull(player.getName(), "Player name should not be null.");
            assertNotNull(player.getTimeLine(), "Player timeline should not be null.");
        }
    }

    @Test
    @Transactional
    public void testDeckProperties() {
        List<Deck> decks = deckRepository.findAll();
        for (Deck deck : decks) {
            assertNotNull(deck.getCards(), "Deck cards should not be null.");
            assertEquals(4, deck.getCards().size(), "Each deck should have 4 cards.");
        }
    }

    @Test
    @Transactional
    public void testGameProperties() {
        List<Game> games = gameRepository.findAll();
        for (Game game : games) {
            assertNotNull(game.getDeck(), "Game deck should not be null.");
            assertNotNull(game.getGameState(), "Game state should not be null.");
            assertEquals(4, game.getPlayers().size(), "Each game should have 4 players.");
        }
    }

    @Test
    @Transactional
    public void testTimeLineProperties() {
        List<TimeLine> timelines = timeLineRepository.findAll();
        for (TimeLine timeLine : timelines) {
            assertNotNull(timeLine.getCards(), "Timeline cards should not be null.");
            assertEquals(4, timeLine.getCards().size(), "Each timeline should have 4 cards.");
        }
    }

    @Test
    @Transactional
    public void testPlayerTimelineRelationship() {
        List<Player> players = playerRepository.findAll();
        for (Player player : players) {
            TimeLine timeLine = player.getTimeLine();
            assertNotNull(timeLine, "Player should have a timeline.");
            assertTrue(timeLine.getCards().size() <= 4, "Timeline should not have more than 4 cards.");
        }
    }

    @Test
    @Transactional
    public void testGamePlayerRelationship() {
        List<Game> games = gameRepository.findAll();
        for (Game game : games) {
            List<Player> players = game.getPlayers();
            assertNotNull(players, "Game should have players.");
            assertEquals(4, players.size(), "Game should have exactly 4 players.");
        }
    }

    @Test
    @Transactional
    public void testDeckCardRelationship() {
        List<Deck> decks = deckRepository.findAll();
        for (Deck deck : decks) {
            List<Card> cards = deck.getCards();
            assertNotNull(cards, "Deck should have cards.");
            assertEquals(4, cards.size(), "Deck should have exactly 4 cards.");
        }
    }
}

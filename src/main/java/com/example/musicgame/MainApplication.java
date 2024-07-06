//package com.example.musicgame;
//
//import com.example.musicgame.model.*;
//import com.example.musicgame.repository.*;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.boot.SpringApplication;
//import org.springframework.boot.autoconfigure.SpringBootApplication;
//
//import java.util.ArrayList;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Random;
//import java.util.Set;
//
//@SpringBootApplication
//public class MainApplication implements CommandLineRunner {
//
//    @Autowired
//    private CardRepository cardRepository;
//
//    @Autowired
//    private DeckRepository deckRepository;
//
//    @Autowired
//    private GameRepository gameRepository;
//
//    @Autowired
//    private PlayerRepository playerRepository;
//
//    @Autowired
//    private TimeLineRepository timeLineRepository;
//
//    @Autowired
//    private UserRepository userRepository;
//
//    public static void main(String[] args) {
//        SpringApplication.run(MainApplication.class, args);
//    }
//
//    @Override
//    public void run(String... args) throws Exception {
//        deleteExistingData();
//        initDatabase();
//    }
//
//    private void deleteExistingData() {
//        gameRepository.deleteAll();
//        playerRepository.deleteAll();
//        deckRepository.deleteAll();
//        cardRepository.deleteAll();
//        timeLineRepository.deleteAll();
//        userRepository.deleteAll();
//        System.out.println("Existing data deleted successfully.");
//    }
//
//    private void initDatabase() {
//        Random random = new Random();
//
//        // Create 20 cards
//        List<Card> cards = new ArrayList<>();
//        for (int i = 1; i <= 20; i++) {
//            Card card = new Card("Song" + i, "Artist" + i, 2000 + i, "spotifyCode" + i, "previewUrl" + i);
//            cards.add(card);
//        }
//        cardRepository.saveAll(cards);
//
//        // Create 20 users
//        List<User> users = new ArrayList<>();
//        for (int i = 1; i <= 20; i++) {
//            User user = new User("user" + i, "password" + i);
//            users.add(user);
//        }
//        userRepository.saveAll(users);
//
//        // Create 25 players
//        List<Player> players = new ArrayList<>();
//        for (int i = 1; i <= 25; i++) {
//            Player player = new Player("Player" + i);
//            players.add(player);
//        }
//        playerRepository.saveAll(players);
//
//        // Create decks
//        List<Deck> decks = new ArrayList<>();
//        for (int i = 0; i < 5; i++) {
//            Deck deck = new Deck(cards.subList(i * 4, (i + 1) * 4));
//            decks.add(deck);
//        }
//        deckRepository.saveAll(decks);
//
//        // Create 30 games
//        List<Game> games = new ArrayList<>();
//        for (int i = 1; i <= 30; i++) {
//            Deck deck = decks.get(random.nextInt(decks.size()));
//            Game game = new Game(deck, GameState.CREATED);
//            Set<Player> gamePlayers = new HashSet<>();
//            while (gamePlayers.size() < 4) {
//                gamePlayers.add(players.get(random.nextInt(players.size())));
//            }
//            gamePlayers.forEach(game::addPlayer);
//            games.add(game);
//        }
//        gameRepository.saveAll(games);
//
//        // Create timelines for each player
//        for (Player player : players) {
//            TimeLine timeLine = new TimeLine();
//            Set<Card> timeLineCards = new HashSet<>();
//            while (timeLineCards.size() < 4) {
//                timeLineCards.add(cards.get(random.nextInt(cards.size())));
//            }
//            int position = 0;
//            for (Card card : timeLineCards) {
//                timeLine.addCardAtPosition(card, position++);
//            }
//            timeLineRepository.save(timeLine);
//            player.setTimeLine(timeLine);
//            playerRepository.save(player);
//        }
//
//        System.out.println("Database initialized successfully with more data.");
//    }
//}

package com.example.musicgame;

import com.example.musicgame.model.Card;
import com.example.musicgame.repository.*;
import com.example.musicgame.service.CardService;
import com.example.musicgame.service.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private SpotifyService spotifyService;

    @Autowired
    private CardService cardService;

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


    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);

    @Override
    public void run(String... args) throws Exception {
//        deleteExistingData();
//    initDatabase();
    }

    private void deleteExistingData() {
        gameRepository.deleteAll();
        playerRepository.deleteAll();
        deckRepository.deleteAll();
        cardRepository.deleteAll();
        timeLineRepository.deleteAll();
        userRepository.deleteAll();
        System.out.println("Existing data deleted successfully.");
    }


    private void initDatabase() {

        cardRepository.resetSequence();

        List<Card> cards = new ArrayList<>();

        cards.add(new Card("Shape of You", "Ed Sheeran", 2017, "spotify:track:7qiZfU4dY1lWllzX7mPBI3", "https://p.scdn.co/mp3-preview/7339548839a263fd721d01eb3364a848cad16fa7?cid=909cdedbe38145d7a5848f6d36392b69"));
        cards.add(new Card("HIGHEST IN THE ROOM", "Travis Scott", 2019, "spotify:track:3eekarcy7kvN4yt5ZFzltW", "https://p.scdn.co/mp3-preview/387b31c31b72f0c16e33d0c78bab869b0a0f4eb3?cid=909cdedbe38145d7a5848f6d36392b69"));
        cards.add(new Card("Savage Remix (feat. Beyoncé)", "Megan Thee Stallion", 2020, "spotify:track:5v4GgrXPMghOnBBLmveLac", "https://p.scdn.co/mp3-preview/adf3906bbf048ebb7b9bb0043560a129beb77b36?cid=909cdedbe38145d7a5848f6d36392b69"));
        cards.add(new Card("Meant to Be (feat. Florida Georgia Line)", "Bebe Rexha", 2017, "spotify:track:7iDa6hUg2VgEL1o1HjmfBn", "https://p.scdn.co/mp3-preview/fa4d66e50c72c03ced38f802adf41e44cad2c2e4?cid=909cdedbe38145d7a5848f6d36392b69"));
        cards.add(new Card("Photograph", "Ed Sheeran", 2014, "spotify:track:1HNkqx9Ahdgi1Ixy2xkKkL", "https://p.scdn.co/mp3-preview/d90f4e5f15d8ed411307945560b1db8cca6b253b?cid=909cdedbe38145d7a5848f6d36392b69"));
        cards.add(new Card("It Ain't Me (with Selena Gomez)", "Kygo", 2017, "spotify:track:3eR23VReFzcdmS7TYCrhCe", "https://p.scdn.co/mp3-preview/b78a834a199aaaff9f0b3025077b2ee302f4c701?cid=909cdedbe38145d7a5848f6d36392b69"));
        cards.add(new Card("Roses (feat. ROZES)", "The Chainsmokers", 2016, "spotify:track:6O6M7pJLABmfBRoGZMu76Y", "https://p.scdn.co/mp3-preview/a0f968725f2164068bee9379d9fce23fbe9bea74?cid=909cdedbe38145d7a5848f6d36392b69"));
        cards.add(new Card("Broccoli (feat. Lil Yachty)", "DRAM", 2016, "spotify:track:7yyRTcZmCiyzzJlNzGC9Ol", "https://p.scdn.co/mp3-preview/72ead3dff2729ca0e0b7fa4b692a73aaa5231f4d?cid=909cdedbe38145d7a5848f6d36392b69"));
        cards.add(new Card("Closer", "The Chainsmokers", 2016, "spotify:track:7BKLCZ1jbUBVqRi2FVlTVw", "https://p.scdn.co/mp3-preview/cfd565c4d3c621771e6d25d99749b9fc200e396c?cid=909cdedbe38145d7a5848f6d36392b69"));
        cards.add(new Card("Story of My Life", "One Direction", 2013, "spotify:track:4nVBt6MZDDP6tRVdQTgxJg", "https://p.scdn.co/mp3-preview/9c3f89f926a702034ae182e723b2d601c2bd754d?cid=909cdedbe38145d7a5848f6d36392b69"));
        cards.add(new Card("Run It! (feat. Juelz Santana)", "Chris Brown", 2005, "spotify:track:7xYnUQigPoIDAMPVK79NEq", "https://p.scdn.co/mp3-preview/74a14b8bc5d019c40321871ad15e024576d6dbfc?cid=909cdedbe38145d7a5848f6d36392b69"));
        cards.add(new Card("Slide (feat. Frank Ocean & Migos)", "Calvin Harris", 2017, "spotify:track:6gpcs5eMhJwax4mIfKDYQk", "https://p.scdn.co/mp3-preview/16f9a0b74ce7aa75cfcfcd2bd928cda87ba11045?cid=909cdedbe38145d7a5848f6d36392b69"));
        cards.add(new Card("Take It Easy - 2013 Remaster", "Eagles", 1972, "spotify:track:4yugZvBYaoREkJKtbG08Qr", "https://p.scdn.co/mp3-preview/4b32d39b05829f2c442aa869354f0f63cefcef24?cid=909cdedbe38145d7a5848f6d36392b69"));
        cards.add(new Card("Hips Don't Lie (feat. Wyclef Jean)", "Shakira", 2005, "spotify:track:3ZFTkvIE7kyPt6Nu3PEa7V", "https://p.scdn.co/mp3-preview/374b492571c9ba59c2c4b455ab79ee7501adab93?cid=909cdedbe38145d7a5848f6d36392b69"));
        cards.add(new Card("Respect", "Aretha Franklin", 1967, "spotify:track:7s25THrKz86DM225dOYwnr", "https://p.scdn.co/mp3-preview/7768dd513193e30ab1ad19deeff2dcc63d2c7555?cid=909cdedbe38145d7a5848f6d36392b69"));
        cards.add(new Card("Just the Way You Are", "Bruno Mars", 2010, "spotify:track:7BqBn9nzAq8spo5e7cZ0dJ", "https://p.scdn.co/mp3-preview/6d1a901b10c7dc609d4c8628006b04bc6e672be8?cid=909cdedbe38145d7a5848f6d36392b69"));
        cards.add(new Card("Higher Love", "Kygo", 2019, "spotify:track:6oJ6le65B3SEqPwMRNXWjY", "https://p.scdn.co/mp3-preview/6737d20b6eba8e6bc5b9270d884b04022f38d713?cid=909cdedbe38145d7a5848f6d36392b69"));
        cards.add(new Card("Say Something", "A Great Big World", 2014, "spotify:track:6Vc5wAMmXdKIAM7WUoEb7N", "https://p.scdn.co/mp3-preview/0178f39b1dd08aa1111138fa775c7b8826cdee7f?cid=909cdedbe38145d7a5848f6d36392b69"));

        cardRepository.saveAll(cards);
        System.out.println("Cards added successfully.");
    }
}


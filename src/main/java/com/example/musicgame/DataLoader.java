//package com.example.musicgame;
//
//import com.example.musicgame.model.Card;
//import com.example.musicgame.model.Track;
//import com.example.musicgame.repository.CardRepository;
//import com.example.musicgame.service.CardService;
//import com.example.musicgame.service.SpotifyService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.CommandLineRunner;
//import org.springframework.stereotype.Component;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//@Component
//public class DataLoader implements CommandLineRunner {
//
//    @Autowired
//    private CardRepository cardRepository;
//
//    @Autowired
//    private SpotifyService spotifyService;
//
//    @Autowired
//    private CardService cardService;
//
//    private static final Logger logger = LoggerFactory.getLogger(DataLoader.class);
//
//    @Override
//    public void run(String... args) throws Exception {
//        //cardService.resetCardIdSequence();
//
//        if (cardRepository.count() == 0) {
//            for (String trackId : TrackIds.TRACK_IDS) {
//                try {
//                    Track track = spotifyService.getTrack(trackId);
//                    if (track != null && track.getAlbum() != null && track.getArtists() != null && track.getArtists().length > 0) {
//                        if (track.getPreview_url() == null || track.getPreview_url().isEmpty()) {
//                            logger.warn("Track with ID: {} has no preview URL, skipping...", trackId);
//                            continue;
//                        }
//                        int releaseYear = Integer.parseInt(track.getAlbum().getRelease_date().substring(0, 4));
//                        Card card = new Card(
//                                track.getName(),
//                                track.getArtists()[0].getName(),
//                                releaseYear,
//                                track.getUri(),
//                                track.getPreview_url()
//                        );
//                        cardRepository.save(card);
//                        logger.info("Saved card: {}", card);
//                    }
//                } catch (Exception e) {
//                    logger.error("Failed to load track with ID: {}", trackId, e);
//                }
//            }
//        }
//    }
//}

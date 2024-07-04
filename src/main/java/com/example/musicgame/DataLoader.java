package com.example.musicgame;

import com.example.musicgame.model.Card;
import com.example.musicgame.model.Track;
import com.example.musicgame.repository.CardRepository;
import com.example.musicgame.service.SpotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private CardRepository cardRepository;

    @Autowired
    private SpotifyService spotifyService;

    @Override
    public void run(String... args) throws Exception {
        if (cardRepository.count() == 0) {
            // Example Spotify track IDs
            String[] trackIds = {
                    "11dFghVXANMlKmJXsNCbNl",
                    "7ouMYWpwJ422jRcDASZB7P",
                    "1dGr1c8CrMLDpV6mPbImSI"
            };

            for (String trackId : trackIds) {
                Track track = spotifyService.getTrack(trackId);
                if (track != null && track.getAlbum() != null && track.getArtists() != null && track.getArtists().length > 0) {
                    int releaseYear = Integer.parseInt(track.getAlbum().getRelease_date().substring(0, 4));
                    Card card = new Card(
                            track.getName(),
                            track.getArtists()[0].getName(),
                            releaseYear,
                            track.getUri()
                    );
                    cardRepository.save(card);
                }
            }
        }
    }
}

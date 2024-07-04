package com.example.musicgame.service;

import com.example.musicgame.model.Track;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@Service
public class SpotifyService {

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    private String accessToken;

    public String getAccessToken() {
        if (accessToken == null) {
            fetchAccessToken();
        }
        return accessToken;
    }

    private void fetchAccessToken() {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(clientId, clientSecret);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        String body = "grant_type=client_credentials";

        HttpEntity<String> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map> response = restTemplate.postForEntity(
                "https://accounts.spotify.com/api/token",
                request,
                Map.class
        );

        Map<String, String> responseBody = response.getBody();
        this.accessToken = responseBody.get("access_token");
    }

    public Track getTrack(String trackId) {
        RestTemplate restTemplate = new RestTemplate();

        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(getAccessToken());
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<Track> response = restTemplate.exchange(
                "https://api.spotify.com/v1/tracks/" + trackId,
                HttpMethod.GET,
                entity,
                Track.class
        );

        return response.getBody();
    }
}

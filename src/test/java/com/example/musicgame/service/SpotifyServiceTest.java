package com.example.musicgame.service;

import com.example.musicgame.model.Track;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.*;

import java.lang.reflect.Field;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application.properties")
class SpotifyServiceTest {

    @InjectMocks
    private SpotifyService spotifyService;

    @Mock
    private RestTemplate restTemplate;

    @Value("${spotify.client.id}")
    private String clientId;

    @Value("${spotify.client.secret}")
    private String clientSecret;

    @BeforeEach
    void setUp() throws Exception {
        MockitoAnnotations.openMocks(this);
        spotifyService = new SpotifyService();

        // Set private fields using reflection
        setPrivateField(spotifyService, "clientId", clientId);
        setPrivateField(spotifyService, "clientSecret", clientSecret);

        // Mocking access token fetch
        mockAccessTokenFetch();

        // Call fetchAccessToken to set the access token for the test
        spotifyService.getAccessToken();
    }

    private void setPrivateField(Object object, String fieldName, String value) throws Exception {
        Field field = object.getClass().getDeclaredField(fieldName);
        field.setAccessible(true);
        field.set(object, value);
    }

    private void mockAccessTokenFetch() {
        Map<String, String> responseBody = new HashMap<>();
        responseBody.put("access_token", "test_access_token");

        when(restTemplate.postForEntity(
                eq("https://accounts.spotify.com/api/token"),
                any(HttpEntity.class),
                eq(Map.class)
        )).thenReturn(new ResponseEntity<>(responseBody, HttpStatus.OK));
    }

    @Test
    void testGetTrack() {
        String trackId = "11dFghVXANMlKmJXsNCbNl";
        Track track = new Track();
        track.setId(trackId);
        track.setName("Test Track");

        HttpHeaders headers = createHeadersWithBearerAuth(spotifyService.getAccessToken());
        HttpEntity<String> entity = new HttpEntity<>(headers);

        // Log headers and entity
        System.out.println("Headers: " + headers);
        System.out.println("Entity: " + entity);

        when(restTemplate.exchange(
                "https://api.spotify.com/v1/tracks/" + trackId,
                HttpMethod.GET,
                entity,
                Track.class
        )).thenReturn(new ResponseEntity<>(track, HttpStatus.OK));

        // Log before fetching the track
        System.out.println("Fetching track with ID: " + trackId);

        Track fetchedTrack = spotifyService.getTrack(trackId);

        // Log the fetched track details
        System.out.println("Fetched Track: " + fetchedTrack);

        assertNotNull(fetchedTrack);
        assertEquals("Test Track", fetchedTrack.getName());
    }

    private HttpHeaders createHeadersWithBearerAuth(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));
        return headers;
    }
}

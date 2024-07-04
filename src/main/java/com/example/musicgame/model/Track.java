package com.example.musicgame.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Track {
    private String id;
    private String name;
    private String uri;
    private Album album;
    private Artist[] artists;
    private int disc_number;
    private int duration_ms;
    private boolean explicit;
    private ExternalIds external_ids;
    private ExternalUrls external_urls;
    private String href;
    private boolean is_playable;
    private Restrictions restrictions;
    private int popularity;
    private String preview_url;
    private int track_number;
    private String type;
    private boolean is_local;

    // Getters and setters
    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public Album getAlbum() {
        return album;
    }

    public void setAlbum(Album album) {
        this.album = album;
    }

    public Artist[] getArtists() {
        return artists;
    }

    public void setArtists(Artist[] artists) {
        this.artists = artists;
    }

    public int getDisc_number() {
        return disc_number;
    }

    public void setDisc_number(int disc_number) {
        this.disc_number = disc_number;
    }

    public int getDuration_ms() {
        return duration_ms;
    }

    public void setDuration_ms(int duration_ms) {
        this.duration_ms = duration_ms;
    }

    public boolean isExplicit() {
        return explicit;
    }

    public void setExplicit(boolean explicit) {
        this.explicit = explicit;
    }

    public ExternalIds getExternal_ids() {
        return external_ids;
    }

    public void setExternal_ids(ExternalIds external_ids) {
        this.external_ids = external_ids;
    }

    public ExternalUrls getExternal_urls() {
        return external_urls;
    }

    public void setExternal_urls(ExternalUrls external_urls) {
        this.external_urls = external_urls;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public boolean isIs_playable() {
        return is_playable;
    }

    public void setIs_playable(boolean is_playable) {
        this.is_playable = is_playable;
    }

    public Restrictions getRestrictions() {
        return restrictions;
    }

    public void setRestrictions(Restrictions restrictions) {
        this.restrictions = restrictions;
    }

    public int getPopularity() {
        return popularity;
    }

    public void setPopularity(int popularity) {
        this.popularity = popularity;
    }

    public String getPreview_url() {
        return preview_url;
    }

    public void setPreview_url(String preview_url) {
        this.preview_url = preview_url;
    }

    public int getTrack_number() {
        return track_number;
    }

    public void setTrack_number(int track_number) {
        this.track_number = track_number;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isIs_local() {
        return is_local;
    }

    public void setIs_local(boolean is_local) {
        this.is_local = is_local;
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Album {
        private String album_type;
        private int total_tracks;
        private String[] available_markets;
        private ExternalUrls external_urls;
        private String href;
        private String id;
        private Image[] images;
        private String name;
        private String release_date;
        private String release_date_precision;
        private Restrictions restrictions;
        private String type;
        private String uri;
        private Artist[] artists;

        // Getters and setters
        public String getAlbum_type() {
            return album_type;
        }

        public void setAlbum_type(String album_type) {
            this.album_type = album_type;
        }

        public int getTotal_tracks() {
            return total_tracks;
        }

        public void setTotal_tracks(int total_tracks) {
            this.total_tracks = total_tracks;
        }

        public String[] getAvailable_markets() {
            return available_markets;
        }

        public void setAvailable_markets(String[] available_markets) {
            this.available_markets = available_markets;
        }

        public ExternalUrls getExternal_urls() {
            return external_urls;
        }

        public void setExternal_urls(ExternalUrls external_urls) {
            this.external_urls = external_urls;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Image[] getImages() {
            return images;
        }

        public void setImages(Image[] images) {
            this.images = images;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getRelease_date() {
            return release_date;
        }

        public void setRelease_date(String release_date) {
            this.release_date = release_date;
        }

        public String getRelease_date_precision() {
            return release_date_precision;
        }

        public void setRelease_date_precision(String release_date_precision) {
            this.release_date_precision = release_date_precision;
        }

        public Restrictions getRestrictions() {
            return restrictions;
        }

        public void setRestrictions(Restrictions restrictions) {
            this.restrictions = restrictions;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }

        public Artist[] getArtists() {
            return artists;
        }

        public void setArtists(Artist[] artists) {
            this.artists = artists;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Artist {
        private ExternalUrls external_urls;
        private Followers followers;
        private String[] genres;
        private String href;
        private String id;
        private Image[] images;
        private String name;
        private int popularity;
        private String type;
        private String uri;

        // Getters and setters
        public ExternalUrls getExternal_urls() {
            return external_urls;
        }

        public void setExternal_urls(ExternalUrls external_urls) {
            this.external_urls = external_urls;
        }

        public Followers getFollowers() {
            return followers;
        }

        public void setFollowers(Followers followers) {
            this.followers = followers;
        }

        public String[] getGenres() {
            return genres;
        }

        public void setGenres(String[] genres) {
            this.genres = genres;
        }

        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Image[] getImages() {
            return images;
        }

        public void setImages(Image[] images) {
            this.images = images;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPopularity() {
            return popularity;
        }

        public void setPopularity(int popularity) {
            this.popularity = popularity;
        }

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getUri() {
            return uri;
        }

        public void setUri(String uri) {
            this.uri = uri;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ExternalIds {
        private String isrc;
        private String ean;
        private String upc;

        // Getters and setters
        public String getIsrc() {
            return isrc;
        }

        public void setIsrc(String isrc) {
            this.isrc = isrc;
        }

        public String getEan() {
            return ean;
        }

        public void setEan(String ean) {
            this.ean = ean;
        }

        public String getUpc() {
            return upc;
        }

        public void setUpc(String upc) {
            this.upc = upc;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class ExternalUrls {
        private String spotify;

        // Getters and setters
        public String getSpotify() {
            return spotify;
        }

        public void setSpotify(String spotify) {
            this.spotify = spotify;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Image {
        private String url;
        private int height;
        private int width;

        // Getters and setters
        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getHeight() {
            return height;
        }

        public void setHeight(int height) {
            this.height = height;
        }

        public int getWidth() {
            return width;
        }

        public void setWidth(int width) {
            this.width = width;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Restrictions {
        private String reason;

        // Getters and setters
        public String getReason() {
            return reason;
        }

        public void setReason(String reason) {
            this.reason = reason;
        }
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Followers {
        private String href;
        private int total;

        // Getters and setters
        public String getHref() {
            return href;
        }

        public void setHref(String href) {
            this.href = href;
        }

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}

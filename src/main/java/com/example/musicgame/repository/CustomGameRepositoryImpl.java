package com.example.musicgame.repository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class CustomGameRepositoryImpl implements CustomGameRepository {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    @Transactional
    public void deleteAllGames() {
        // Delete from dependent tables first
        jdbcTemplate.update("DELETE FROM game_player");
        jdbcTemplate.update("DELETE FROM player");
        jdbcTemplate.update("DELETE FROM time_line");
        jdbcTemplate.update("DELETE FROM card");
        jdbcTemplate.update("DELETE FROM deck");

        // Delete from the game table
        jdbcTemplate.update("DELETE FROM game");
    }
}

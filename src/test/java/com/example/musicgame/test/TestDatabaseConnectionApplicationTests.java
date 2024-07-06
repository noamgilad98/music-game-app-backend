package com.example.musicgame.test;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test") // Ensure it uses the test profile if you have different profiles
public class TestDatabaseConnectionApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${spring.datasource.url}")
    private String dbUrl;

    @Value("${spring.datasource.username}")
    private String dbUser;

    @Value("${spring.datasource.password}")
    private String dbPassword;

    @Test
    void contextLoads() {
    }

    @Test
    void testDatabaseConnection() {
        assertThat(jdbcTemplate).isNotNull();
        assertThat(dbUrl).isEqualTo("jdbc:postgresql://raja.db.elephantsql.com/xbkikoqh");
        assertThat(dbUser).isEqualTo("xbkikoqh");
        assertThat(dbPassword).isEqualTo("4K1yrDXi19zjgSm0QPrrwwKfiXq9N7nK");

        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        assertThat(result).isEqualTo(1);
    }
}

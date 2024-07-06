package com.example.musicgame.test;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;

@SpringBootApplication
public class TestDatabaseConnectionApplication implements CommandLineRunner {

    public static void main(String[] args) {
        SpringApplication.run(TestDatabaseConnectionApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {
        String url = "jdbc:postgresql://raja.db.elephantsql.com/xbkikoqh";
        String user = "xbkikoqh";
        String password = "4K1yrDXi19zjgSm0QPrrwwKfiXq9N7nK";

        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            if (connection != null) {
                System.out.println("Connected to the database!");
            } else {
                System.out.println("Failed to make connection!");
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}

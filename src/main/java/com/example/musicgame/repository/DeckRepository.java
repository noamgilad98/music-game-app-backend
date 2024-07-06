package com.example.musicgame.repository;

import com.example.musicgame.model.Deck;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeckRepository extends JpaRepository<Deck, Long> {
    // Additional query methods (if any) can be defined here
}

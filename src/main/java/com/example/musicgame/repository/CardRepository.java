package com.example.musicgame.repository;

import com.example.musicgame.model.Card;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {

    @Modifying
    @Transactional
    @Query(value = "ALTER SEQUENCE card_id_seq RESTART WITH 1", nativeQuery = true)
    void resetSequence();
}

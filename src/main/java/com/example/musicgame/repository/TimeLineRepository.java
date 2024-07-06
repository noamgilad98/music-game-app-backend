package com.example.musicgame.repository;

import com.example.musicgame.model.TimeLine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TimeLineRepository extends JpaRepository<TimeLine, Long> {
    // Additional query methods (if any) can be defined here
}

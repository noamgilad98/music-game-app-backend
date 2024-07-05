package com.example.musicgame.repository;

import com.example.musicgame.model.Timeline;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TimelineRepository extends JpaRepository<Timeline, Long> {
}

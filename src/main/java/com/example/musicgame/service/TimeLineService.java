package com.example.musicgame.service;

import com.example.musicgame.model.TimeLine;
import com.example.musicgame.repository.TimeLineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TimeLineService {

    @Autowired
    private TimeLineRepository timeLineRepository;

    public TimeLine createTimeLine(TimeLine timeLine) {
        return timeLineRepository.save(timeLine);
    }

    public TimeLine getTimeLineById(Long id) {
        return timeLineRepository.findById(id).orElseThrow(() -> new RuntimeException("Timeline not found"));
    }

    // Other service methods as needed
}

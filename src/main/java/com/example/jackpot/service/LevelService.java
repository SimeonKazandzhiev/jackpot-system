package com.example.jackpot.service;

import com.example.jackpot.model.Level;

import java.util.Collection;
import java.util.List;

public interface LevelService {
    Level getById(Long id);

    Collection<Level> getAllLevels();

    Level createLevel(Level level);

    boolean update(Level level);

    Level getRandomWinningLevel(List<Level> levels);

    void updateLevelsBatch(List<Level> levels);
}

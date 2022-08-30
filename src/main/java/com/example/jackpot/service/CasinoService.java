package com.example.jackpot.service;

import com.example.jackpot.model.Casino;

import java.util.List;
import java.util.Optional;

public interface CasinoService {
    Optional<Casino> findById(Long id);

    Optional<Casino> findByName(String name);

    Casino create(Casino casino);

    List<Casino> getAllCasinos();
}

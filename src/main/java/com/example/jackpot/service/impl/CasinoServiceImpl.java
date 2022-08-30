package com.example.jackpot.service.impl;

import com.example.jackpot.exception.CasinoCreationException;
import com.example.jackpot.exception.ExistingCasinoException;
import com.example.jackpot.model.Casino;
import com.example.jackpot.repository.CasinoRepository;
import com.example.jackpot.service.CasinoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import static com.example.jackpot.constants.CasinoMessages.EXISTING_CASINO_MESSAGE;
import static com.example.jackpot.constants.LogErrorMessages.LOG_ERROR_MESSAGE;


@Service
@Slf4j
public class CasinoServiceImpl implements CasinoService {

    private final CasinoRepository casinoRepository;

    public CasinoServiceImpl(final CasinoRepository casinoRepository) {
        this.casinoRepository = casinoRepository;
    }

    @Override
    public Optional<Casino> findById(final Long id) {
        return this.casinoRepository.findById(id);
    }

    @Override
    public Optional<Casino> findByName(final String name) {
        return this.casinoRepository.findByName(name);
    }

    @Override
    public Casino create(final Casino casino) {
        Optional<Casino> casinoByName = findByName(casino.getName());

        if (casinoByName.isPresent()) {
            log.error(LOG_ERROR_MESSAGE);
            throw new ExistingCasinoException(EXISTING_CASINO_MESSAGE);
        }

        long casinoId = this.casinoRepository.create(casino);
        if (casinoId > 0) {
            casino.setId(casinoId);
            return casino;
        }
        throw new CasinoCreationException(EXISTING_CASINO_MESSAGE);
    }

    @Override
    public List<Casino> getAllCasinos() {
        return casinoRepository.getAllCasinos();
    }
}

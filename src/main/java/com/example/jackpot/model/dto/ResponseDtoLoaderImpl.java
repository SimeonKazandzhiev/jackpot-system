package com.example.jackpot.model.dto;

import com.example.jackpot.model.Casino;
import com.example.jackpot.model.Jackpot;
import com.example.jackpot.model.Level;
import com.example.jackpot.model.Player;
import com.example.jackpot.service.CasinoService;
import com.example.jackpot.service.JackpotService;
import com.example.jackpot.service.PlayerService;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class ResponseDtoLoaderImpl implements ResponseDtoLoader {
    private final JackpotService jackpotService;
    private final PlayerService playerService;
    private final CasinoService casinoService;

    public ResponseDtoLoaderImpl(final JackpotService jackpotService, final PlayerService playerService, final CasinoService casinoService) {
        this.jackpotService = jackpotService;
        this.playerService = playerService;
        this.casinoService = casinoService;

    }

    @Override
    public ResponseDto loadResponseDto(BetDto bet) {
        Long playerId = bet.getPlayerId();
        Optional<Casino> casino = casinoService.findById(bet.getCasinoId());
        String casinoName = casino.get().getName();
        Optional<Player> player = playerService.getById(bet.getPlayerId());
        BigDecimal playerBalance = player.get().getBalance();

        Jackpot jackpot = jackpotService.findByGivenCasinoId(casino.get().getId());
        var levels = jackpotService.getAllLevels(jackpot.getId());
        List<BigDecimal> jackpotAmounts = new ArrayList<>();

        for (int i = 0; i < levels.size(); i++) {
            Level level = levels.get(i);
            jackpotAmounts.add(level.getTotalAmountCollected());
        }
        return new ResponseDto(casinoName, jackpotAmounts, playerId, playerBalance);
    }
}
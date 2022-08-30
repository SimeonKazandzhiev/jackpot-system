package com.example.jackpot.handler.impl;


import com.example.jackpot.handler.ResponseHandler;
import com.example.jackpot.model.Jackpot;
import com.example.jackpot.model.Level;
import com.example.jackpot.model.dto.BetDto;
import com.example.jackpot.model.dto.ResponseDto;
import com.example.jackpot.model.dto.ResponseDtoLoader;
import com.example.jackpot.service.JackpotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.example.jackpot.config.SchedulerTimingRates.UPDATE_JACKPOTS_TO_FRONT_END_RATE;
import static com.example.jackpot.constants.WebSocketUrls.NOTIFY_USER_URL;
import static com.example.jackpot.constants.WebSocketUrls.SHOW_ALL_LEVELS_URL;

@Service
@EnableScheduling
public class ResponseHandlerImpl implements ResponseHandler {
    private final SimpMessagingTemplate messagingTemplate;
    private final ResponseDtoLoader responseDtoLoader;
    private final JackpotService jackpotService;

    @Autowired
    public ResponseHandlerImpl(final SimpMessagingTemplate messagingTemplate, final ResponseDtoLoader responseDtoLoader,
                               final JackpotService jackpotService) {
        this.messagingTemplate = messagingTemplate;
        this.responseDtoLoader = responseDtoLoader;
        this.jackpotService = jackpotService;
    }

    @Override
    public void notifyUser(final String id, BetDto bet) {
        ResponseDto infoDto = responseDtoLoader.loadResponseDto(bet);
        messagingTemplate.convertAndSendToUser(id, NOTIFY_USER_URL, infoDto);
    }


    @Scheduled(fixedRateString = UPDATE_JACKPOTS_TO_FRONT_END_RATE)
    private void showAllJackpotLevels() {
        StringBuilder sb = getLevels();
        messagingTemplate.convertAndSend(SHOW_ALL_LEVELS_URL, sb);
    }

    private StringBuilder getLevels() {
        var sb = new StringBuilder();
        var counter = 0;

        final List<Jackpot> allJackpots = jackpotService.getAllJackpots();

        for (Jackpot jackpot : allJackpots) {
            final List<Level> levels1 = jackpot.getLevels();
            sb.append("Casino id: ").append(jackpot.getId()).append(" --> ");
            for (Level level : levels1) {
                sb.append("Level ")
                            .append(++counter)
                            .append(": ")
                            .append(level.getTotalAmountCollected().intValue())
                            .append("  ");
            }
            counter = 0;
            sb.append(System.lineSeparator());
            sb.append(System.lineSeparator());
        }
        return sb;
    }
}

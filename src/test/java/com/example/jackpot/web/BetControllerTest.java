package com.example.jackpot.web;

import com.example.jackpot.model.dto.BetDto;
import com.example.jackpot.util.BigDecimalRandomGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class BetControllerTest {

    private static final Long PLAYER_ID = 1L;
    private static final Long CASINO_ID = 1L;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("makeBet() should return correct responseDto.")
    void makeBet() throws Exception {

        BetDto bet = new BetDto(BigDecimalRandomGenerator.generateRandom(), CASINO_ID, PLAYER_ID);
        String userId = UUID.randomUUID().toString();

        var response = mockMvc.perform(
                post("/bet/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(bet))
                        .accept(MediaType.APPLICATION_JSON_VALUE));

        response.andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print());
    }

    @Test
    @DisplayName("makeBet() should return bad request status with not valid bet amount.")
    void makeBetBadRequestInvalidAmountGiven() throws Exception {

        final BigDecimal invalidAmount = BigDecimal.valueOf(-20);

        BetDto bet = new BetDto(invalidAmount, CASINO_ID, PLAYER_ID);
        String userId = UUID.randomUUID().toString();

        var response = mockMvc.perform(
                post("/bet/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(bet))
                        .accept(MediaType.APPLICATION_JSON_VALUE));

        response.andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print());
    }

    @Test
    @DisplayName("makeBet() should return not found status with non existing casino.")
    void makeBetBadRequestNonExistingCasino() throws Exception {
        Long casinoId = 7L;

        BetDto bet = new BetDto(BigDecimalRandomGenerator.generateRandom(), casinoId, PLAYER_ID);
        String userId = UUID.randomUUID().toString();

        var response = mockMvc.perform(
                post("/bet/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(bet))
                        .accept(MediaType.APPLICATION_JSON_VALUE));

        response.andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print());
    }

    @Test
    @DisplayName("makeBet() should return not found status with non existing player.")
    void makeBetBadRequestNonExistingPlayer() throws Exception {

        Long playerId = 5L;

        BetDto bet = new BetDto(BigDecimalRandomGenerator.generateRandom(), CASINO_ID, playerId);
        String userId = UUID.randomUUID().toString();

        var response = mockMvc.perform(
                post("/bet/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(bet))
                        .accept(MediaType.APPLICATION_JSON_VALUE));

        response.andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print());
    }

    @Test
    @DisplayName("makeBet() should return badRequest status with insufficient balance for a bet.")
    void makeBetBadRequestInsufficientBalance() throws Exception {

        BigDecimal invalidAmount = BigDecimal.valueOf(100_000_000);

        BetDto bet = new BetDto(invalidAmount, CASINO_ID, PLAYER_ID);
        String userId = UUID.randomUUID().toString();

        var response = mockMvc.perform(
                post("/bet/{id}", userId)
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(bet))
                        .accept(MediaType.APPLICATION_JSON_VALUE));

        response.andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print());
    }

}
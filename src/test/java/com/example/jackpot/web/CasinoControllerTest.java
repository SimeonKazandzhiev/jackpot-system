package com.example.jackpot.web;

import com.example.jackpot.model.Casino;
import com.example.jackpot.model.Jackpot;
import com.example.jackpot.model.Level;
import com.example.jackpot.util.BigDecimalRandomGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.MOCK)
@AutoConfigureMockMvc
class CasinoControllerTest {

    private static final Long CASINO_ID = 1L;
    private static final String CASINO_NAME = "Efbet";
    private static final Long JACKPOT_ID = 1L;

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @DisplayName("Casino creation should pass successfully.")
    void createCasino() throws Exception {
        Casino casino = createMockCasino();

        var response = mockMvc.perform(
                post("/casino/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(casino))
                        .accept(MediaType.APPLICATION_JSON));
        response.andExpect(status().isOk());
    }

    @Test
    @DisplayName("Casino which is existing should throw exception when try to create.")
    void createCasinoThrows() throws Exception {
        Casino casino = createMockCasino();
        casino.setName(CASINO_NAME);
        Jackpot jackpot = casino.getJackpot();
        jackpot.setId(casino.getId());
        jackpot.getLevels().get(0).setJackpotId(jackpot.getId());

        var response = mockMvc.perform(
                        post("/casino/")
                                .contentType(MediaType.APPLICATION_JSON_VALUE)
                                .content(objectMapper.writeValueAsString(casino))
                                .accept(MediaType.APPLICATION_JSON_VALUE));


        response.andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print());
    }

    @Test
    @DisplayName("Casino updating should pass successfully.")
    void updateCasino() throws Exception {
        final Integer numOfLevels = 4;
        Casino casino = new Casino(CASINO_ID,CASINO_NAME, new Jackpot(JACKPOT_ID, CASINO_ID, new Random().nextInt(), numOfLevels,
                List.of(new Level(1L, BigDecimalRandomGenerator.generateRandom(), BigDecimalRandomGenerator.generateRandom(),
                                BigDecimalRandomGenerator.generateRandom(),BigDecimalRandomGenerator.generateRandom(),
                                BigDecimalRandomGenerator.generateRandom(), JACKPOT_ID),
                        new Level(2L, BigDecimalRandomGenerator.generateRandom(), BigDecimalRandomGenerator.generateRandom(),
                                BigDecimalRandomGenerator.generateRandom(), BigDecimalRandomGenerator.generateRandom(),
                                BigDecimalRandomGenerator.generateRandom(), JACKPOT_ID),
                        new Level(3L, BigDecimalRandomGenerator.generateRandom(), BigDecimalRandomGenerator.generateRandom(),
                                BigDecimalRandomGenerator.generateRandom(), BigDecimalRandomGenerator.generateRandom(),
                                BigDecimalRandomGenerator.generateRandom(), JACKPOT_ID),
                        new Level(4L, BigDecimalRandomGenerator.generateRandom(), BigDecimalRandomGenerator.generateRandom(),
                                BigDecimalRandomGenerator.generateRandom(), BigDecimalRandomGenerator.generateRandom(),
                                BigDecimalRandomGenerator.generateRandom(), JACKPOT_ID))));

        var response = mockMvc.perform(
                put("/casino/update/")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(casino))
                        .accept(MediaType.APPLICATION_JSON));
        response
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Casino by given id should throw exception")
    void updateCasinoThrows() throws Exception {
        long targetId = new Random().nextLong();
        mockMvc.perform(
                        put("/casino/{id}", targetId)
                                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Jackpot with not matching levels count should throw exception")
    void createCasinoJackpotThrows() throws Exception {

        Casino casino = createMockCasino();
        casino.getJackpot().setNumberOfLevels(new Random().nextInt()/2);

        var response = mockMvc.perform(
                post("/casino")
                        .contentType(MediaType.APPLICATION_JSON_VALUE)
                        .content(objectMapper.writeValueAsString(casino))
                        .accept(MediaType.APPLICATION_JSON_VALUE));

        response.andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE))
                .andDo(print());
    }

    private Casino createMockCasino() {
        final Integer numOfLevels = 1;
        Long randomCasinoId = new Random().nextLong(4,Integer.MAX_VALUE/2);
        return new Casino(randomCasinoId, UUID.randomUUID().toString(),
                new Jackpot(randomCasinoId, randomCasinoId, new Random().nextInt(), numOfLevels,
                        List.of(new Level(new Random().nextLong(4,Integer.MAX_VALUE/2), BigDecimalRandomGenerator.generateRandom(),
                                BigDecimalRandomGenerator.generateRandom(), BigDecimalRandomGenerator.generateRandom(),
                                BigDecimalRandomGenerator.generateRandom(), BigDecimalRandomGenerator.generateRandom(),
                                randomCasinoId))));
    }

}
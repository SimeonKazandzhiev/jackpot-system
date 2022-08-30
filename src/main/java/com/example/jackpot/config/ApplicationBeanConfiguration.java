package com.example.jackpot.config;

import com.example.jackpot.manager.JackpotInstanceManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class ApplicationBeanConfiguration {
    @Bean
    public Map<Long, JackpotInstanceManager> casinoIdsToJackpots(){
        return new ConcurrentHashMap<>();
    }
}

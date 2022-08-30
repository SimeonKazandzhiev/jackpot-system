package com.example.jackpot.config;

import com.example.jackpot.exception.UnableReadRandomPropertiesException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class RandomConfig {
    public static Integer getProperties() {
        try (InputStream input = LockConfig.class.getClassLoader().getResourceAsStream("random.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                log.info("Sorry, unable to find random.properties");
                return 100;
            }
            prop.load(input);
            String timeunit = prop.getProperty("rate");
            return Integer.parseInt(timeunit);
        } catch (IOException ex) {
            throw new UnableReadRandomPropertiesException();
        }
    }
}

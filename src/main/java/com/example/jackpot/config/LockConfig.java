package com.example.jackpot.config;

import com.example.jackpot.exception.UnableReadLockPropertiesException;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

@Slf4j
public class LockConfig {
    public static Integer getProperties() {
        try (InputStream input = LockConfig.class.getClassLoader().getResourceAsStream("lock.properties")) {
            Properties prop = new Properties();
            if (input == null) {
                log.info("Sorry, unable to find lock.properties");
                return 300;
            }
            prop.load(input);
            String timeunit = prop.getProperty("timeunit");
            return Integer.parseInt(timeunit);
        } catch (IOException ex) {
            throw new UnableReadLockPropertiesException();
        }
    }
}

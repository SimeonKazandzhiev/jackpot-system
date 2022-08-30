package com.example.jackpot.constants;

public class JackpotMessages {
    public static final String NON_EXISTING_JACKPOT_BY_ID = "Jackpot with given ID does not exist.";
    public static final String NON_EXISTING_JACKPOT_BY_CASINO_ID = "Jackpot with given casino ID does not exist.";
    public static final String JACKPOT_CANNOT_BE_UPDATED = "The jackpot cannot be updated!";
    public static final String FORBIDDEN_CHANGE_OF_LEVELS_COUNT = "You dont have permit to change the number of levels.";
    public static final String UPDATING_JACKPOT_LEVELS_JSON = "Jackpot ID in URL = '%d' does not match ID in message body = '%d'";
    public static final String INVALID_JACKPOT_ID = "Jackpot ID must be positive";
    public static final String INVALID_COUNT_OF_LEVELS = "The number of levels for a jackpot must match the size of the levels!";
    public static final String FORBIDDEN_CASINO_CHANGE = "You dont have permit to change the casino!";

    private JackpotMessages(){}
}

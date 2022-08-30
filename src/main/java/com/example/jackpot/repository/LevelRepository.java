package com.example.jackpot.repository;

import com.example.jackpot.model.Level;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlBatch;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;
import org.jdbi.v3.sqlobject.transaction.Transaction;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface LevelRepository {
    String LEVEL_TABLE = "levels";
    // GET LEVEL BY GIVEN ID
    @SqlQuery("SELECT * FROM `" + LEVEL_TABLE + "` WHERE `id` = :id; ")
    @RegisterBeanMapper(Level.class)
    Optional<Level> findById(@Bind("id") Long id);

    // GET ALL LEVELS
    @SqlQuery("SELECT * FROM `" + LEVEL_TABLE + "`; ")
    @RegisterBeanMapper(Level.class)
    Collection<Level> findAll();

    // CREATE NEW LEVEL
    @SqlUpdate("INSERT INTO `" + LEVEL_TABLE + "` " +
            " (`id`, `total_amount_collected` ,`percent_of_bet`, `starting_amount`, `minimum_amount_to_be_won`, `win_probability`, `jackpot_id`) " +
            " VALUES (:id, :total_amount_collected, :percent_of_bet, :starting_amount, :minimum_amount_to_be_won, :win_probability, :jackpot_id) ")
    @GetGeneratedKeys({"id"})
    long create(@Bind("id") Long id,
                @Bind("total_amount_collected") BigDecimal totalAmountCollected,
                @Bind("percent_of_bet") BigDecimal percentOfBet,
                @Bind("starting_amount") BigDecimal startingAmount,
                @Bind("minimum_amount_to_be_won") BigDecimal minAmountToBeWon,
                @Bind("win_probability") BigDecimal winProbability,
                @Bind("jackpot_id") Long jackpotId);

    // UPDATING LEVEL
    @SqlUpdate("UPDATE `" + LEVEL_TABLE + "` " +
            " SET `total_amount_collected` = :total_amount_collected , `percent_of_bet` = :percent_of_bet,`starting_amount` = :starting_amount, `minimum_amount_to_be_won` = :minimum_amount_to_be_won, `win_probability` = :win_probability " +
            " WHERE id = :id;")
    boolean update(@Bind("id") Long id,
                   @Bind("total_amount_collected") BigDecimal totalAmountCollected,
                   @Bind("percent_of_bet") BigDecimal percentOfBet,
                   @Bind("starting_amount") BigDecimal startingAmount,
                   @Bind("minimum_amount_to_be_won") BigDecimal minAmountToBeWon,
                   @Bind("win_probability") BigDecimal winProbability,
                   @Bind("jackpot_id") Long jackpotID);

    // UPDATING LEVELS WITH BATCH
    @Transaction
    @SqlBatch("UPDATE `" + LEVEL_TABLE +
            "` SET `total_amount_collected` = :total_amount_collected, `percent_of_bet` = :percent_of_bet, `starting_amount` = :starting_amount, `minimum_amount_to_be_won` = :minimum_amount_to_be_won, `win_probability` = :win_probability, " +
            "`jackpot_id` = :jackpot_id WHERE id = :id;  ")
    void updateLevelsBatch(@Bind("total_amount_collected") List<BigDecimal> totalAmountsCollected,
                      @Bind("percent_of_bet") List<BigDecimal> percentagesOfBet,
                      @Bind("starting_amount") List<BigDecimal> startAmounts,
                      @Bind("minimum_amount_to_be_won") List<BigDecimal> minAmountsToWin,
                      @Bind("win_probability") List<BigDecimal> winProbabilities,
                      @Bind("jackpot_id") List<Long> jackpotIds,
                      @Bind("id") List<Long> ids);
}

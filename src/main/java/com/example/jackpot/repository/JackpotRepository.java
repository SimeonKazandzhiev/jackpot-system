package com.example.jackpot.repository;

import com.example.jackpot.model.Jackpot;
import com.example.jackpot.model.Level;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

public interface JackpotRepository {
    String JACKPOT_TABLE = "jackpots";

    // GET JACKPOT BY ID
    @SqlQuery("SELECT * FROM `" + JACKPOT_TABLE + "` WHERE `id` = :id; ")
    @RegisterBeanMapper(Jackpot.class)
    Optional<Jackpot> findById(@Bind("id") Long id);

    //GET JACKPOT BY GIVEN CASINO ID
    @SqlQuery("SELECT * FROM `" + JACKPOT_TABLE + "` WHERE `casino_id` = :casino_id; ")
    @RegisterBeanMapper(Jackpot.class)
    Optional<Jackpot> findJackpotByCasinoId(@Bind("casino_id") Long id);

    @SqlUpdate("INSERT INTO `" + JACKPOT_TABLE + "` " +
            " (`casino_id`, `trigger_percent`, `number_of_levels`) " +
            " VALUES (:casino_id, :trigger_percent, :number_of_levels) ")
    @GetGeneratedKeys({"id", "casino_id"})
    long create(@Bind("casino_id") Long casinoId,
                @Bind("trigger_percent") Integer triggerPercent,
                @Bind("number_of_levels") Integer numberOfLevels);

    @SqlUpdate("UPDATE `" + JACKPOT_TABLE + "` " +
            " SET `casino_id` = :casino_id, `trigger_percent` = :trigger_percent, `number_of_levels` = :number_of_levels " +
            " WHERE id = :id; ")
    boolean update(@Bind("casino_id") Long casinoId,
                   @Bind("trigger_percent") Integer triggerPercent,
                   @Bind("number_of_levels") Integer numberOfLevels,
                   @Bind("id") Long id);

    @SqlQuery("SELECT * FROM levels where jackpot_id = :jackpot_id;")
    @RegisterBeanMapper(Level.class)
    List<Level> getAllLevelsForJackpot(@Bind("jackpot_id") Long jackpotId);

    @SqlQuery("SELECT * FROM `" + JACKPOT_TABLE + "`; ")
    @RegisterBeanMapper(Jackpot.class)
    List<Jackpot> getAllJackpots();
}

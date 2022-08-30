package com.example.jackpot.repository;
import com.example.jackpot.model.Player;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.Optional;

public interface PlayerRepository {
    String PLAYERS_TABLE = "players";
    // GET PLAYER BY ID
    @SqlQuery("SELECT * FROM `" + PLAYERS_TABLE + "` WHERE `id` = :id; ")
    @RegisterBeanMapper(Player.class)
    Optional<Player> findById(@Bind("id") Long id);

    //GET PLAYER BY NAME
    @SqlQuery("SELECT * FROM `" + PLAYERS_TABLE + "` WHERE `name` = :name; ")
    @RegisterBeanMapper(Player.class)
    Optional<Player> findByName(@Bind("name") String name);

    //UPDATE PLAYER
    @SqlUpdate("UPDATE `" + PLAYERS_TABLE +
            "` SET `balance` = :balance, `name` = :name WHERE id = :id; ")
    boolean update(@BindBean Player player);

    @SqlUpdate("INSERT INTO `" + PLAYERS_TABLE + "` " +
           " (`id`, `balance`, `name`) " +
            " VALUES (:id, :balance, :name)"  )
    @GetGeneratedKeys("id")
    @RegisterBeanMapper(Player.class)
    Player create(@BindBean Player player);

}

package com.example.jackpot.repository;

import com.example.jackpot.model.Casino;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.GetGeneratedKeys;
import org.jdbi.v3.sqlobject.statement.SqlQuery;
import org.jdbi.v3.sqlobject.statement.SqlUpdate;

import java.util.List;
import java.util.Optional;

public interface CasinoRepository {
    String CASINO_TABLE = "casinos";
    // GET CASINO BY ID
    @SqlQuery("SELECT * FROM `" + CASINO_TABLE + "` WHERE `id` = :id; ")
    @RegisterBeanMapper(Casino.class)
    Optional<Casino> findById(@Bind("id") Long id);

    // GET CASINO BY NAME
    @SqlQuery("SELECT * FROM `" + CASINO_TABLE + "` WHERE `name` = :name; ")
    @RegisterBeanMapper(Casino.class)
    Optional<Casino> findByName(@Bind("name") String name);

    // CREATE NEW CASINO
    @SqlUpdate("INSERT INTO `" + CASINO_TABLE + "` " +
            " (`id`, `name`) " +
            " VALUES (:id, :name) ")
    @GetGeneratedKeys("id")
    long create(@BindBean Casino casino);

    @SqlQuery("SELECT * FROM  casinos ORDER BY id;")
    @RegisterBeanMapper(Casino.class)
    List<Casino> getAllCasinos();
}

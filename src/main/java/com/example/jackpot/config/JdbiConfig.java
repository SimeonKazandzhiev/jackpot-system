package com.example.jackpot.config;

import com.example.jackpot.repository.CasinoRepository;
import com.example.jackpot.repository.JackpotRepository;
import com.example.jackpot.repository.LevelRepository;
import com.example.jackpot.repository.PlayerRepository;
import com.zaxxer.hikari.HikariDataSource;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.RowMapper;
import org.jdbi.v3.core.spi.JdbiPlugin;
import org.jdbi.v3.sqlobject.SqlObjectPlugin;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

import javax.sql.DataSource;
import java.util.List;

@Configuration
@PropertySource("classpath:jdbi.properties")
public class JdbiConfig {

    @Value("${driver}")
    private String driverClassname;

    @Value("${url}")
    private String url;

    @Value("${user}")
    private String username;

    @Value("${password}")
    private String password;

    @Bean
    public JdbiPlugin sqlObjectPlugin() {
        return new SqlObjectPlugin();
    }

    @Bean
    public LevelRepository levelRepository(Jdbi jdbi) {
        return jdbi.onDemand(LevelRepository.class);
    }

    @Bean
    public PlayerRepository playerRepository(Jdbi jdbi) {
        return jdbi.onDemand(PlayerRepository.class);
    }

    @Bean
    public CasinoRepository casinoRepository(Jdbi jdbi) {
        return jdbi.onDemand(CasinoRepository.class);
    }

    @Bean
    public JackpotRepository jackpotRepository(Jdbi jdbi) {
        return jdbi.onDemand(JackpotRepository.class);
    }

    @Bean
    public Jdbi jdbi(DataSource ds, List<JdbiPlugin> jdbiPlugins, List<RowMapper<?>> rowMappers) {
        TransactionAwareDataSourceProxy proxy = new TransactionAwareDataSourceProxy(ds);
        Jdbi jdbi = Jdbi.create(proxy);
        jdbiPlugins.forEach(jdbi::installPlugin);
        rowMappers.forEach(jdbi::registerRowMapper);
        return jdbi;
    }

    @Bean
    DataSource getDataSource() {
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setDriverClassName(driverClassname);
        dataSource.setJdbcUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        return dataSource;
    }

}
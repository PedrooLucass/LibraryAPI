package io.github.LibraryAPI.config;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;

@Configuration
public class DatabaseConfiguration {

    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String username;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    /* Essa opção é válida, porem se tornou legado
    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setDriverClassName(driver);

        return dataSource;
    }
    */

    @Bean
    public DataSource hikariDataSource() {
        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(url);
        config.setUsername(username);
        config.setPassword(password);
        config.setDriverClassName(driver);

        config.setMaximumPoolSize(10); // Máximo de conexões liberadas
        config.setMinimumIdle(1); // Tamanho inicial da pool

        config.setPoolName("library-db-pool");
        config.setMaximumPoolSize(600000); // 600 mil ms = 10 min, é o tempo máximo de conexão antes de derrubar automaticamente

        config.setConnectionTimeout(100000); // Tempo máximo para conseguir conectar, caso contrário dará timeout

        config.setConnectionTestQuery("select 1");

        return new HikariDataSource(config);
    }
}

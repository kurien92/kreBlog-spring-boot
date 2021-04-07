package net.kurien.blog.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EncryptablePropertySource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Slf4j
@Configuration
@EncryptablePropertySource("classpath:application.yml")
public class DataSourceConfig {
    @Bean
    @ConfigurationProperties(prefix = "db")
    public HikariConfig hikariConfig() {
        return new HikariConfig();
    }

    @Bean
    @Primary
    public DataSource dataSource() {
        DataSource dataSource = new HikariDataSource(hikariConfig());
        log.info("dataSource : {}", dataSource);

        return dataSource;
    }

}

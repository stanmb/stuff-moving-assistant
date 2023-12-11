package ru.svrd.stuff_moving_assistant.infrastructure

import com.zaxxer.hikari.HikariConfig
import com.zaxxer.hikari.HikariDataSource
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

@Configuration
class DbConfiguration {

    @Bean
    @ConfigurationProperties("spring.datasource.master.hikari")
    fun masterHikariConfig() = HikariConfig()

    @Bean
    fun masterDataSource(masterHikariConfig: HikariConfig) = HikariDataSource(masterHikariConfig)

    @Bean
    fun masterNamedJdbcTemplate(masterDataSource: DataSource) = NamedParameterJdbcTemplate(masterDataSource)
}

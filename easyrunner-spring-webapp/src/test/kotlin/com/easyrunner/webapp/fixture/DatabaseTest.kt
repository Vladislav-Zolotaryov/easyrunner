package com.easyrunner.webapp.fixture

import com.easyrunner.webapp.spring.SpringUsed
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.DynamicPropertyRegistry
import org.springframework.test.context.DynamicPropertySource
import org.springframework.test.web.reactive.server.WebTestClient
import org.testcontainers.containers.PostgreSQLContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers


@Testcontainers
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
abstract class DatabaseTest {

    @Autowired
    lateinit var webTestClient: WebTestClient

    companion object {
        @Container
        private val postgresContainer: PostgreSQLContainer<*> =
            PostgreSQLContainer("postgres:14.5-alpine")
                .withDatabaseName("easyrunner")
                .withUsername("easy")
                .withPassword("runner")

        @DynamicPropertySource
        @JvmStatic
        @SpringUsed
        fun registerDynamicProperties(registry: DynamicPropertyRegistry) {
            registry.add("spring.r2dbc.url") { postgresContainer.jdbcUrl.replace("jdbc", "r2dbc") }
            registry.add("spring.r2dbc.username", postgresContainer::getUsername)
            registry.add("spring.r2dbc.password", postgresContainer::getPassword)

            registry.add("spring.liquibase.url", postgresContainer::getJdbcUrl)
            registry.add("spring.liquibase.user", postgresContainer::getUsername)
            registry.add("spring.liquibase.password", postgresContainer::getPassword)
        }
    }

}
package com.easyrunner.webapp

import com.easyrunner.webapp.fixture.DatabaseTest
import com.easyrunner.webapp.users.UserLoginCommand
import com.easyrunner.webapp.users.UserRegisterCommand
import com.easyrunner.webapp.users.Username
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.test.web.reactive.server.WebTestClient


@AutoConfigureWebTestClient
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserTests : DatabaseTest() {

    @Autowired
    private lateinit var webTestClient: WebTestClient

    companion object {
        val testUsername = Username("TestUser")
        const val testPassword = "TestPassword"
    }

    @Test
    @Order(1)
    fun canRegister() {
        webTestClient
            .post()
            .uri("/user")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(UserRegisterCommand(testUsername, testPassword))
            .exchange()
            .expectStatus().isOk
            .expectBody()
            .jsonPath("$.userId").isNotEmpty
            .jsonPath("$.username").isEqualTo(testUsername.value)
    }

    @Test
    @Order(2)
    fun canLogin() {
        webTestClient
            .post()
            .uri("/user/login")
            .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
            .bodyValue(UserLoginCommand(testUsername, testPassword))
            .exchange()
            .expectStatus().isOk
            .expectBody().isEmpty
    }

}

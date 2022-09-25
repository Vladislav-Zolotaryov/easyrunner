package com.easyrunner.webapp

import com.easyrunner.webapp.fixture.DatabaseTest
import com.easyrunner.webapp.users.UserLoginCommand
import com.easyrunner.webapp.users.UserRegisterCommand
import org.junit.jupiter.api.Order
import org.junit.jupiter.api.Test
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType


class UserTests : DatabaseTest() {

    companion object {
        const val testUsername = "TestUser"
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
            .jsonPath("$.username").isEqualTo(testUsername)
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

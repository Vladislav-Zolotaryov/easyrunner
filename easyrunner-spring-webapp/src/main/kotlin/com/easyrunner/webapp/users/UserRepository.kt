package com.easyrunner.webapp.users

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono

interface UserRepository : ReactiveCrudRepository<UserDb, String> {
    fun findByUsername(username: String): Mono<UserDb>
    fun existsByUsername(username: String): Mono<Boolean>
}

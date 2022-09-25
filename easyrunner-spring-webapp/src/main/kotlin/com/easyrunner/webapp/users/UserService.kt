package com.easyrunner.webapp.users

import com.easyrunner.webapp.mono.MonoExt
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.transaction.reactive.TransactionalOperator
import reactor.core.publisher.Mono
import java.util.*

class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val transactionalOperator: TransactionalOperator,
) {

    fun register(username: Username, password: String): Mono<UserView> =
        transactionalOperator.transactional(
            userRepository
                .existsByUsername(username.value)
                .flatMap { exists ->
                    if (exists) {
                        Mono.error(UsernameAlreadyTakenException())
                    } else {
                        userRepository.save(
                            UserDb(
                                userId = UUID.randomUUID().toString(),
                                username = username.value,
                                passwordHash = passwordEncoder.encode(password)
                            )
                        ).map { it.view() }
                    }
                })

    fun login(username: Username, password: String): Mono<Void> =
        transactionalOperator.transactional(
            userRepository.findByUsername(username.value)
                .switchIfEmpty(Mono.error(IncorrectCredentials()))
                .flatMap {
                    if (passwordEncoder.matches(password, it.passwordHash)) {
                        MonoExt.success()
                    } else {
                        Mono.error(IncorrectCredentials())
                    }
                }
        )

}
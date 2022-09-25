package com.easyrunner.webapp.users

import com.easyrunner.webapp.spring.SpringUsed
import org.springframework.context.annotation.Bean
import org.springframework.http.MediaType
import org.springframework.web.reactive.function.server.body
import org.springframework.web.reactive.function.server.router


class UserController(private val userService: UserService) {

    @Bean
    @SpringUsed
    fun router() = router {
        "/user".nest {
            accept(MediaType.APPLICATION_JSON).nest {
                "/login".nest {
                    POST { request ->
                        request.bodyToMono(UserLoginCommand::class.java)
                            .map {
                                userService.login(it.username, it.password)
                            }
                            .flatMap {
                                ok().body(it)
                            }
                    }
                }
                POST { request ->
                    request.bodyToMono(UserRegisterCommand::class.java)
                        .map {
                            userService.register(it.username, it.password)
                        }
                        .flatMap {
                            ok().body(it)
                        }
                }
            }
        }
    }

}
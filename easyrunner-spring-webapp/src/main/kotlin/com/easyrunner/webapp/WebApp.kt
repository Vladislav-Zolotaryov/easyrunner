package com.easyrunner.webapp

import com.easyrunner.webapp.spring.SpringUsed
import com.easyrunner.webapp.users.UserBeans
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.ApplicationContextInitializer
import org.springframework.context.support.GenericApplicationContext
import org.springframework.context.support.beans
import org.springframework.security.crypto.factory.PasswordEncoderFactories


@SpringBootApplication
class WebApp

fun beans() = beans {
    bean {
        PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }
    UserBeans.beans(this)
}

@SpringUsed
class BeansInitializer : ApplicationContextInitializer<GenericApplicationContext> {
    override fun initialize(context: GenericApplicationContext) = beans().initialize(context)
}

fun main(args: Array<String>) {
    runApplication<WebApp>(*args)
}

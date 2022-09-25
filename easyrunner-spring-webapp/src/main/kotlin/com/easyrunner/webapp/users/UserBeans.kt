package com.easyrunner.webapp.users

import org.springframework.context.support.BeanDefinitionDsl


object UserBeans {

    fun beans(dsl: BeanDefinitionDsl) =
        with(dsl) {
            bean<UserService>()
            bean<UserController>()
        }

}
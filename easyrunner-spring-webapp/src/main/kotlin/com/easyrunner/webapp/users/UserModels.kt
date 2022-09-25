package com.easyrunner.webapp.users

import org.springframework.data.relational.core.mapping.Table


data class UserView(val userId: String, val username: String)

@Table(name = "users")
data class UserDb(val userId: String, val username: String, val passwordHash: String)

data class UserRegisterCommand(val username: String, val password: String)

data class UserLoginCommand(val username: String, val password: String)

class UsernameAlreadyTakenException : IllegalArgumentException()

class IncorrectCredentials : IllegalArgumentException()
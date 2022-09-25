package com.easyrunner.webapp.users

import org.springframework.data.relational.core.mapping.Table

/*
@JvmInline
value class UserId(val value: String)
*/

@JvmInline
value class Username(val value: String)

data class UserView(val userId: String, val username: Username)

@Table(name = "users")
data class UserDb(val userId: String, val username: String, val passwordHash: String)

data class UserRegisterCommand(val username: Username, val password: String)

data class UserLoginCommand(val username: Username, val password: String)

class UsernameAlreadyTakenException : IllegalArgumentException()

class IncorrectCredentials : IllegalArgumentException()
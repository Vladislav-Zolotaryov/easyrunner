package com.easyrunner.webapp.users

fun UserDb.view(): UserView = UserView(this.userId, Username(this.username))
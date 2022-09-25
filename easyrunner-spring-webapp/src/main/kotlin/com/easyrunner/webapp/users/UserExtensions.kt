package com.easyrunner.webapp.users

fun UserDb.view(): UserView = UserView(this.userId, this.username)
package com.erikriosetiawan.jolalinotes.models

data class User(
    var _id: String,
    var name: String,
    var email: String = "",
    var dateRegistered: String = "",
    var lastLogin: String = "",
)

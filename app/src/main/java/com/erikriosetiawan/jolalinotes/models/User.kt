package com.erikriosetiawan.jolalinotes.models

data class User(
    var _id: String,
    var name: String,
    var email: String,
    var dateRegistered: String = "",
    var lastLogin: String = "",
) {
    override fun toString(): String {
        return """
            _id: ${this._id}
            name: ${this.name}
            email: ${this.email}
            dateRegister: ${this.dateRegistered}
            lastLogin: ${this.lastLogin}
        """.trimIndent()
    }
}

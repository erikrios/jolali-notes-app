package com.erikriosetiawan.jolalinotes.models

data class Note(
    var _id: String,
    var title: String,
    var description: String,
    var date: String,
    var time: String,
    var ownerId: NoteOwner
) {
    override fun toString(): String {
        return """
            _id: ${this._id}
            title: ${this.title}
            description: ${this.description}
            date: ${this.date}
            time: ${this.time}
            ownerId._id: ${this.ownerId._id}
            ownerId.name: ${this.ownerId.name}
        """.trimIndent()
    }
}
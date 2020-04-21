package net.nnaabbee.example.demo.responses

import net.nnaabbee.example.demo.entities.User

data class UserResponse(val id: Int, val name: String, val age: Int) {
    companion object {
        fun create(user: User): UserResponse {
            return UserResponse(user.id!!, user.name, user.age)
        }
    }
}
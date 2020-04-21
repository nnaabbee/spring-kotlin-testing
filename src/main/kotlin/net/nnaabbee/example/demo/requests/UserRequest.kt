package net.nnaabbee.example.demo.requests

import net.nnaabbee.example.demo.entities.User
import java.time.LocalDateTime
import javax.validation.constraints.Max
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull
import javax.validation.constraints.Size

data class UserPostRequest (
        @NotBlank
        @Size(max = 100)
        val name: String,
        @NotNull
        @Max(999)
        val age: Int) {
    fun toEntity(): User {
        return User(
                null,
                name,
                age,
                LocalDateTime.now(),
                LocalDateTime.now()
        )
    }
}
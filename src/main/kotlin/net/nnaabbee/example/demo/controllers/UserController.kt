package net.nnaabbee.example.demo.controllers

import net.nnaabbee.example.demo.exceptions.ValidationError
import net.nnaabbee.example.demo.responses.UserResponse
import net.nnaabbee.example.demo.repositories.IUserRepository
import net.nnaabbee.example.demo.requests.UserPostRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.validation.Errors
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.Valid

@RestController
@RequestMapping("users")
class UserController(@Autowired val userRepository: IUserRepository) {
    @GetMapping("/")
    fun readList(): List<UserResponse> {
        return userRepository.findAll().map { UserResponse.create(it) }
    }

    @GetMapping("/{id}")
    fun read(@PathVariable id: Int): UserResponse {
        return userRepository.findById(id).map { UserResponse.create(it) }.get()
    }

    @PostMapping("/")
    fun register(@RequestBody @Valid request: UserPostRequest, errors: Errors): UserResponse {
        if (errors.hasErrors()) {
            val errorMessage = errors.allErrors.map { it.defaultMessage }.joinToString(",")
            throw ValidationError(errorMessage)
        }
        return userRepository.save(request.toEntity())
                .run { UserResponse.create(this) }
    }
}
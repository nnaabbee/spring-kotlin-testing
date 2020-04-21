package net.nnaabbee.example.demo.repositories

import net.nnaabbee.example.demo.entities.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface IUserRepository : JpaRepository<User, Int> {
}
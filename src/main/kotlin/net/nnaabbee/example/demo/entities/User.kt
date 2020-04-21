package net.nnaabbee.example.demo.entities

import java.io.Serializable
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "users")
data class User (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int?,
        @Column(length = 100, nullable = false)
        val name: String,
        @Column(nullable = false)
        val age: Int,
        @Column(nullable = false)
        val createdAt: LocalDateTime,
        @Column(nullable = false)
        val updatedAt: LocalDateTime
) : Serializable
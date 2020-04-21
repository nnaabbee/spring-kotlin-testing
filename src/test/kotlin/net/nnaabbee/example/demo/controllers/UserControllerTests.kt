package net.nnaabbee.example.demo.controllers

import net.nnaabbee.example.demo.responses.UserResponse
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.jdbc.Sql
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.result.MockMvcResultHandlers.print
import org.springframework.test.web.servlet.setup.MockMvcBuilders

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import net.nnaabbee.example.demo.requests.UserPostRequest
import org.assertj.core.api.Assertions.assertThat
import org.springframework.http.MediaType

@AutoConfigureMockMvc
@SpringBootTest
class UserControllerTests {

    lateinit var mockMvc: MockMvc

    @Autowired
    lateinit var target: UserController

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders.standaloneSetup(target).build()
    }

    @Test
    @Sql(statements = ["INSERT INTO users (name, age, created_at, updated_at) VALUES ('test', 21, now(), now());"])
    fun getUserListTest() {
        val userData = target.userRepository.findAll().last()
        val responseJsonString = mockMvc.perform(
                MockMvcRequestBuilders.get("/users/")
        )
                .andDo(print())
                .andExpect(status().isOk)
                // レスポンスボディを文字列として取得
                .andReturn().response.contentAsString

        val objectMapper = jacksonObjectMapper()
        val results = objectMapper.readValue<List<UserResponse>>(responseJsonString)
        val actual = results.filter { it.id == userData.id }
        val expects = listOf(UserResponse.create(userData))

        assertThat(actual).isEqualTo(expects)
    }

    @Test
    fun registerUser() {
        val param = UserPostRequest("テスト", 23)
        val responseJsonString = mockMvc.perform(
                MockMvcRequestBuilders.post("/users/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .
        )
                .andDo(print())
                .andExpect(status().isOk)
                // レスポンスボディを文字列として取得
                .andReturn().response.contentAsString

        val objectMapper = jacksonObjectMapper()
        val result = objectMapper.readValue<UserResponse>(responseJsonString)
        assertThat(result.name).isEqualTo("テスト")
        assertThat(result.age).isEqualTo(23)
    }
}
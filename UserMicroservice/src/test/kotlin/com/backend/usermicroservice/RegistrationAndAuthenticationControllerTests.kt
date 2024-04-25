package com.backend.usermicroservice

import com.backend.usermicroservice.controllers.UserController
import com.backend.usermicroservice.UserService
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@ExtendWith(SpringExtension::class)
@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

    @MockBean
    private lateinit var userService: UserService

    @Autowired
    private lateinit var context: WebApplicationContext

    @BeforeEach
    fun setup() {
        mockMvc = MockMvcBuilders
            .webAppContextSetup(context)
            .build()
    }

    @Test
    fun `register new user`() {
        val newUser = User(name = "testUser", email = "testEmail@test.com", userPassword = "testPassword")
        val registrationRequest = UserController.RegistrationRequest(newUser.name, newUser.email, newUser.userPassword)

        Mockito.`when`(userService.registerUser(newUser.name, newUser.email, newUser.userPassword)).thenReturn(newUser)

        mockMvc.perform(
            post("/users/register")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(registrationRequest))
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("$.name").value(newUser.name))
            .andExpect(jsonPath("$.email").value(newUser.email))
    }
}
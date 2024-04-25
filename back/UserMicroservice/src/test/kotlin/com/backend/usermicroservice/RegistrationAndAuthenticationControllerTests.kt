package com.backend.usermicroservice


import com.backend.usermicroservice.controllers.AuthenticationRequest
import com.backend.usermicroservice.controllers.RegistrationRequest
import com.backend.usermicroservice.models.User
import com.fasterxml.jackson.databind.ObjectMapper
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.http.MediaType
import org.springframework.test.context.ActiveProfiles
import org.springframework.test.context.junit.jupiter.SpringExtension
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

@ExtendWith(SpringExtension::class)
@SpringBootTest
@ActiveProfiles("test")
@AutoConfigureMockMvc
class RegistrationAndAuthenticationControllerTests {

    @Autowired
    private lateinit var mockMvc: MockMvc

    @Autowired
    private lateinit var objectMapper: ObjectMapper

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
        val newUser = User(name = "testUser", phoneNumber = "+1234567890", userPassword = "testPassword")
        val registrationRequest = RegistrationRequest(newUser.phoneNumber, newUser.userPassword, newUser.name)

        val result = mockMvc.perform(
            post("/register")
                .content(objectMapper.writeValueAsString(registrationRequest))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("body.user.name").value(newUser.name))
            .andExpect(jsonPath("body.user.phoneNumber").value(newUser.phoneNumber))
    }

    @Test
    fun `authenticate user`() {
        val newUser = User(name = "testUser", phoneNumber = "+123456789", userPassword = "testPassword")
        val registrationRequest = RegistrationRequest(newUser.phoneNumber, newUser.userPassword, newUser.name)

        mockMvc.perform(
            post("/register")
                .content(objectMapper.writeValueAsString(registrationRequest))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("body.user.name").value(newUser.name))
            .andExpect(jsonPath("body.user.phoneNumber").value(newUser.phoneNumber))

        val loginRequest = AuthenticationRequest(newUser.phoneNumber, newUser.userPassword)

        mockMvc.perform(
            post("/authenticate")
                .content(objectMapper.writeValueAsString(loginRequest))
                .contentType(MediaType.APPLICATION_JSON)
        )
            .andExpect(status().isOk)
            .andExpect(jsonPath("jwt").exists())
    }


}
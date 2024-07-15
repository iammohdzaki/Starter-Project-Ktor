package com.api.routing

import com.api.Endpoints
import com.api.configureServer
import com.api.controllers.UserController
import com.api.routes.UserRoutes
import com.api.sources.UserDataSource
import com.base.ServerConfig
import com.base.hashing.HashingService
import com.base.hashing.HashingServiceImpl
import com.base.jwt.service.TokenService
import com.base.jwt.service.TokenServiceImpl
import com.data.User
import com.data.request.UserRequest
import com.plugins.configureSecurity
import com.plugins.setupConfig
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpStatusCode
import io.ktor.http.contentType
import io.ktor.server.testing.testApplication
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import org.koin.test.KoinTest
import kotlin.test.assertEquals

class UserRoutesTest : KoinTest {

    private val userDataSource: UserDataSource = mockk()

    @BeforeEach
    fun setup() {
        startKoin {
            modules(
                module {
                    single {
                        ServerConfig()
                    }
                    single<TokenService> {
                        TokenServiceImpl()
                    }
                    single<HashingService> {
                        HashingServiceImpl()
                    }
                    single { UserController(get(), get(), get(), get()) }
                    single { userDataSource }
                }
            )
        }
    }

    @AfterEach
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun registerUser() = testApplication {
        configureServer()
        application {
            setupConfig()
            configureSecurity()
        }
        val userRequest = UserRequest(
            fullName = "Zaki",
            email = "zaki@yopmail.com",
            phoneNumber = "9700000001",
            countryCode = "+91",
            password = "Qwerty@123"
        )
        coEvery { userDataSource.checkIfUserExists(userRequest.email) } returns null
        coEvery { userDataSource.insertUser(any()) } returns true

        routing {
            UserRoutes()
        }

        client.post(Endpoints.USER_REGISTER_REQUEST) {
            contentType(ContentType.Application.Json)
            header("Accept-Language", "en")
            setBody("{\"email\":\"zaki@yopmail.com\",\"phoneNumber\":\"9700000001\",\"countryCode\":\"+91\",\"password\":\"Qwerty@123\",\"fullName\":\"Zaki\"}")
        }.apply {
            println(bodyAsText())
            assertEquals(HttpStatusCode.OK, status)
        }

        coVerify { userDataSource.checkIfUserExists(userRequest.email) }
        coVerify { userDataSource.insertUser(any()) }
    }

    @Test
    fun loginUser() = testApplication {
        configureServer()
        application {
            setupConfig()
            configureSecurity()
        }
        val userRequest = UserRequest(
            fullName = "Zaki",
            email = "zaki@yopmail.com",
            phoneNumber = "9700000001",
            countryCode = "+91",
            password = "Qwerty@123"
        )
        coEvery { userDataSource.checkIfUserExists(userRequest.email) } returns User(
            userId = "6694e2eeafdf7f2b699a71dc",
            fullName = "Zaki",
            email = "zaki@yopmail.com",
            phoneNumber = "9700000001",
            countryCode = "+91",
            createdOn = 1721033454278,
            password = "8040d342b808167ee13359a2a26b04e4e90d3f08b0349e85c357504e259bbdf0",
            salt = "078b0953781b1bb5a7e2befe73c7deb5c82bcbd77cd593857ba101bb69deaa87"
        )
        routing {
            UserRoutes()
        }

        client.post(Endpoints.USER_LOGIN_REQUEST) {
            contentType(ContentType.Application.Json)
            header("Accept-Language", "en")
            setBody("{\"email\":\"zaki@yopmail.com\",\"phoneNumber\":\"9700000001\",\"countryCode\":\"+91\",\"password\":\"Qwerty@123\",\"fullName\":\"Zaki\"}")
        }.apply {
            println(bodyAsText())
            assertEquals(HttpStatusCode.OK, status)
        }

        coVerify { userDataSource.checkIfUserExists(userRequest.email) }
    }

    @Test
    fun getUserProfile() = testApplication {
        configureServer()
        application {
            setupConfig()
            configureSecurity()
        }
        coEvery { userDataSource.getUser(any()) } returns User(
            userId = "6694e2eeafdf7f2b699a71dc",
            fullName = "Zaki",
            email = "zaki@yopmail.com",
            phoneNumber = "9700000001",
            countryCode = "+91",
            createdOn = 1721033454278,
            password = "8040d342b808167ee13359a2a26b04e4e90d3f08b0349e85c357504e259bbdf0",
            salt = "078b0953781b1bb5a7e2befe73c7deb5c82bcbd77cd593857ba101bb69deaa87"
        )
        routing {
            UserRoutes()
        }

        client.get(Endpoints.USER_PROFILE_REQUEST) {
            header("Accept-Language", "en")
            header(
                "Authorization",
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJodHRwOi8vMC4wLjAuMDo4MDgwIiwiaXNzIjoiaHR0cDovLzAuMC4wLjA6ODA4MCIsImV4cCI6MTc1MjU3MDM5OSwidXNlcklkIjoiNjY5NGUyZWVhZmRmN2YyYjY5OWE3MWRjIn0.i353BcHel_uR6_Gtx1q17w454Zy652x_8WaoQdPr30Y"
            )
        }.apply {
            println(bodyAsText())
            assertEquals(HttpStatusCode.OK, status)
        }

        coVerify { userDataSource.getUser(any()) }
    }

    @Test
    fun editProfile() = testApplication {
        configureServer()
        application {
            setupConfig()
            configureSecurity()
        }
        coEvery { userDataSource.getUser(any()) } returns User(
            userId = "6694e2eeafdf7f2b699a71dc",
            fullName = "Zaki",
            email = "zaki@yopmail.com",
            phoneNumber = "9700000001",
            countryCode = "+91",
            createdOn = 1721033454278,
            password = "8040d342b808167ee13359a2a26b04e4e90d3f08b0349e85c357504e259bbdf0",
            salt = "078b0953781b1bb5a7e2befe73c7deb5c82bcbd77cd593857ba101bb69deaa87"
        )
        coEvery { userDataSource.updateUser(any()) } returns true
        routing {
            UserRoutes()
        }

        client.post(Endpoints.USER_PROFILE_EDIT_REQUEST) {
            contentType(ContentType.Application.Json)
            header("Accept-Language", "en")
            header(
                "Authorization",
                "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhdWQiOiJodHRwOi8vMC4wLjAuMDo4MDgwIiwiaXNzIjoiaHR0cDovLzAuMC4wLjA6ODA4MCIsImV4cCI6MTc1MjU3MDM5OSwidXNlcklkIjoiNjY5NGUyZWVhZmRmN2YyYjY5OWE3MWRjIn0.i353BcHel_uR6_Gtx1q17w454Zy652x_8WaoQdPr30Y"
            )
            setBody("{\"email\":\"zaki@yopmail.com\",\"phoneNumber\":\"9700000002\",\"countryCode\":\"+91\",\"password\":\"Qwerty@123\",\"fullName\":\"Zaki\"}")
        }.apply {
            println(bodyAsText())
            assertEquals(HttpStatusCode.OK, status)
        }

        coVerify { userDataSource.getUser(any()) }
        coVerify { userDataSource.updateUser(any()) }
    }
}
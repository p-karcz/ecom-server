package com.karcz.piotr

import com.karcz.piotr.repository.dao.CustomerDaoImpl
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import com.karcz.piotr.repository.initDatabase
import com.karcz.piotr.routes.*
import com.karcz.piotr.security.JWTService
import io.ktor.auth.*
import io.ktor.auth.jwt.*

// TODO implement refresh token
// TODO add tests
// TODO implement koin
// TODO implement coroutines

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
@JvmOverloads
fun Application.module(testing: Boolean = false) {
    install(ContentNegotiation) {
        gson {}
        json()
    }

    install(DefaultHeaders)
    install(CallLogging)

    install(StatusPages) {
        exception<Throwable> { cause ->
            call.response.status(HttpStatusCode.InternalServerError)
            call.respond(HttpStatusCode.InternalServerError, cause.message.toString())
            throw cause
        }
        exception<MissingRequestParameterException> { cause ->
            call.respond(HttpStatusCode.BadRequest)
            throw cause
        }
    }

    initDatabase()

    val jwtService = JWTService(
        environment.config.property("jwt.secret").getString(),
        environment.config.property("jwt.issuer").getString(),
        environment.config.property("jwt.audience").getString()
    )

    install(Authentication) {
        jwt {
            realm = environment.config.property("jwt.realm").getString()
            verifier(jwtService.verifier)

            validate { credential ->
                CustomerDaoImpl().get(credential.payload.getClaim("email").asString())?.let {
                    UserIdPrincipal(it.email)
                }
            }
        }
    }

    install(Routing) {
        cartRoute()
        customerRoute()
        ordersRoute()
        productRoute()
        authorizationRoute(jwtService)
    }
}

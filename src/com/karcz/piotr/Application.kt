package com.karcz.piotr

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import com.karcz.piotr.database.initDatabase
import com.karcz.piotr.routes.loginRoute

fun main(args: Array<String>): Unit = io.ktor.server.netty.EngineMain.main(args)

@Suppress("unused")
@kotlin.jvm.JvmOverloads
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

    install(Routing) {
        loginRoute()
    }
}

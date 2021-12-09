package com.karcz.piotr

import io.ktor.application.*
import io.ktor.features.*
import io.ktor.gson.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.serialization.*
import com.karcz.piotr.repository.initDatabase
import com.karcz.piotr.routes.*

// TODO start hashing passwords
// TODO add tests
// TODO implement authentication
// TODO implement koin
// TODO implement coroutines
// TODO add data transfer objects and start using domain models

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
        cartRoute()
        customerRoute()
        orderDetailsRoute()
        ordersRoute()
        productRoute()
    }
}

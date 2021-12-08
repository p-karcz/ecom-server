package com.karcz.piotr.routes

import io.ktor.application.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import com.karcz.piotr.database.data.ProductsDatabaseTable

fun Route.loginRoute() {
    route("/login") {
        get {
            call.respond(transaction { ProductsDatabaseTable.select { ProductsDatabaseTable.id eq 1 } })
        }
    }
}
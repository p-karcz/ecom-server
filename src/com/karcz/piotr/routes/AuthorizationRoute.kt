package com.karcz.piotr.routes

import com.karcz.piotr.data.CustomerModel
import com.karcz.piotr.repository.dao.CustomerDao
import com.karcz.piotr.repository.dao.CustomerDaoImpl
import com.karcz.piotr.transfer.data.Response
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.authorizationRoute() {
    val customerDao: CustomerDao = CustomerDaoImpl()

    route("/login") {
        post {
            val request = try {
                call.receive<CustomerModel>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            // TODO implement login with authentication
        }
    }

    route("/register") {
        post {
            val request = try {
                call.receive<CustomerModel>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            if (customerDao.isIn(request)) {
                call.respond(HttpStatusCode.OK, Response(false, "This email address is already taken."))
            } else {
                customerDao.add(request)
                call.respond(HttpStatusCode.OK, Response(true, "Customer created."))
            }
        }
    }
}

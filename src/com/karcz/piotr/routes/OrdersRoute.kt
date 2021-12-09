package com.karcz.piotr.routes

import com.karcz.piotr.repository.dao.OrderDao
import com.karcz.piotr.repository.dao.OrderDaoImpl
import com.karcz.piotr.repository.resources.OrderResource
import com.karcz.piotr.transfer.Response
import io.ktor.application.*
import io.ktor.features.ContentTransformationException
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.lang.NumberFormatException

fun Route.ordersRoute() {
    val orderDao: OrderDao = OrderDaoImpl()

    route("me/orders") {
        get {
            // TODO retrieve email
            val orders = orderDao.getAllForCustomer("sampleemail@example.com")
            call.respond(HttpStatusCode.OK, orders)
        }

        post {
            val request = try {
                call.receive<OrderResource>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            if (orderDao.isIn(request)) {
                call.respond(HttpStatusCode.OK, Response(false, "Order already in history."))
            } else {
                orderDao.add(request)
                call.respond(HttpStatusCode.OK, Response(true, "Order added."))
            }
        }
    }

    route("me/orders/{orderId}") {
        get {
            call.parameters["orderId"]?.let {
                try {
                    val orderId = it.toInt()
                    val order = orderDao.get(orderId)
                    if (order != null) {
                        call.respond(HttpStatusCode.OK, order)
                    } else {
                        call.respond(HttpStatusCode.OK, Response(false, "Cannot find order with id: $orderId."))
                    }
                } catch (e: NumberFormatException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            } ?: call.respond(HttpStatusCode.BadRequest)
        }
    }
}
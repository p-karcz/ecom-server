package com.karcz.piotr.routes

import com.karcz.piotr.data.OrderModel
import com.karcz.piotr.repository.dao.OrderDao
import com.karcz.piotr.repository.dao.OrderDaoImpl
import com.karcz.piotr.repository.dao.OrderDetailDao
import com.karcz.piotr.repository.dao.OrderDetailDaoImpl
import com.karcz.piotr.transfer.data.Response
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.ordersRoute() {
    val orderDao: OrderDao = OrderDaoImpl()
    val orderDetailDao: OrderDetailDao = OrderDetailDaoImpl()

    route("me/orders") {
        get {
            // TODO retrieve email
            val orders = orderDao.getAllForCustomer("sampleemail@example.com")
            call.respond(HttpStatusCode.OK, orders)
        }

        post {
            val request = try {
                call.receive<OrderModel>()
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
                    val order = orderDetailDao.getAll(orderId)
                    call.respond(HttpStatusCode.OK, order)
                } catch (e: NumberFormatException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            } ?: call.respond(HttpStatusCode.BadRequest)
        }
    }
}

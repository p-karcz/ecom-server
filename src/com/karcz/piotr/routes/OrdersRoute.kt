package com.karcz.piotr.routes

import com.karcz.piotr.data.OrderModel
import com.karcz.piotr.repository.dao.OrderDao
import com.karcz.piotr.repository.dao.OrderDaoImpl
import com.karcz.piotr.repository.dao.OrderDetailDao
import com.karcz.piotr.repository.dao.OrderDetailDaoImpl
import com.karcz.piotr.transfer.data.Response
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.sql.SQLException

fun Route.ordersRoute() {
    val orderDao: OrderDao = OrderDaoImpl()
    val orderDetailDao: OrderDetailDao = OrderDetailDaoImpl()

    authenticate {
        route("me/orders") {
            get {
                val email = call.principal<UserIdPrincipal>()?.name
                if (email == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }

                val orders = orderDao.getAllForCustomer(email)
                call.respond(HttpStatusCode.OK, orders)
            }

            post {
                val orderModel = try {
                    call.receive<OrderModel>()
                } catch (e: ContentTransformationException) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }

                val email = call.principal<UserIdPrincipal>()?.name
                if (email == null || email != orderModel.customerEmail) {
                    call.respond(HttpStatusCode.Forbidden)
                    return@post
                }

                if (orderDao.isIn(orderModel)) {
                    call.respond(HttpStatusCode.OK, Response(false, "Order already in history."))
                } else {
                    try {
                        orderDao.add(orderModel)
                    } catch (e: SQLException) {
                        call.respond(HttpStatusCode.InternalServerError)
                    }
                    call.respond(HttpStatusCode.OK, Response(true, "Order added."))
                }
            }
        }

        route("me/orders/{orderId}") {
            get {
                val orderString = call.parameters["orderId"]
                if (orderString == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                val orderId: Int = try {
                    orderString.toInt()
                } catch (e: NumberFormatException) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@get
                }

                val email = call.principal<UserIdPrincipal>()?.name
                val order = orderDao.get(orderId)
                if (email == null || order == null || email != order.customerEmail) {
                    call.respond(HttpStatusCode.Forbidden)
                    return@get
                }

                val orders = orderDetailDao.getAllForOrder(orderId)
                call.respond(HttpStatusCode.OK, orders)
            }
        }
    }
}

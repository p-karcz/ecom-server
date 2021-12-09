package com.karcz.piotr.routes

import com.karcz.piotr.repository.dao.OrderDetailDao
import com.karcz.piotr.repository.dao.OrderDetailDaoImpl
import io.ktor.routing.*

fun Route.orderDetailsRoute() {
    val orderDetailDao: OrderDetailDao = OrderDetailDaoImpl()

    route("me/orders/{orderId}/details") {

    }
}
package com.karcz.piotr.routes

import com.karcz.piotr.domaindata.OrderDomainModel
import com.karcz.piotr.repository.dao.*
import com.karcz.piotr.transfer.data.AllOrdersTransferModel
import com.karcz.piotr.transfer.data.AllOrderDetailsWithProductsTransferModel
import com.karcz.piotr.transfer.data.AllOrderDetailsTransferModel
import com.karcz.piotr.transfer.data.Response
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.datetime.Clock
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.SQLException

fun Route.ordersRoute() {
    val orderDao: OrderDao = OrderDaoImpl()
    val orderDetailDao: OrderDetailDao = OrderDetailDaoImpl()
    val productDao: ProductDao = ProductDaoImpl()
    val customerDao: CustomerDao = CustomerDaoImpl()
    val addressDao: AddressDao = AddressDaoImpl()

    authenticate {
        route("me/orders") {
            get {
                val email = call.principal<UserIdPrincipal>()?.name
                if (email == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }

                val orders = orderDao.getAllForCustomer(email)
                call.respond(HttpStatusCode.OK, AllOrdersTransferModel(orders.map { it.toTransferModel() }))
            }

            post {
                val orderTransferModel = try {
                    call.receive<AllOrderDetailsTransferModel>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }

                val orderDomainModel = orderTransferModel.toDomainModel()
                if (orderDomainModel == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }

                val email = call.principal<UserIdPrincipal>()?.name
                if (email == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@post
                }

                val customer = customerDao.get(email)
                val customerAddress = addressDao.get(customer!!.addressId)
                val totalQuantity = orderDomainModel.orderDetailList.sumOf { it.quantity }
                val totalPrice = orderDomainModel.orderDetailList.sumOf { it.price }

                if (customerAddress == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@post
                }

                val orderAddressId = try {
                    addressDao.add(customerAddress.copy())
                } catch (e: SQLException) {
                    call.respond(HttpStatusCode.InternalServerError)
                    return@post
                }

                val newOrder = OrderDomainModel(
                    OrderDomainModel.DEFAULT_NOT_USED_ORDER_ID,
                    email,
                    orderAddressId,
                    totalQuantity,
                    totalPrice,
                    Clock.System.now().toString()
                )

                if (orderDomainModel.orderDetailList.any { !productDao.isAvailable(it.productId, it.quantity) }) {
                    call.respond(HttpStatusCode.OK, Response(false, "There are less available products."))
                    return@post
                }

                try {
                    transaction {
                        val newOrderId = orderDao.add(newOrder)
                        orderDomainModel.orderDetailList.forEach {
                            val newQuantity = productDao.get(it.productId)!!.quantity.minus(it.quantity)
                            productDao.updateQuantity(it.productId, newQuantity)
                            orderDetailDao.add(it.copy(orderId = newOrderId))
                        }
                    }
                } catch (e: SQLException) {
                    call.respond(HttpStatusCode.InternalServerError)
                }

                call.respond(HttpStatusCode.OK, Response(true, "Order added."))
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
                val products = orders.mapNotNull { productDao.get(it.productId) }
                call.respond(HttpStatusCode.OK, AllOrderDetailsWithProductsTransferModel(
                    orders.map { it.toTransferModel() },
                    products.map { it.toTransferModel() }
                ))
            }
        }
    }
}

package com.karcz.piotr.routes

import com.karcz.piotr.repository.dao.CartDao
import com.karcz.piotr.repository.dao.CartDaoImpl
import com.karcz.piotr.repository.resources.CartResource
import com.karcz.piotr.transfer.Response
import io.ktor.application.*
import io.ktor.features.ContentTransformationException
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.cartRoute() {
    val cartDao: CartDao = CartDaoImpl()

    route("/me/cart") {
        get {
            // TODO retrieve email when a user is logged in
            val cartItems: List<CartResource> = cartDao.getAllForClient("sampleemail@example.com")
            call.respond(HttpStatusCode.OK, cartItems)
        }
    }

    route("/me/cart/addItem") {
        post {
            val request = try {
                call.receive<CartResource>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            if (cartDao.isIn(request)) {
                val currentCartItem = cartDao.get(request.customerEmail, request.productId)
                cartDao.update(cart = request.copy(quantity = request.quantity + (currentCartItem?.quantity ?: 0)))
            } else {
                cartDao.add(request)
            }

            call.respond(HttpStatusCode.OK, Response(true, "Item added to cart."))
        }
    }

    route("/me/cart/removeItem") {
        delete {
            val request = try {
                call.receive<CartResource>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            if (cartDao.isIn(request)) {
                cartDao.remove(request)
                call.respond(HttpStatusCode.OK, Response(true, "Item removed from cart"))
            } else {
                call.respond(HttpStatusCode.OK, Response(false, "Item was already removed from cart"))
            }
        }
    }

    route("/me/cart/updateItem") {
        put {
            val request = try {
                call.receive<CartResource>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }

            if (cartDao.isIn(request)) {
                cartDao.update(request)
                call.respond(HttpStatusCode.OK, Response(true, "Item updated."))
            } else {
                call.respond(HttpStatusCode.OK, Response(false, "Item is not in cart."))
            }
        }
    }
}
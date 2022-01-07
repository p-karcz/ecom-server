package com.karcz.piotr.routes

import com.karcz.piotr.data.CartModel
import com.karcz.piotr.repository.dao.CartDao
import com.karcz.piotr.repository.dao.CartDaoImpl
import com.karcz.piotr.transfer.data.Response
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.sql.SQLException

fun Route.cartRoute() {
    val cartDao: CartDao = CartDaoImpl()

    authenticate {
        route("/me/cart") {
            get {
                val email = call.principal<UserIdPrincipal>()?.name
                if (email != null) {
                    val cartItems: List<CartModel> = cartDao.getAllForClient(email)
                    call.respond(HttpStatusCode.OK, cartItems)
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }

        route("/me/cart/addItem") {
            post {
                val cartModel = try {
                    call.receive<CartModel>()
                } catch (e: ContentTransformationException) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }

                val email = call.principal<UserIdPrincipal>()?.name
                if (email == null || email != cartModel.customerEmail) {
                    call.respond(HttpStatusCode.Forbidden)
                    return@post
                }

                try {
                    if (cartDao.isIn(cartModel)) {
                        val currentCartItem = cartDao.get(cartModel.customerEmail, cartModel.productId)
                        cartDao.update(cartModel = cartModel.copy(quantity = cartModel.quantity + (currentCartItem?.quantity ?: 0)))
                    } else {
                        cartDao.add(cartModel)
                    }
                } catch (e: SQLException) {
                    call.respond(HttpStatusCode.InternalServerError)
                }

                call.respond(HttpStatusCode.OK, Response(true, "Item added to cart."))
            }
        }

        route("/me/cart/removeItem") {
            delete {
                val cartModel = try {
                    call.receive<CartModel>()
                } catch (e: ContentTransformationException) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                val email = call.principal<UserIdPrincipal>()?.name
                if (email == null || email != cartModel.customerEmail) {
                    call.respond(HttpStatusCode.Forbidden)
                    return@delete
                }

                if (cartDao.isIn(cartModel)) {
                    try {
                        cartDao.remove(cartModel)
                    } catch (e: SQLException) {
                        call.respond(HttpStatusCode.InternalServerError)
                    }
                    call.respond(HttpStatusCode.OK, Response(true, "Item removed from cart"))
                } else {
                    call.respond(HttpStatusCode.OK, Response(false, "Item was already removed from cart"))
                }
            }
        }

        route("/me/cart/updateItem") {
            put {
                val cartModel = try {
                    call.receive<CartModel>()
                } catch (e: ContentTransformationException) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@put
                }

                val email = call.principal<UserIdPrincipal>()?.name
                if (email == null || email != cartModel.customerEmail) {
                    call.respond(HttpStatusCode.Forbidden)
                    return@put
                }

                if (cartDao.isIn(cartModel)) {
                    try {
                        if (cartModel.quantity > 0) {
                            cartDao.update(cartModel)
                        } else {
                            cartDao.remove(cartModel)
                        }
                    } catch (e: SQLException) {
                        call.respond(HttpStatusCode.InternalServerError)
                    }
                    call.respond(HttpStatusCode.OK, Response(true, "Item updated."))
                } else {
                    call.respond(HttpStatusCode.OK, Response(false, "Item is not in cart."))
                }
            }
        }
    }
}


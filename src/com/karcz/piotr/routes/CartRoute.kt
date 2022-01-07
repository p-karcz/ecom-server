package com.karcz.piotr.routes

import com.karcz.piotr.data.CartModel
import com.karcz.piotr.repository.dao.CartDao
import com.karcz.piotr.repository.dao.CartDaoImpl
import com.karcz.piotr.repository.dao.ProductDao
import com.karcz.piotr.repository.dao.ProductDaoImpl
import com.karcz.piotr.transfer.data.CartResponseModel
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
    val productDao: ProductDao = ProductDaoImpl()

    authenticate {
        route("/me/cart") {
            get {
                val email = call.principal<UserIdPrincipal>()?.name
                if (email != null) {
                    val cartItems: List<CartModel> = cartDao.getAllForClient(email)
                    val productItems = cartItems.mapNotNull { productDao.get(it.productId) }
                    call.respond(HttpStatusCode.OK, CartResponseModel(cartItems, productItems))
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }

        route("/me/cart/addItem") {
            post {
                val cartModel = try {
                    call.receive<CartModel>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }

                val email = call.principal<UserIdPrincipal>()?.name
                if (email == null || email != cartModel.customerEmail) {
                    call.respond(HttpStatusCode.Forbidden)
                    return@post
                }

                try {
                    if (productDao.isAvailable(cartModel.productId, cartModel.quantity)) {
                        if (cartDao.isIn(cartModel)) {
                            val currentCartItem = cartDao.get(cartModel.customerEmail, cartModel.productId)
                            cartDao.update(
                                cartModel = cartModel.copy(
                                    quantity = cartModel.quantity + (currentCartItem?.quantity ?: 0)
                                )
                            )
                        } else {
                            cartDao.add(cartModel)
                        }
                    } else {
                        call.respond(HttpStatusCode.OK, Response(false, "There are less available products of this id."))
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
                } catch (e: Exception) {
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
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@put
                }

                val email = call.principal<UserIdPrincipal>()?.name
                if (email == null || email != cartModel.customerEmail) {
                    call.respond(HttpStatusCode.Forbidden)
                    return@put
                }

                if (productDao.isAvailable(cartModel.productId, cartModel.quantity)) {
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
                } else {
                    call.respond(HttpStatusCode.OK, Response(false, "There are less available products of this id."))
                }
            }
        }
    }
}


package com.karcz.piotr.routes

import com.karcz.piotr.domaindata.CartDomainModel
import com.karcz.piotr.repository.dao.CartDao
import com.karcz.piotr.repository.dao.CartDaoImpl
import com.karcz.piotr.repository.dao.ProductDao
import com.karcz.piotr.repository.dao.ProductDaoImpl
import com.karcz.piotr.transfer.data.AllCartsTransferModel
import com.karcz.piotr.transfer.data.CartTransferModel
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
                    val cartItems: List<CartDomainModel> = cartDao.getAllForClient(email)
                    val productItems = cartItems.mapNotNull { productDao.get(it.productId) }
                    call.respond(HttpStatusCode.OK, AllCartsTransferModel(
                        cartItems.map { it.toTransferModel() },
                        productItems.map { it.toTransferModel() }
                    ))
                } else {
                    call.respond(HttpStatusCode.NotFound)
                }
            }
        }

        route("/me/cart/addItem") {
            post {
                val cartTransferModel = try {
                    call.receive<CartTransferModel>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }

                val cartDomainModel = cartTransferModel.toDomainModel()
                if (cartDomainModel == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }

                val email = call.principal<UserIdPrincipal>()?.name
                if (email == null || email != cartDomainModel.customerEmail) {
                    call.respond(HttpStatusCode.Forbidden)
                    return@post
                }

                try {
                    if (productDao.isAvailable(cartDomainModel.productId, cartDomainModel.quantity)) {
                        if (cartDao.isIn(cartDomainModel)) {
                            val currentCartItem = cartDao.get(cartDomainModel.customerEmail, cartDomainModel.productId)
                            cartDao.update(
                                cartDomainModel = cartDomainModel.copy(
                                    quantity = cartDomainModel.quantity + (currentCartItem?.quantity ?: 0)
                                )
                            )
                        } else {
                            cartDao.add(cartDomainModel)
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
                val cartTransferModel = try {
                    call.receive<CartTransferModel>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                val cartDomainModel = cartTransferModel.toDomainModel()
                if (cartDomainModel == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                val email = call.principal<UserIdPrincipal>()?.name
                if (email == null || email != cartDomainModel.customerEmail) {
                    call.respond(HttpStatusCode.Forbidden)
                    return@delete
                }

                if (cartDao.isIn(cartDomainModel)) {
                    try {
                        cartDao.remove(cartDomainModel)
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
                val cartTransferModel = try {
                    call.receive<CartTransferModel>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@put
                }

                val cartDomainModel = cartTransferModel.toDomainModel()
                if (cartDomainModel == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@put
                }

                val email = call.principal<UserIdPrincipal>()?.name
                if (email == null || email != cartDomainModel.customerEmail) {
                    call.respond(HttpStatusCode.Forbidden)
                    return@put
                }

                if (productDao.isAvailable(cartDomainModel.productId, cartDomainModel.quantity)) {
                    if (cartDao.isIn(cartDomainModel)) {
                        try {
                            if (cartDomainModel.quantity > 0) {
                                cartDao.update(cartDomainModel)
                            } else {
                                cartDao.remove(cartDomainModel)
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


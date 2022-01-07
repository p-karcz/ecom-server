package com.karcz.piotr.routes

import com.karcz.piotr.data.AddressModel
import com.karcz.piotr.data.CustomerModel
import com.karcz.piotr.data.OrderModel
import com.karcz.piotr.repository.dao.*
import com.karcz.piotr.transfer.data.Response
import io.ktor.application.*
import io.ktor.auth.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.SQLException

fun Route.customerRoute() {
    val customerDao: CustomerDao = CustomerDaoImpl()
    val addressDao: AddressDao = AddressDaoImpl()
    val orderDetailDao: OrderDetailDao = OrderDetailDaoImpl()
    val orderDao: OrderDao = OrderDaoImpl()
    val cartDao: CartDao = CartDaoImpl()

    authenticate {
        route("/me") {
            get {
                val email = call.principal<UserIdPrincipal>()?.name
                if (email == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }

                val customer = customerDao.get(email)
                if (customer != null) {
                    call.respond(HttpStatusCode.OK, customer)
                } else {
                    call.respond(HttpStatusCode.OK, Response(false, "Customer does not exist."))
                }
            }
        }

        route("/me/update") {
            put {
                val customerModel = try {
                    call.receive<CustomerModel>()
                } catch (e: ContentTransformationException) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@put
                }

                val email = call.principal<UserIdPrincipal>()?.name
                if (email == null || email != customerModel.email) {
                    call.respond(HttpStatusCode.Forbidden)
                    return@put
                }

                if (customerDao.isIn(customerModel.email)) {
                    try {
                        customerDao.update(customerModel)
                    } catch (e: SQLException) {
                        call.respond(HttpStatusCode.InternalServerError)
                    }
                    call.respond(HttpStatusCode.OK, Response(true, "Customer data updated."))
                } else {
                    call.respond(HttpStatusCode.OK, Response(false, "Customer does not exist."))
                }
            }
        }

        route("/me/removeAccount") {
            delete {
                val customerModel = try {
                    call.receive<CustomerModel>()
                } catch (e: ContentTransformationException) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                val email = call.principal<UserIdPrincipal>()?.name
                if (email == null || email != customerModel.email) {
                    call.respond(HttpStatusCode.Forbidden)
                    return@delete
                }

                if (customerDao.isIn(customerModel.email)) {
                    try {
                        transaction {
                            val customerOrderAddressesIds = orderDao.getAllAddressesIdsForCustomer(email)
                            val customerAddressId = customerModel.addressId

                            customerDao.remove(email)
                            customerOrderAddressesIds.forEach {
                                addressDao.remove(it)
                            }
                            addressDao.remove(customerAddressId)
                        }
                    } catch (e: SQLException) {
                        call.respond(HttpStatusCode.InternalServerError)
                    }

                    call.respond(HttpStatusCode.OK, Response(true, "Customer removed."))
                } else {
                    call.respond(HttpStatusCode.OK, Response(false, "Customer does not exist."))
                }
            }
        }

        route("/me/address") {
            get {
                val email = call.principal<UserIdPrincipal>()?.name
                if (email == null) {
                    call.respond(HttpStatusCode.NotFound)
                    return@get
                }

                val address = customerDao.get(email)?.addressId?.let { addressDao.get(it) }
                if (address != null) {
                    call.respond(HttpStatusCode.OK, address)
                } else {
                    call.respond(HttpStatusCode.OK, Response(false, "AddressId is not correct."))
                }
            }

            post {
                val addressModel = try {
                    call.receive<AddressModel>()
                } catch (e: ContentTransformationException) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@post
                }

                val email = call.principal<UserIdPrincipal>()?.name
                if (email == null) {
                    call.respond(HttpStatusCode.Forbidden)
                    return@post
                }

                val customer = customerDao.get(email)
                if (customer == null || customer.addressId != addressModel.id) {
                    call.respond(HttpStatusCode.Forbidden)
                    return@post
                }

                if (addressDao.isInOrFalse(addressModel)) {
                    call.respond(HttpStatusCode.OK, Response(false, "Address already exists."))
                } else {
                    try {
                        addressDao.add(addressModel)
                    } catch (e: SQLException) {
                        call.respond(HttpStatusCode.InternalServerError)
                    }
                    call.respond(HttpStatusCode.OK, Response(true, "Address added."))
                }
            }

            put {
                val addressModel = try {
                    call.receive<AddressModel>()
                } catch (e: ContentTransformationException) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@put
                }

                val email = call.principal<UserIdPrincipal>()?.name
                if (email == null) {
                    call.respond(HttpStatusCode.Forbidden)
                    return@put
                }

                val customer = customerDao.get(email)
                if (customer == null || customer.addressId != addressModel.id) {
                    call.respond(HttpStatusCode.Forbidden)
                    return@put
                }

                if (addressDao.isInOrFalse(addressModel)) {
                    try {
                        addressDao.update(addressModel)
                    } catch (e: SQLException) {
                        call.respond(HttpStatusCode.InternalServerError)
                    }
                    call.respond(HttpStatusCode.OK, Response(true, "Address updated."))
                } else {
                    call.respond(HttpStatusCode.OK, Response(false, "Address does not exist."))
                }
            }
        }
    }
}

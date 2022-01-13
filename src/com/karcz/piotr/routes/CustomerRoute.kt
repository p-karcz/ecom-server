package com.karcz.piotr.routes

import com.karcz.piotr.repository.dao.*
import com.karcz.piotr.transfer.data.AddressTransferModel
import com.karcz.piotr.transfer.data.CustomerTransferModel
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
    val orderDao: OrderDao = OrderDaoImpl()

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
                    call.respond(HttpStatusCode.OK, customer.toTransferModel())
                } else {
                    call.respond(HttpStatusCode.OK, CustomerTransferModel())
                }
            }
        }

        route("/me/update") {
            put {
                val customerTransferModel = try {
                    call.receive<CustomerTransferModel>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@put
                }

                val customerDomainModel = customerTransferModel.toDomainModel()
                if (customerDomainModel == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@put
                }

                val email = call.principal<UserIdPrincipal>()?.name
                if (email == null || email != customerDomainModel.email) {
                    call.respond(HttpStatusCode.Forbidden)
                    return@put
                }

                if (customerDao.isIn(customerDomainModel.email)) {
                    try {
                        customerDao.update(customerDomainModel)
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
                val customerTransferModel = try {
                    call.receive<CustomerTransferModel>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                val customerDomainModel = customerTransferModel.toDomainModel()
                if (customerDomainModel == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@delete
                }

                val email = call.principal<UserIdPrincipal>()?.name
                if (email == null || email != customerDomainModel.email) {
                    call.respond(HttpStatusCode.Forbidden)
                    return@delete
                }

                if (customerDao.isIn(customerDomainModel.email)) {
                    try {
                        transaction {
                            val customerOrderAddressesIds = orderDao.getAllAddressesIdsForCustomer(email)
                            val customerAddressId = customerDomainModel.addressId

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
                    call.respond(HttpStatusCode.OK, address.toTransferModel())
                } else {
                    call.respond(HttpStatusCode.OK, AddressTransferModel())
                }
            }

            put {
                val addressTransferModel = try {
                    call.receive<AddressTransferModel>()
                } catch (e: Exception) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@put
                }

                val addressDomainModel = addressTransferModel.toDomainModel()
                if (addressDomainModel == null) {
                    call.respond(HttpStatusCode.BadRequest)
                    return@put
                }

                val email = call.principal<UserIdPrincipal>()?.name
                if (email == null) {
                    call.respond(HttpStatusCode.Forbidden)
                    return@put
                }

                val customer = customerDao.get(email)
                if (customer == null || customer.addressId != addressDomainModel.id) {
                    call.respond(HttpStatusCode.Forbidden)
                    return@put
                }

                if (addressDao.isInOrFalse(addressDomainModel)) {
                    try {
                        addressDao.update(addressDomainModel)
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

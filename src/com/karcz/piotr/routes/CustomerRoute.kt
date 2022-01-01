package com.karcz.piotr.routes

import com.karcz.piotr.data.AddressModel
import com.karcz.piotr.data.CustomerModel
import com.karcz.piotr.repository.dao.AddressDao
import com.karcz.piotr.repository.dao.AddressDaoImpl
import com.karcz.piotr.repository.dao.CustomerDao
import com.karcz.piotr.repository.dao.CustomerDaoImpl
import com.karcz.piotr.transfer.data.Response
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*

fun Route.customerRoute() {
    val customerDao: CustomerDao = CustomerDaoImpl()
    val addressDao: AddressDao = AddressDaoImpl()

    route("/me") {
        get {
            val customer = customerDao.get("sampleemail@example.com")
            if (customer != null) {
                call.respond(HttpStatusCode.OK, customer)
            } else {
                call.respond(HttpStatusCode.OK, Response(false, "Customer does not exist."))
            }
        }
    }

    route("/me/update") {
        put {
            val request = try {
                call.receive<CustomerModel>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }

            if (customerDao.isIn(request)) {
                customerDao.update(request)
                call.respond(HttpStatusCode.OK, Response(true, "Customer data updated."))
            } else {
                call.respond(HttpStatusCode.OK, Response(false, "Customer does not exist."))
            }
        }
    }

    route("/me/removeAccount") {
        delete {
            val request = try {
                call.receive<CustomerModel>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            if (customerDao.isIn(request)) {
                customerDao.remove(request)
                // TODO Logout
                call.respond(HttpStatusCode.OK, Response(true, "Customer removed."))
            } else {
                call.respond(HttpStatusCode.OK, Response(false, "Customer does not exist."))
            }
        }
    }

    route("/me/address") {
        get {
            // TODO get addressId from authenticated customer
            val address = addressDao.get(0)

            if (address != null) {
                call.respond(HttpStatusCode.OK, address)
            } else {
                call.respond(HttpStatusCode.OK, Response(false, "AddressId is not correct."))
            }
        }

        post {
            val request = try {
                call.receive<AddressModel>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            if (addressDao.isIn(request)) {
                call.respond(HttpStatusCode.OK, Response(false, "Address already exists."))
            } else {
                addressDao.add(request)
                call.respond(HttpStatusCode.OK, Response(true, "Address added."))
            }
        }

        put {
            val request = try {
                call.receive<AddressModel>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@put
            }

            if (addressDao.isIn(request)) {
                addressDao.update(request)
                call.respond(HttpStatusCode.OK, Response(true, "Address updated."))
            } else {
                call.respond(HttpStatusCode.OK, Response(false, "Address does not exist."))
            }
        }

        delete {
            val request = try {
                call.receive<AddressModel>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@delete
            }

            if (addressDao.isIn(request)) {
                addressDao.remove(request)
                call.respond(HttpStatusCode.OK, Response(true, "Address removed"))
            } else {
                call.respond(HttpStatusCode.OK, Response(false, "Address was already removed"))
            }
        }
    }
}

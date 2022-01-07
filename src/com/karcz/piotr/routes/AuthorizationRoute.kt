package com.karcz.piotr.routes

import com.karcz.piotr.data.authentication.CustomerLoginModel
import com.karcz.piotr.data.authentication.CustomerRegistrationModel
import com.karcz.piotr.repository.dao.AddressDao
import com.karcz.piotr.repository.dao.AddressDaoImpl
import com.karcz.piotr.repository.dao.CustomerDao
import com.karcz.piotr.repository.dao.CustomerDaoImpl
import com.karcz.piotr.security.JWTService
import com.karcz.piotr.security.compareToHash
import com.karcz.piotr.transfer.data.Response
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.sql.SQLException

fun Route.authorizationRoute(jwtService: JWTService) {
    val addressDao: AddressDao = AddressDaoImpl()
    val customerDao: CustomerDao = CustomerDaoImpl()

    route("/login") {
        post {
            val customerLoginModel = try {
                call.receive<CustomerLoginModel>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val customerModel = customerDao.get(customerLoginModel.email)

            if (customerModel == null) {
                call.respond(HttpStatusCode.NotFound)
                return@post
            }

            if (!compareToHash(customerLoginModel.password, customerModel.password)) {
                call.respond(HttpStatusCode.Unauthorized)
                return@post
            }

            val token = jwtService.create(customerLoginModel.email)
            call.respond(HttpStatusCode.OK, hashMapOf("token" to token))
        }
    }

    route("/register") {
        post {
            val customerRegistrationModel = try {
                call.receive<CustomerRegistrationModel>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            if (customerDao.isIn(customerRegistrationModel.email)) {
                call.respond(HttpStatusCode.OK, Response(false, "This email address is already taken."))
            } else {
                try {
                    val addressId = addressDao.add(customerRegistrationModel.toAddressModel())
                    customerDao.add(customerRegistrationModel.toCustomerModel(addressId))
                } catch (e: SQLException) {
                    call.respond(HttpStatusCode.InternalServerError)
                }
                val token = jwtService.create(customerRegistrationModel.email)
                call.respond(HttpStatusCode.OK, hashMapOf("token" to token))
            }
        }
    }
}

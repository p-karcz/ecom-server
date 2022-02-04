package com.karcz.piotr.routes

import com.karcz.piotr.transfer.data.CustomerLoginTransferModel
import com.karcz.piotr.transfer.data.CustomerRegistrationTransferModel
import com.karcz.piotr.repository.dao.AddressDao
import com.karcz.piotr.repository.dao.AddressDaoImpl
import com.karcz.piotr.repository.dao.CustomerDao
import com.karcz.piotr.repository.dao.CustomerDaoImpl
import com.karcz.piotr.security.JWTService
import com.karcz.piotr.security.compareToHash
import com.karcz.piotr.transfer.data.Response
import com.karcz.piotr.transfer.data.TokenTransferModel
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
            val customerLoginTransferModel = try {
                call.receive<CustomerLoginTransferModel>()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val customerLoginDomainModel = customerLoginTransferModel.toDomain()
            if (customerLoginDomainModel == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val customerModel = customerDao.get(customerLoginDomainModel.email)

            if (customerModel == null) {
                call.respond(HttpStatusCode.NotFound)
                return@post
            }

            if (!compareToHash(customerLoginDomainModel.password, customerModel.password)) {
                call.respond(HttpStatusCode.Unauthorized)
                return@post
            }

            val token = jwtService.create(customerLoginDomainModel.email)
            call.respond(HttpStatusCode.OK, TokenTransferModel(token))
        }
    }

    route("/register") {
        post {
            val customerRegistrationTransferModel = try {
                call.receive<CustomerRegistrationTransferModel>()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val customerRegistrationDomainModel = customerRegistrationTransferModel.toDomainModel()
            if (customerRegistrationDomainModel == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            if (customerDao.isIn(customerRegistrationDomainModel.email)) {
                call.respond(HttpStatusCode.Conflict)
            } else {
                try {
                    val addressId = addressDao.add(customerRegistrationDomainModel.toAddressDomainModel())
                    customerDao.add(customerRegistrationDomainModel.toCustomerDomainModel(addressId))
                } catch (e: SQLException) {
                    call.respond(HttpStatusCode.InternalServerError)
                }
                val token = jwtService.create(customerRegistrationDomainModel.email)
                call.respond(HttpStatusCode.OK, TokenTransferModel(token))
            }
        }
    }
}

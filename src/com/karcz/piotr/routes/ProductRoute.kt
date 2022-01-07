package com.karcz.piotr.routes

import com.karcz.piotr.repository.dao.ProductDao
import com.karcz.piotr.repository.dao.ProductDaoImpl
import com.karcz.piotr.transfer.data.ProductsFilterModel
import com.karcz.piotr.transfer.data.Response
import com.karcz.piotr.transfer.qparameters.ProductsOrderByQueryParameter
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import java.lang.NumberFormatException

fun Route.productRoute() {
    val productDao: ProductDao = ProductDaoImpl()

    route("/products/{productId}") {
        get {
            call.parameters["productId"]?.let {
                try {
                    val productId = it.toInt()
                    val product = productDao.get(productId)
                    if (product != null) {
                        call.respond(HttpStatusCode.OK, product)
                    } else {
                        call.respond(HttpStatusCode.OK, Response(false, "Cannot find product with id: $productId."))
                    }
                } catch (e: NumberFormatException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            } ?: call.respond(HttpStatusCode.BadRequest)
        }
    }

    route("/products/{productId}/sizes") {
        get {
            call.parameters["productId"]?.let {
                try {
                    val productId = it.toInt()
                    val product = productDao.get(productId)
                    if (product != null) {
                        val sizes = productDao.getOtherSizesForProduct(product)
                        call.respond(HttpStatusCode.OK, sizes)
                    } else {
                        call.respond(HttpStatusCode.OK, Response(false, "Cannot find product with id: $productId."))
                    }
                } catch (e: NumberFormatException) {
                    call.respond(HttpStatusCode.BadRequest)
                }
            } ?: call.respond(HttpStatusCode.BadRequest)
        }
    }

    route("/products") {
        post {
            val orderByParameter = ProductsOrderByQueryParameter.process(call.request.queryParameters["orderBy"])
            val filter = try {
                call.receive<ProductsFilterModel>()
            } catch (e: ContentTransformationException) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }
            val products = productDao.get(filter, orderByParameter)
            call.respond(HttpStatusCode.OK, products)
        }

        get {
            val products = productDao.getAll()
            call.respond(HttpStatusCode.OK, products)
        }
    }

    route("/categories") {
        get {
            val categories = productDao.getAllCategories()
            call.respond(HttpStatusCode.OK, categories)
        }
    }

    route("/producers") {
        get {
            val producers = productDao.getAllProducers()
            call.respond(HttpStatusCode.OK, producers)
        }
    }

    route("/sizes") {
        get {
            val sizes = productDao.getAllSizes()
            call.respond(HttpStatusCode.OK, sizes)
        }
    }

    route("/colors") {
        get {
            val colors = productDao.getAllColors()
            call.respond(HttpStatusCode.OK, colors)
        }
    }
}

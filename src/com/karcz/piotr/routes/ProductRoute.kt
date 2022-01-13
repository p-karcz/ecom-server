package com.karcz.piotr.routes

import com.karcz.piotr.repository.dao.ProductDao
import com.karcz.piotr.repository.dao.ProductDaoImpl
import com.karcz.piotr.transfer.data.AllProductsTransferModel
import com.karcz.piotr.transfer.data.ProductTransferModel
import com.karcz.piotr.transfer.data.ProductsFilterTransferModel
import com.karcz.piotr.transfer.data.SingleListTransferModel
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
                        call.respond(HttpStatusCode.OK, product.toTransferModel())
                    } else {
                        call.respond(HttpStatusCode.OK, ProductTransferModel())
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
                        call.respond(HttpStatusCode.OK, AllProductsTransferModel(sizes.map { size -> size.toTransferModel() }))
                    } else {
                        call.respond(HttpStatusCode.OK, AllProductsTransferModel())
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
            val productsFilterTransferModel = try {
                call.receive<ProductsFilterTransferModel>()
            } catch (e: Exception) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val productsFilterDomainModel = productsFilterTransferModel.toDomain()
            if (productsFilterDomainModel == null) {
                call.respond(HttpStatusCode.BadRequest)
                return@post
            }

            val products = productDao.get(productsFilterDomainModel, orderByParameter)
            call.respond(HttpStatusCode.OK, AllProductsTransferModel(products.map { it.toTransferModel() }))
        }

        get {
            val products = productDao.getAllAvailable()
            call.respond(HttpStatusCode.OK, AllProductsTransferModel(products.map { it.toTransferModel() }))
        }
    }

    route("/categories") {
        get {
            val categories = productDao.getAllCategories()
            call.respond(HttpStatusCode.OK, SingleListTransferModel(categories))
        }
    }

    route("/producers") {
        get {
            val producers = productDao.getAllProducers()
            call.respond(HttpStatusCode.OK, SingleListTransferModel(producers))
        }
    }

    route("/sizes") {
        get {
            val sizes = productDao.getAllSizes()
            call.respond(HttpStatusCode.OK, SingleListTransferModel(sizes))
        }
    }

    route("/colors") {
        get {
            val colors = productDao.getAllColors()
            call.respond(HttpStatusCode.OK, SingleListTransferModel(colors))
        }
    }
}

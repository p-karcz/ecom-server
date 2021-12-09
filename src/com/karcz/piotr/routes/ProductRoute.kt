package com.karcz.piotr.routes

import com.karcz.piotr.repository.dao.CategoryDao
import com.karcz.piotr.repository.dao.CategoryDaoImpl
import com.karcz.piotr.repository.dao.ProductDao
import com.karcz.piotr.repository.dao.ProductDaoImpl
import com.karcz.piotr.transfer.Response
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import java.lang.NumberFormatException

fun Route.productRoute() {
    val productDao: ProductDao = ProductDaoImpl()
    val categoryDao: CategoryDao = CategoryDaoImpl()

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

    route("/products") {
        get {
            val products = productDao.getAll()
            call.respond(HttpStatusCode.OK, products)
        }
    }

    route("/products/categories") {
        get {
            val categories = categoryDao.getAll()
            call.respond(HttpStatusCode.OK, categories)
        }
    }

    route("/products/{categoryId}") {
        get {
            call.parameters["categoryName"]?.let {
                val category = productDao.getByCategory(it)
                call.respond(HttpStatusCode.OK, category)
            } ?: call.respond(HttpStatusCode.BadRequest)
        }
    }

    route("/{categoryId}") {
        get {
            call.parameters["categoryName"]?.let {
                val category = categoryDao.get(it)
                if (category != null) {
                    call.respond(HttpStatusCode.OK, category)
                } else {
                    call.respond(HttpStatusCode.OK, Response(false, "Cannot find product with id: $it."))
                }
            } ?: call.respond(HttpStatusCode.BadRequest)
        }
    }
}
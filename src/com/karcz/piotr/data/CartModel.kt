package com.karcz.piotr.data

import com.karcz.piotr.repository.tables.CartsDatabaseTable
import org.jetbrains.exposed.sql.ResultRow

data class CartModel(
    val customerEmail: String,
    val productId: Int,
    val quantity: Int
)

fun ResultRow.toCartModel() = CartModel(
    customerEmail = this[CartsDatabaseTable.customerEmail],
    productId = this[CartsDatabaseTable.productId],
    quantity = this[CartsDatabaseTable.quantity]
)

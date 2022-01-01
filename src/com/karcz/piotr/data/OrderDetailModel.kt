package com.karcz.piotr.data

import com.karcz.piotr.repository.tables.OrderDetailsDatabaseTable
import org.jetbrains.exposed.sql.ResultRow

data class OrderDetailModel(
    val orderId: Int,
    val productId: Int,
    val quantity: Int,
    val price: Double
)

fun ResultRow.toOrderDetailModel() = OrderDetailModel(
    orderId = this[OrderDetailsDatabaseTable.orderId],
    productId = this[OrderDetailsDatabaseTable.productId],
    quantity = this[OrderDetailsDatabaseTable.quantity],
    price = this[OrderDetailsDatabaseTable.price]
)

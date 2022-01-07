package com.karcz.piotr.data

import com.karcz.piotr.repository.tables.OrdersDatabaseTable
import org.jetbrains.exposed.sql.ResultRow

data class OrderModel(
    val id: Int,
    val customerEmail: String,
    val addressId: Int,
    val totalQuantity: Int,
    val totalPrice: Double,
    val date: String
) {
    companion object {
        const val DEFAULT_NOT_USED_ORDER_ID = -1
    }
}

fun ResultRow.toOrderModel() = OrderModel(
    id = this[OrdersDatabaseTable.id],
    customerEmail = this[OrdersDatabaseTable.customerEmail],
    addressId = this[OrdersDatabaseTable.addressId],
    totalQuantity = this[OrdersDatabaseTable.totalQuantity],
    totalPrice = this[OrdersDatabaseTable.totalPrice],
    date = this[OrdersDatabaseTable.date]
)

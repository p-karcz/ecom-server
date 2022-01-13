package com.karcz.piotr.domaindata

import com.karcz.piotr.repository.tables.OrdersDatabaseTable
import com.karcz.piotr.transfer.data.OrderTransferModel
import org.jetbrains.exposed.sql.ResultRow

data class OrderDomainModel(
    val id: Int,
    val customerEmail: String,
    val addressId: Int,
    val totalQuantity: Int,
    val totalPrice: Double,
    val date: String
) {

    fun toTransferModel() = OrderTransferModel(
        id = id,
        customerEmail = customerEmail,
        addressId = addressId,
        totalQuantity = totalQuantity,
        totalPrice = totalPrice,
        date = date
    )

    companion object {
        const val DEFAULT_NOT_USED_ORDER_ID = -1
    }
}

fun ResultRow.toOrderDomainModel() = OrderDomainModel(
    id = this[OrdersDatabaseTable.id],
    customerEmail = this[OrdersDatabaseTable.customerEmail],
    addressId = this[OrdersDatabaseTable.addressId],
    totalQuantity = this[OrdersDatabaseTable.totalQuantity],
    totalPrice = this[OrdersDatabaseTable.totalPrice],
    date = this[OrdersDatabaseTable.date]
)

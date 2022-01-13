package com.karcz.piotr.domaindata

import com.karcz.piotr.repository.tables.OrderDetailsDatabaseTable
import com.karcz.piotr.transfer.data.OrderDetailTransferModel
import org.jetbrains.exposed.sql.ResultRow

data class OrderDetailDomainModel(
    val orderId: Int,
    val productId: Int,
    val quantity: Int,
    val price: Double
) {

    fun toTransferModel() = OrderDetailTransferModel(
        orderId = orderId,
        productId = productId,
        quantity = quantity,
        price = price
    )
}

fun ResultRow.toOrderDetailDomainModel() = OrderDetailDomainModel(
    orderId = this[OrderDetailsDatabaseTable.orderId],
    productId = this[OrderDetailsDatabaseTable.productId],
    quantity = this[OrderDetailsDatabaseTable.quantity],
    price = this[OrderDetailsDatabaseTable.price]
)

package com.karcz.piotr.domaindata

import com.karcz.piotr.repository.tables.CartsDatabaseTable
import com.karcz.piotr.transfer.data.CartTransferModel
import org.jetbrains.exposed.sql.ResultRow

data class CartDomainModel(
    val customerEmail: String,
    val productId: Int,
    val quantity: Int
) {

    fun toTransferModel() = CartTransferModel(
        productId = productId,
        quantity = quantity
    )
}

fun ResultRow.toCartDomainModel() = CartDomainModel(
    customerEmail = this[CartsDatabaseTable.customerEmail],
    productId = this[CartsDatabaseTable.productId],
    quantity = this[CartsDatabaseTable.quantity]
)

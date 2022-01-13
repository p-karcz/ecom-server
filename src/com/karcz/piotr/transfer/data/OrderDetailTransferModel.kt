package com.karcz.piotr.transfer.data

import com.karcz.piotr.domaindata.OrderDetailDomainModel

data class OrderDetailTransferModel(
    val orderId: Int? = null,
    val productId: Int? = null,
    val quantity: Int? = null,
    val price: Double? = null
) {

    fun toDomainModel(): OrderDetailDomainModel? {
        return if (listOf(orderId, productId, quantity, price).any { it == null }) {
            null
        } else {
            OrderDetailDomainModel(
                orderId = orderId!!,
                productId = productId!!,
                quantity = quantity!!,
                price = price!!
            )
        }
    }
}

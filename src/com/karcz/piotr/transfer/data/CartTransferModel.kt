package com.karcz.piotr.transfer.data

import com.karcz.piotr.domaindata.CartDomainModel

data class CartTransferModel(
    val productId: Int? = null,
    val quantity: Int? = null
) {

    fun toDomainModel(customerEmail: String): CartDomainModel? {
        return if (listOf(customerEmail, productId, quantity).any { it == null }) {
            null
        } else {
            CartDomainModel(
                customerEmail = customerEmail,
                productId = productId!!,
                quantity = quantity!!
            )
        }
    }
}

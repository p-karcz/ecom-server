package com.karcz.piotr.transfer.data

import com.karcz.piotr.domaindata.OrderDomainModel

data class OrderTransferModel(
    val id: Int? = null,
    val customerEmail: String? = null,
    val addressId: Int? = null,
    val totalQuantity: Int? = null,
    val totalPrice: Double? = null,
    val date: String? = null
) {

    fun toDomainModel(): OrderDomainModel? {
        return if (listOf(id, customerEmail, addressId, totalQuantity, totalPrice, date).any { it == null }) {
            null
        } else {
            OrderDomainModel(
                id = id!!,
                customerEmail = customerEmail!!,
                addressId = addressId!!,
                totalQuantity = totalQuantity!!,
                totalPrice = totalPrice!!,
                date = date!!
            )
        }
    }
}

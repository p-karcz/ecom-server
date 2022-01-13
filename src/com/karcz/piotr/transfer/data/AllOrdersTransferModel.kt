package com.karcz.piotr.transfer.data

import com.karcz.piotr.domaindata.AllOrdersDomainModel

data class AllOrdersTransferModel(
    val orders: List<OrderTransferModel>? = null
) {

    fun toDomain(): AllOrdersDomainModel? {
        return if (orders == null) {
            null
        } else {
            AllOrdersDomainModel(orders = orders.mapNotNull { it.toDomainModel() })
        }
    }
}

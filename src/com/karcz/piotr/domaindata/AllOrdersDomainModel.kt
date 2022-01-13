package com.karcz.piotr.domaindata

import com.karcz.piotr.transfer.data.AllOrdersTransferModel

data class AllOrdersDomainModel(
    val orders: List<OrderDomainModel>
) {

    fun toTransferModel() = AllOrdersTransferModel(
        orders = orders.map { it.toTransferModel() }
    )
}

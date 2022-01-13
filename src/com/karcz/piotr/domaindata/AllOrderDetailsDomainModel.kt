package com.karcz.piotr.domaindata

import com.karcz.piotr.transfer.data.AllOrderDetailsTransferModel

data class AllOrderDetailsDomainModel(
    val orderDetailList: List<OrderDetailDomainModel>
) {

    fun toTransferModel() = AllOrderDetailsTransferModel(
        orderDetailList = orderDetailList.map { it.toTransferModel() }
    )
}

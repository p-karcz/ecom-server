package com.karcz.piotr.transfer.data

import com.karcz.piotr.domaindata.AllOrderDetailsDomainModel

data class AllOrderDetailsTransferModel(
    val orderDetailList: List<OrderDetailTransferModel>? = null
) {

    fun toDomainModel(): AllOrderDetailsDomainModel? {
        return if (orderDetailList == null) {
            null
        } else {
            AllOrderDetailsDomainModel(
                orderDetailList = orderDetailList.mapNotNull { it.toDomainModel() }
            )
        }
    }
}

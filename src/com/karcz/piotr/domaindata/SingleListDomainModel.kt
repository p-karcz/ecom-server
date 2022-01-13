package com.karcz.piotr.domaindata

import com.karcz.piotr.transfer.data.SingleListTransferModel

data class SingleListDomainModel<T>(
    val values: List<T>
) {

    fun toTransferModel() = SingleListTransferModel(values)
}

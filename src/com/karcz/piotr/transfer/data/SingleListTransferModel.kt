package com.karcz.piotr.transfer.data

import com.karcz.piotr.domaindata.SingleListDomainModel

data class SingleListTransferModel<T>(
    val values: List<T>? = null
) {

    fun toDomainModel(): SingleListDomainModel<T>? {
        return if (values == null) {
            null
        } else {
            SingleListDomainModel(values)
        }
    }
}

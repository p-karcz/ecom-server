package com.karcz.piotr.transfer.data

import com.karcz.piotr.domaindata.AllProductsDomainModel

data class AllProductsTransferModel(
    val products: List<ProductTransferModel>? = null
) {

    fun toDomainModel(): AllProductsDomainModel? {
        return if (products == null) {
            null
        } else {
            AllProductsDomainModel(
                products = products.mapNotNull { it.toDomainModel() }
            )
        }
    }
}

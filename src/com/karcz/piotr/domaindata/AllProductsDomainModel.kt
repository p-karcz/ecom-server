package com.karcz.piotr.domaindata

import com.karcz.piotr.transfer.data.AllProductsTransferModel

data class AllProductsDomainModel(
    val products: List<ProductDomainModel>
) {

    fun toTransferModel() = AllProductsTransferModel(
        products = products.map { it.toTransferModel() }
    )
}

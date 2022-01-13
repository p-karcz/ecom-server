package com.karcz.piotr.transfer.data

import com.karcz.piotr.domaindata.ProductsFilterDomainModel

data class ProductsFilterTransferModel(
    val categories: List<String>? = null,
    val priceFrom: Double? = null,
    val priceTo: Double? = null,
    val producers: List<String>? = null,
    val sizes: List<String>? = null,
    val colors: List<String>? = null
) {

    fun toDomain(): ProductsFilterDomainModel? {
        return if (listOf(categories, producers, sizes, colors).any { it == null }) {
            null
        } else {
            ProductsFilterDomainModel(
                categories = categories!!,
                priceFrom = priceFrom,
                priceTo = priceTo,
                producers = producers!!,
                sizes = sizes!!,
                colors = colors!!
            )
        }
    }
}

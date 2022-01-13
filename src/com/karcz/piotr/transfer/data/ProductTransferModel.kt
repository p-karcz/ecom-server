package com.karcz.piotr.transfer.data

import com.karcz.piotr.domaindata.ProductDomainModel

data class ProductTransferModel(
    val id: Int? = null,
    val name: String? = null,
    val price: Double? = null,
    val image: String? = null,
    val description: String? = null,
    val category: String? = null,
    val producer: String? = null,
    val size: String? = null,
    val color: String? = null,
    val popularity: Int? = null,
    val quantity: Int? = null,
    val productCode: Int? = null
) {

    fun toDomainModel(): ProductDomainModel? {
        return if (
            listOf(id, name, price, image, description, category, producer, size, color, popularity, quantity, productCode)
                .any { it == null }
        ) {
            null
        } else {
            ProductDomainModel(
                id = id!!,
                name = name!!,
                price = price!!,
                image = image!!,
                description = description!!,
                category = category!!,
                producer = producer!!,
                size = size!!,
                color = color!!,
                popularity = popularity!!,
                quantity = quantity!!,
                productCode = productCode!!
            )
        }
    }
}

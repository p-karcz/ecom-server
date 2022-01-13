package com.karcz.piotr.domaindata

import com.karcz.piotr.repository.tables.ProductsDatabaseTable
import com.karcz.piotr.transfer.data.ProductTransferModel
import org.jetbrains.exposed.sql.ResultRow

data class ProductDomainModel(
    val id: Int,
    val name: String,
    val price: Double,
    val image: String,
    val description: String,
    val category: String,
    val producer: String,
    val size: String,
    val color: String,
    val popularity: Int,
    val quantity: Int,
    val productCode: Int
) {

    fun toTransferModel() = ProductTransferModel(
        id = id,
        name = name,
        price = price,
        image = image,
        description = description,
        category = category,
        producer = producer,
        size = size,
        color = color,
        popularity = popularity,
        quantity = quantity,
        productCode = productCode
    )
}

fun ResultRow.toProductDomainModel() = ProductDomainModel(
    id = this[ProductsDatabaseTable.id],
    name = this[ProductsDatabaseTable.name],
    price = this[ProductsDatabaseTable.price],
    image = this[ProductsDatabaseTable.image],
    description = this[ProductsDatabaseTable.description],
    category = this[ProductsDatabaseTable.category],
    producer = this[ProductsDatabaseTable.producer],
    size = this[ProductsDatabaseTable.size],
    color = this[ProductsDatabaseTable.color],
    popularity = this[ProductsDatabaseTable.popularity],
    quantity = this[ProductsDatabaseTable.quantity],
    productCode = this[ProductsDatabaseTable.productCode]
)

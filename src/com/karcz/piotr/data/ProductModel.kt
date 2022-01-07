package com.karcz.piotr.data

import com.karcz.piotr.repository.tables.ProductsDatabaseTable
import org.jetbrains.exposed.sql.ResultRow

data class ProductModel(
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
)

fun ResultRow.toProductModel() = ProductModel(
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

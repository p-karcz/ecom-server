package com.karcz.piotr.mappers

import com.karcz.piotr.repository.resources.ProductResource
import com.karcz.piotr.repository.tables.ProductsDatabaseTable
import com.karcz.piotr.domain.ProductDomainModel
import org.jetbrains.exposed.sql.ResultRow

fun ProductResource.toDomain() = ProductDomainModel(
    name = this.name,
    price = this.price,
    image = this.image,
    description = this.description
)

fun ResultRow.toProductResource() = ProductResource(
    id = this[ProductsDatabaseTable.id],
    categoryName = this[ProductsDatabaseTable.categoryName],
    name = this[ProductsDatabaseTable.name],
    price = this[ProductsDatabaseTable.price],
    image = this[ProductsDatabaseTable.image],
    description = this[ProductsDatabaseTable.description]
)
package com.karcz.piotr.mappers

import com.karcz.piotr.domain.CartDomainModel
import com.karcz.piotr.repository.resources.CartResource
import com.karcz.piotr.repository.tables.CartsDatabaseTable
import org.jetbrains.exposed.sql.ResultRow

fun CartResource.toDomain() = CartDomainModel(
    productId = this.productId,
    quantity = this.quantity
)

fun ResultRow.toCartResource() = CartResource(
    customerEmail = this[CartsDatabaseTable.customerEmail],
    productId = this[CartsDatabaseTable.productId],
    quantity = this[CartsDatabaseTable.quantity]
)
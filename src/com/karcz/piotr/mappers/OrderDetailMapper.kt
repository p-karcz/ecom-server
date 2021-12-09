package com.karcz.piotr.mappers

import com.karcz.piotr.repository.resources.OrderDetailResource
import com.karcz.piotr.repository.tables.OrderDetailsDatabaseTable
import com.karcz.piotr.domain.OrderDetailDomainModel
import org.jetbrains.exposed.sql.ResultRow

fun OrderDetailResource.toDomain() = OrderDetailDomainModel(
    quantity = this.quantity,
    price = this.price
)

fun ResultRow.toOrderDetailResource() = OrderDetailResource(
    orderId = this[OrderDetailsDatabaseTable.orderId],
    productId = this[OrderDetailsDatabaseTable.productId],
    quantity = this[OrderDetailsDatabaseTable.quantity],
    price = this[OrderDetailsDatabaseTable.price]
)
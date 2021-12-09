package com.karcz.piotr.mappers

import com.karcz.piotr.repository.resources.OrderResource
import com.karcz.piotr.repository.tables.OrdersDatabaseTable
import com.karcz.piotr.domain.OrderDomainModel
import org.jetbrains.exposed.sql.ResultRow

fun OrderResource.toDomain() = OrderDomainModel(
    totalQuantity = this.totalQuantity,
    totalPrice = this.totalPrice,
    date = this.date
)

fun ResultRow.toOrderResource() = OrderResource(
    id = this[OrdersDatabaseTable.id],
    customerEmail = this[OrdersDatabaseTable.customerEmail],
    addressId = this[OrdersDatabaseTable.addressId],
    totalQuantity = this[OrdersDatabaseTable.totalQuantity],
    totalPrice = this[OrdersDatabaseTable.totalPrice],
    date = this[OrdersDatabaseTable.date]
)
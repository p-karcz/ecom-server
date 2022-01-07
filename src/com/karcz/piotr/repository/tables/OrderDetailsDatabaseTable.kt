package com.karcz.piotr.repository.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object OrderDetailsDatabaseTable : Table() {
    val orderId: Column<Int> = reference(
        name = "orderId",
        refColumn = OrdersDatabaseTable.id,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
    val productId: Column<Int> = reference(
        name = "productId",
        refColumn = ProductsDatabaseTable.id,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
    val quantity: Column<Int> = integer("quantity")
    val price: Column<Double> = double("price")

    override val primaryKey = PrimaryKey(orderId, productId)
}

package com.karcz.piotr.repository.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object OrderDetailsDatabaseTable : Table() {
    val orderId: Column<Int> = reference("orderId", OrdersDatabaseTable.id)
    val productId: Column<Int> = reference("productId", ProductsDatabaseTable.id)
    val quantity: Column<Int> = integer("quantity")
    val price: Column<Double> = double("price")

    override val primaryKey = PrimaryKey(orderId, productId)
}
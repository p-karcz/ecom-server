package com.karcz.piotr.database.data

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object OrderDetailsDatabaseTable : IntIdTable() {
    val orderId: Column<EntityID<Int>> = reference("orderId", OrdersDatabaseTable)
    val productId: Column<EntityID<Int>> = reference("productId", ProductsDatabaseTable)
    val quantity: Column<Int> = integer("quantity")
    val price: Column<Double> = double("price")
}

class OrderDetailDatabaseEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<OrderDetailDatabaseEntity>(OrderDetailsDatabaseTable)
    val orderId by OrderDatabaseEntity referrersOn OrderDetailsDatabaseTable.orderId
    val productIdTable by ProductDatabaseEntity referrersOn OrderDetailsDatabaseTable.productId
    val quantity by OrderDetailsDatabaseTable.quantity
    val price by OrderDetailsDatabaseTable.price
}
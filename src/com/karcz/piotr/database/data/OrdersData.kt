package com.karcz.piotr.database.data

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object OrdersDatabaseTable : IntIdTable() {
    val customerId: Column<EntityID<Int>> = reference("customerId", CustomersDatabaseTable)
    val addressId: Column<EntityID<Int>> = reference("addressId", AddressesDatabaseTable)
    val totalQuantity: Column<Int> = integer("totalQuantity")
    val totalPrice: Column<Double> = double("totalPrice")
    val date: Column<String> = varchar("date", 20)
}

class OrderDatabaseEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<OrderDatabaseEntity>(OrdersDatabaseTable)
    val customerId by CustomerDatabaseEntity referrersOn OrdersDatabaseTable.customerId
    val addressId by AddressDatabaseEntity referrersOn OrdersDatabaseTable.addressId
    val totalQuantity by OrdersDatabaseTable.totalQuantity
    val totalPrice by OrdersDatabaseTable.totalPrice
    val date by OrdersDatabaseTable.date
}
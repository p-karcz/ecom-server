package com.karcz.piotr.repository.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object OrdersDatabaseTable : Table() {
    val id: Column<Int> = integer("id").uniqueIndex().autoIncrement()
    val customerEmail: Column<String> = reference("customerEmail", CustomersDatabaseTable.email)
    val addressId: Column<Int> = reference("addressId", AddressesDatabaseTable.id)
    val totalQuantity: Column<Int> = integer("totalQuantity")
    val totalPrice: Column<Double> = double("totalPrice")
    val date: Column<String> = varchar("date", 20)

    override val primaryKey = PrimaryKey(id)
}
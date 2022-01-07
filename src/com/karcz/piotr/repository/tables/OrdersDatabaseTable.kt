package com.karcz.piotr.repository.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object OrdersDatabaseTable : Table() {
    val id: Column<Int> = integer("id").uniqueIndex().autoIncrement()
    val customerEmail: Column<String> = reference(
        name ="customerEmail",
        refColumn = CustomersDatabaseTable.email,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
    val addressId: Column<Int> = reference(
        name = "addressId",
        refColumn = AddressesDatabaseTable.id,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
    val totalQuantity: Column<Int> = integer("totalQuantity")
    val totalPrice: Column<Double> = double("totalPrice")
    val date: Column<String> = varchar("date", 20)

    override val primaryKey = PrimaryKey(id)
}

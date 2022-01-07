package com.karcz.piotr.repository.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.ReferenceOption
import org.jetbrains.exposed.sql.Table

object CartsDatabaseTable : Table() {
    val customerEmail: Column<String> = reference(
        name = "customerEmail",
        refColumn = CustomersDatabaseTable.email,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
    val productId: Column<Int> = reference(
        name = "productId",
        refColumn = ProductsDatabaseTable.id,
        onDelete = ReferenceOption.CASCADE,
        onUpdate = ReferenceOption.CASCADE
    )
    val quantity: Column<Int> = integer("quantity").check { it greater 0 }

    override val primaryKey = PrimaryKey(customerEmail, productId)
}

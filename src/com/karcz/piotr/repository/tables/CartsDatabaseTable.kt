package com.karcz.piotr.repository.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CartsDatabaseTable : Table() {
    val customerEmail: Column<String> = reference("customerEmail", CustomersDatabaseTable.email)
    val productId: Column<Int> = reference("productId", ProductsDatabaseTable.id)
    val quantity: Column<Int> = integer("quantity")

    override val primaryKey = PrimaryKey(customerEmail, productId)
}
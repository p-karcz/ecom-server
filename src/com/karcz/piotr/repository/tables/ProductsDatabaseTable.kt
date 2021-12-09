package com.karcz.piotr.repository.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ProductsDatabaseTable : Table() {
    val id: Column<Int> = integer("id").uniqueIndex().autoIncrement()
    val name: Column<String> = varchar("name", 50)
    val price: Column<Double> = double("price")
    val image: Column<String> = varchar("image", 100)
    val description: Column<String> = varchar("description", 300)
    val categoryName: Column<String> = reference("categoryName", CategoriesDatabaseTable.name)

    override val primaryKey = PrimaryKey(id)
}
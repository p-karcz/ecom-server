package com.karcz.piotr.repository.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object ProductsDatabaseTable : Table() {
    val id: Column<Int> = integer("id").uniqueIndex().autoIncrement()
    val name: Column<String> = varchar("name", 50)
    val price: Column<Double> = double("price")
    val image: Column<String> = varchar("image", 100)
    val category: Column<String> = varchar("category", 50)
    val description: Column<String> = varchar("description", 300)
    val producer: Column<String> = varchar("producer", 50)
    val size: Column<String> = varchar("size", 5)
    val color: Column<String> = varchar("color", 50)
    val popularity: Column<Int> = integer("popularity")

    override val primaryKey = PrimaryKey(id)
}

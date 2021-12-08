package com.karcz.piotr.database.data

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object ProductsDatabaseTable : IntIdTable() {
    val name: Column<String> = varchar("name", 50)
    val price: Column<Double> = double("price")
    val image: Column<String> = varchar("image", 100)
    val description: Column<String> = varchar("description", 300)
    val category: Column<Int> = integer("category")
}

class ProductDatabaseEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<ProductDatabaseEntity>(ProductsDatabaseTable)
    val name by ProductsDatabaseTable.name
    val price by ProductsDatabaseTable.price
    val image by ProductsDatabaseTable.image
    val description by ProductsDatabaseTable.description
    val category by ProductsDatabaseTable.category
}
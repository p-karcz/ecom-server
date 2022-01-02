package com.karcz.piotr.repository.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object AddressesDatabaseTable : Table() {
    val id: Column<Int> = integer("id").uniqueIndex().autoIncrement()
    val street: Column<String> = varchar("street", 30)
    val streetNumber: Column<Int> = integer("streetNumber")
    val flatNumber: Column<Int> = integer("flatNumber")
    val postalCode: Column<String> = varchar("postalCode", 10)
    val country: Column<String> = varchar("country", 20)
    val city: Column<String> = varchar("city", 20)

    override val primaryKey = PrimaryKey(id)
}

package com.karcz.piotr.database.data

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object AddressesDatabaseTable : IntIdTable() {
    val street: Column<String> = varchar("street", 30)
    val streetNumber: Column<Int> = integer("streetNumber")
    val flatNumber: Column<Int> = integer("flatNumber")
    val postalCode: Column<String> = varchar("postalCode", 10)
    val country: Column<String> = varchar("country", 20)
    val city: Column<String> = varchar("city", 20)
}

class AddressDatabaseEntity(id: EntityID<Int>): IntEntity(id) {
    companion object : IntEntityClass<AddressDatabaseEntity>(AddressesDatabaseTable)
    val street by AddressesDatabaseTable.street
    val streetNumber by AddressesDatabaseTable.streetNumber
    val flatNumber by AddressesDatabaseTable.flatNumber
    val postalCode by AddressesDatabaseTable.postalCode
    val country by AddressesDatabaseTable.country
    val city by AddressesDatabaseTable.city
}
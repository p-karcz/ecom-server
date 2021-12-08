package com.karcz.piotr.database.data

import org.jetbrains.exposed.dao.IntEntity
import org.jetbrains.exposed.dao.IntEntityClass
import org.jetbrains.exposed.dao.id.EntityID
import org.jetbrains.exposed.dao.id.IntIdTable
import org.jetbrains.exposed.sql.Column

object CustomersDatabaseTable : IntIdTable() {
    val addressId: Column<EntityID<Int>> = reference("addressId", AddressesDatabaseTable)
    val name: Column<String> = varchar("name", 20)
    val surname: Column<String> = varchar("surname", 20)
    val email: Column<String> = varchar("email", 30)
    val password: Column<String> = varchar("password", 400)
}

class CustomerDatabaseEntity(id: EntityID<Int>) : IntEntity(id) {
    companion object : IntEntityClass<CustomerDatabaseEntity>(CustomersDatabaseTable)
    val addressId by AddressDatabaseEntity referrersOn CustomersDatabaseTable.addressId
    val name by CustomersDatabaseTable.name
    val surname by CustomersDatabaseTable.surname
    val email by CustomersDatabaseTable.email
    val password by CustomersDatabaseTable.password
}
package com.karcz.piotr.repository.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CustomersDatabaseTable : Table() {
    val email: Column<String> = varchar("email", 30).uniqueIndex()
    val addressId: Column<Int?> = reference("id", AddressesDatabaseTable.id).nullable()
    val name: Column<String> = varchar("name", 20)
    val surname: Column<String> = varchar("surname", 20)
    val password: Column<String> = varchar("password", 400)

    override val primaryKey = PrimaryKey(email)
}
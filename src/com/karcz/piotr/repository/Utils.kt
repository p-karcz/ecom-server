package com.karcz.piotr.repository

import com.karcz.piotr.repository.tables.*
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

fun initDatabase(): org.jetbrains.exposed.sql.Database {
    val database = Database.db
    transaction {
        SchemaUtils.create(
            AddressesDatabaseTable,
            CustomersDatabaseTable,
            OrderDetailsDatabaseTable,
            OrdersDatabaseTable,
            ProductsDatabaseTable
        )
    }
    return database
}
package com.karcz.piotr.database

import com.karcz.piotr.database.data.*
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

    transaction {
        ProductsDatabaseTable.insert {
            it[name] = "dupa"
            it[price] = 45.0
            it[image] = "dupa"
            it[description] = "dupa"
            it[category] = 0
        }
    }

    return database
}
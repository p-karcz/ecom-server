package com.karcz.piotr.repository.tables

import org.jetbrains.exposed.sql.Column
import org.jetbrains.exposed.sql.Table

object CategoriesDatabaseTable : Table() {
    val name: Column<String> = varchar("name", 20).uniqueIndex()
    val description: Column<String> = varchar("description", 50)

    override val primaryKey = PrimaryKey(name)
}
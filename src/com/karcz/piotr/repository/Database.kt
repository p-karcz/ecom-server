package com.karcz.piotr.repository

import com.typesafe.config.ConfigFactory
import org.jetbrains.exposed.sql.Database

object Database {
    private val conf = ConfigFactory.load()
    val db by lazy {
        Database.connect(
            url = conf.getString("database.databaseUrl"),
            driver = conf.getString("database.databaseDriver"),
            user = conf.getString("database.databaseUser"),
            password = conf.getString("database.databasePassword")
        )
    }
}
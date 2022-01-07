package com.karcz.piotr.data

import com.karcz.piotr.repository.tables.CustomersDatabaseTable
import io.ktor.auth.*
import org.jetbrains.exposed.sql.ResultRow

data class CustomerModel(
    val email: String,
    val addressId: Int,
    val name: String,
    val surname: String,
    val password: String
)

fun ResultRow.toCustomerModel() = CustomerModel(
    email = this[CustomersDatabaseTable.email],
    addressId = this[CustomersDatabaseTable.addressId],
    name = this[CustomersDatabaseTable.name],
    surname = this[CustomersDatabaseTable.surname],
    password = this[CustomersDatabaseTable.password]
)

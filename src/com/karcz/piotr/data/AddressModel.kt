package com.karcz.piotr.data

import com.karcz.piotr.repository.tables.AddressesDatabaseTable
import org.jetbrains.exposed.sql.ResultRow

data class AddressModel(
    val id: Int?,
    val street: String,
    val streetNumber: Int,
    val flatNumber: Int,
    val postalCode: String,
    val country: String,
    val city: String
)

fun ResultRow.toAddressModel() = AddressModel(
    id = this[AddressesDatabaseTable.id],
    street = this[AddressesDatabaseTable.street],
    streetNumber = this[AddressesDatabaseTable.streetNumber],
    flatNumber = this[AddressesDatabaseTable.flatNumber],
    postalCode = this[AddressesDatabaseTable.postalCode],
    country = this[AddressesDatabaseTable.country],
    city = this[AddressesDatabaseTable.city]
)

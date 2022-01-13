package com.karcz.piotr.domaindata

import com.karcz.piotr.repository.tables.AddressesDatabaseTable
import com.karcz.piotr.transfer.data.AddressTransferModel
import org.jetbrains.exposed.sql.ResultRow

data class AddressDomainModel(
    val id: Int,
    val street: String,
    val streetNumber: Int,
    val flatNumber: Int,
    val postalCode: String,
    val country: String,
    val city: String
) {

    fun toTransferModel() = AddressTransferModel(
        id = id,
        street = street,
        streetNumber = streetNumber,
        flatNumber = flatNumber,
        postalCode = postalCode,
        country = country,
        city = city
    )
}

fun ResultRow.toAddressDomainModel() = AddressDomainModel(
    id = this[AddressesDatabaseTable.id],
    street = this[AddressesDatabaseTable.street],
    streetNumber = this[AddressesDatabaseTable.streetNumber],
    flatNumber = this[AddressesDatabaseTable.flatNumber],
    postalCode = this[AddressesDatabaseTable.postalCode],
    country = this[AddressesDatabaseTable.country],
    city = this[AddressesDatabaseTable.city]
)

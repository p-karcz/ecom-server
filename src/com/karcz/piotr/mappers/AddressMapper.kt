package com.karcz.piotr.mappers

import com.karcz.piotr.repository.resources.AddressResource
import com.karcz.piotr.repository.tables.AddressesDatabaseTable
import com.karcz.piotr.domain.AddressDomainModel
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.statements.InsertStatement

fun AddressResource.toDomain() = AddressDomainModel(
    street = this.street,
    streetNumber = this.streetNumber,
    flatNumber = this.flatNumber,
    postalCode = this.postalCode,
    country = this.country,
    city = this.city
)

fun ResultRow.toAddressResource() = AddressResource(
    id = this[AddressesDatabaseTable.id],
    street = this[AddressesDatabaseTable.street],
    streetNumber = this[AddressesDatabaseTable.streetNumber],
    flatNumber = this[AddressesDatabaseTable.flatNumber],
    postalCode = this[AddressesDatabaseTable.postalCode],
    country = this[AddressesDatabaseTable.country],
    city = this[AddressesDatabaseTable.city]
)
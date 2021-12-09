package com.karcz.piotr.mappers

import com.karcz.piotr.repository.resources.CustomerResource
import com.karcz.piotr.repository.tables.CustomersDatabaseTable
import com.karcz.piotr.domain.CustomerDomainModel
import org.jetbrains.exposed.sql.ResultRow

fun CustomerResource.toDomain() = CustomerDomainModel(
    name = this.name,
    surname = this.surname,
    email = this.email,
    password = this.password
)

fun ResultRow.toCustomerResource() = CustomerResource(
    email = this[CustomersDatabaseTable.email],
    addressId = this[CustomersDatabaseTable.addressId],
    name = this[CustomersDatabaseTable.name],
    surname = this[CustomersDatabaseTable.surname],
    password = this[CustomersDatabaseTable.password]
)
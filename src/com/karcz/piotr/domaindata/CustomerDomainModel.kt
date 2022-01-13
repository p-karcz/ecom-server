package com.karcz.piotr.domaindata

import com.karcz.piotr.repository.tables.CustomersDatabaseTable
import com.karcz.piotr.transfer.data.CustomerTransferModel
import org.jetbrains.exposed.sql.ResultRow

data class CustomerDomainModel(
    val email: String,
    val addressId: Int,
    val name: String,
    val surname: String,
    val password: String
) {

    fun toTransferModel() = CustomerTransferModel(
        email = email,
        addressId = addressId,
        name = name,
        surname = surname,
        password = password
    )
}

fun ResultRow.toCustomerDomainModel() = CustomerDomainModel(
    email = this[CustomersDatabaseTable.email],
    addressId = this[CustomersDatabaseTable.addressId],
    name = this[CustomersDatabaseTable.name],
    surname = this[CustomersDatabaseTable.surname],
    password = this[CustomersDatabaseTable.password]
)

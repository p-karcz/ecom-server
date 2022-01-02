package com.karcz.piotr.repository.dao

import com.karcz.piotr.data.AddressModel
import com.karcz.piotr.data.toAddressModel
import com.karcz.piotr.repository.tables.AddressesDatabaseTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class AddressDaoImpl : AddressDao {

    override fun isInOrFalse(address: AddressModel): Boolean {
        if (address.id == null) return false
        return transaction {
            (AddressesDatabaseTable.select { AddressesDatabaseTable.id eq address.id }.singleOrNull())
        } != null
    }

    override fun get(id: Int): AddressModel? {
        return transaction {
            AddressesDatabaseTable.select { AddressesDatabaseTable.id eq id }.singleOrNull()
        }?.toAddressModel()
    }

    override fun add(address: AddressModel): Int {
        return transaction {
            AddressesDatabaseTable.insert {
                it[street] = address.street
                it[streetNumber] = address.streetNumber
                it[flatNumber] = address.flatNumber
                it[postalCode] = address.postalCode
                it[country] = address.country
                it[city] = address.city
            }
        } get AddressesDatabaseTable.id
    }

    override fun update(address: AddressModel) {
        if (address.id == null) return
        transaction {
            AddressesDatabaseTable.update({ AddressesDatabaseTable.id eq address.id }) {
                it[street] = address.street
                it[streetNumber] = address.streetNumber
                it[flatNumber] = address.flatNumber
                it[postalCode] = address.postalCode
                it[country] = address.country
                it[city] = address.city
            }
        }
    }

    override fun remove(address: AddressModel) {
        if (address.id == null) return
        transaction {
            AddressesDatabaseTable.deleteWhere { AddressesDatabaseTable.id eq address.id }
        }
    }
}

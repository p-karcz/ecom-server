package com.karcz.piotr.repository.dao

import com.karcz.piotr.mappers.toAddressResource
import com.karcz.piotr.repository.resources.AddressResource
import com.karcz.piotr.repository.tables.AddressesDatabaseTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class AddressDaoImpl : AddressDao {

    override fun isIn(address: AddressResource): Boolean {
        return transaction {
            (AddressesDatabaseTable.select { AddressesDatabaseTable.id eq address.id }.singleOrNull())
        } != null
    }

    override fun get(id: Int): AddressResource? {
        return transaction {
            AddressesDatabaseTable.select { AddressesDatabaseTable.id eq id }.singleOrNull()
        }?.toAddressResource()
    }

    override fun add(address: AddressResource) {
        transaction {
            AddressesDatabaseTable.insert {
                it[id] = address.id
                it[street] = address.street
                it[streetNumber] = address.streetNumber
                it[flatNumber] = address.flatNumber
                it[postalCode] = address.postalCode
                it[country] = address.country
                it[city] = address.city
            }
        }
    }

    override fun update(address: AddressResource) {
        transaction {
            AddressesDatabaseTable.update({ AddressesDatabaseTable.id eq address.id }) {
                it[id] = address.id
                it[street] = address.street
                it[streetNumber] = address.streetNumber
                it[flatNumber] = address.flatNumber
                it[postalCode] = address.postalCode
                it[country] = address.country
                it[city] = address.city
            }
        }
    }

    override fun remove(address: AddressResource) {
        transaction {
            AddressesDatabaseTable.deleteWhere { AddressesDatabaseTable.id eq address.id }
        }
    }
}
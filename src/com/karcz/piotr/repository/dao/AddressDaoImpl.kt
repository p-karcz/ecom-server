package com.karcz.piotr.repository.dao

import com.karcz.piotr.data.AddressModel
import com.karcz.piotr.data.toAddressModel
import com.karcz.piotr.repository.tables.AddressesDatabaseTable
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class AddressDaoImpl : AddressDao {

    override fun isIn(address: AddressModel): Boolean {
        return transaction {
            (AddressesDatabaseTable.select { AddressesDatabaseTable.id eq address.id }.singleOrNull())
        } != null
    }

    override fun get(id: Int): AddressModel? {
        return transaction {
            AddressesDatabaseTable.select { AddressesDatabaseTable.id eq id }.singleOrNull()
        }?.toAddressModel()
    }

    override fun add(address: AddressModel) {
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

    override fun update(address: AddressModel) {
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

    override fun remove(address: AddressModel) {
        transaction {
            AddressesDatabaseTable.deleteWhere { AddressesDatabaseTable.id eq address.id }
        }
    }
}

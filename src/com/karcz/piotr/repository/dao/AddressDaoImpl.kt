package com.karcz.piotr.repository.dao

import com.karcz.piotr.data.AddressModel
import com.karcz.piotr.data.toAddressModel
import com.karcz.piotr.repository.tables.AddressesDatabaseTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class AddressDaoImpl : AddressDao {

    override fun isInOrFalse(addressModel: AddressModel): Boolean {
        return transaction {
            (AddressesDatabaseTable.select { AddressesDatabaseTable.id eq addressModel.id }.singleOrNull())
        } != null
    }

    override fun get(id: Int): AddressModel? {
        return transaction {
            AddressesDatabaseTable.select { AddressesDatabaseTable.id eq id }.singleOrNull()
        }?.toAddressModel()
    }

    override fun add(addressModel: AddressModel): Int {
        return transaction {
            AddressesDatabaseTable.insert {
                it[street] = addressModel.street
                it[streetNumber] = addressModel.streetNumber
                it[flatNumber] = addressModel.flatNumber
                it[postalCode] = addressModel.postalCode
                it[country] = addressModel.country
                it[city] = addressModel.city
            }
        } get AddressesDatabaseTable.id
    }

    override fun update(addressModel: AddressModel) {
        transaction {
            AddressesDatabaseTable.update({ AddressesDatabaseTable.id eq addressModel.id }) {
                it[street] = addressModel.street
                it[streetNumber] = addressModel.streetNumber
                it[flatNumber] = addressModel.flatNumber
                it[postalCode] = addressModel.postalCode
                it[country] = addressModel.country
                it[city] = addressModel.city
            }
        }
    }

    override fun remove(id: Int) {
        transaction {
            AddressesDatabaseTable.deleteWhere { AddressesDatabaseTable.id eq id }
        }
    }
}

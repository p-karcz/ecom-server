package com.karcz.piotr.repository.dao

import com.karcz.piotr.domaindata.AddressDomainModel
import com.karcz.piotr.domaindata.toAddressDomainModel
import com.karcz.piotr.repository.tables.AddressesDatabaseTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class AddressDaoImpl : AddressDao {

    override fun isInOrFalse(addressDomainModel: AddressDomainModel): Boolean {
        return transaction {
            (AddressesDatabaseTable.select { AddressesDatabaseTable.id eq addressDomainModel.id }.singleOrNull())
        } != null
    }

    override fun get(id: Int): AddressDomainModel? {
        return transaction {
            AddressesDatabaseTable.select { AddressesDatabaseTable.id eq id }.singleOrNull()
        }?.toAddressDomainModel()
    }

    override fun add(addressDomainModel: AddressDomainModel): Int {
        return transaction {
            AddressesDatabaseTable.insert {
                it[street] = addressDomainModel.street
                it[streetNumber] = addressDomainModel.streetNumber
                it[flatNumber] = addressDomainModel.flatNumber
                it[postalCode] = addressDomainModel.postalCode
                it[country] = addressDomainModel.country
                it[city] = addressDomainModel.city
            }
        } get AddressesDatabaseTable.id
    }

    override fun update(addressDomainModel: AddressDomainModel) {
        transaction {
            AddressesDatabaseTable.update({ AddressesDatabaseTable.id eq addressDomainModel.id }) {
                it[street] = addressDomainModel.street
                it[streetNumber] = addressDomainModel.streetNumber
                it[flatNumber] = addressDomainModel.flatNumber
                it[postalCode] = addressDomainModel.postalCode
                it[country] = addressDomainModel.country
                it[city] = addressDomainModel.city
            }
        }
    }

    override fun remove(id: Int) {
        transaction {
            AddressesDatabaseTable.deleteWhere { AddressesDatabaseTable.id eq id }
        }
    }
}

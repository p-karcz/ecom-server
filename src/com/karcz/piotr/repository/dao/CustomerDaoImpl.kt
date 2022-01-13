package com.karcz.piotr.repository.dao

import com.karcz.piotr.domaindata.CustomerDomainModel
import com.karcz.piotr.domaindata.toCustomerDomainModel
import com.karcz.piotr.repository.tables.CustomersDatabaseTable
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.jetbrains.exposed.sql.update

class CustomerDaoImpl : CustomerDao {

    override fun isIn(email: String): Boolean {
        return transaction {
            (CustomersDatabaseTable.select { CustomersDatabaseTable.email eq email }.singleOrNull())
        } != null
    }

    override fun get(email: String): CustomerDomainModel? {
        return transaction {
            CustomersDatabaseTable.select { CustomersDatabaseTable.email eq email}.singleOrNull()
        }?.toCustomerDomainModel()
    }

    override fun add(customerDomainModel: CustomerDomainModel) {
        transaction {
            CustomersDatabaseTable.insert {
                it[email] = customerDomainModel.email
                it[addressId] = customerDomainModel.addressId
                it[name] = customerDomainModel.name
                it[surname] = customerDomainModel.surname
                it[password] = customerDomainModel.password
            }
        }
    }

    override fun update(customerDomainModel: CustomerDomainModel) {
        transaction {
            CustomersDatabaseTable.update({ CustomersDatabaseTable.email eq customerDomainModel.email }) {
                it[email] = customerDomainModel.email
                it[addressId] = customerDomainModel.addressId
                it[name] = customerDomainModel.name
                it[surname] = customerDomainModel.surname
                it[password] = customerDomainModel.password
            }
        }
    }

    override fun remove(email: String) {
        transaction {
            CustomersDatabaseTable.deleteWhere { CustomersDatabaseTable.email eq email }
        }
    }
}

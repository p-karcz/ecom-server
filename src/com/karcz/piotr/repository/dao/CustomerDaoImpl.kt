package com.karcz.piotr.repository.dao

import com.karcz.piotr.data.CustomerModel
import com.karcz.piotr.data.toCustomerModel
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

    override fun get(email: String): CustomerModel? {
        return transaction {
            CustomersDatabaseTable.select { CustomersDatabaseTable.email eq email}.singleOrNull()
        }?.toCustomerModel()
    }

    override fun add(customerModel: CustomerModel) {
        transaction {
            CustomersDatabaseTable.insert {
                it[email] = customerModel.email
                it[addressId] = customerModel.addressId
                it[name] = customerModel.name
                it[surname] = customerModel.surname
                it[password] = customerModel.password
            }
        }
    }

    override fun update(customerModel: CustomerModel) {
        transaction {
            CustomersDatabaseTable.update({ CustomersDatabaseTable.email eq customerModel.email }) {
                it[email] = customerModel.email
                it[addressId] = customerModel.addressId
                it[name] = customerModel.name
                it[surname] = customerModel.surname
                it[password] = customerModel.password
            }
        }
    }

    override fun remove(email: String) {
        transaction {
            CustomersDatabaseTable.deleteWhere { CustomersDatabaseTable.email eq email }
        }
    }
}

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

    override fun add(customer: CustomerModel) {
        transaction {
            CustomersDatabaseTable.insert {
                it[email] = customer.email
                it[addressId] = customer.addressId
                it[name] = customer.name
                it[surname] = customer.surname
                it[password] = customer.password
            }
        }
    }

    override fun update(customer: CustomerModel) {
        transaction {
            CustomersDatabaseTable.update({ CustomersDatabaseTable.email eq customer.email }) {
                it[email] = customer.email
                it[addressId] = customer.addressId
                it[name] = customer.name
                it[surname] = customer.surname
                it[password] = customer.password
            }
        }
    }

    override fun remove(customer: CustomerModel) {
        transaction {
            CustomersDatabaseTable.deleteWhere { CustomersDatabaseTable.email eq customer.email }
        }
    }
}

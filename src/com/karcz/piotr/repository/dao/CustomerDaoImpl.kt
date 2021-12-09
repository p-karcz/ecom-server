package com.karcz.piotr.repository.dao

import com.karcz.piotr.mappers.toCustomerResource
import com.karcz.piotr.repository.resources.CustomerResource
import com.karcz.piotr.repository.tables.AddressesDatabaseTable
import com.karcz.piotr.repository.tables.CustomersDatabaseTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.transactions.transaction

class CustomerDaoImpl : CustomerDao {

    override fun isIn(customer: CustomerResource): Boolean {
        return transaction {
            (CustomersDatabaseTable.select { CustomersDatabaseTable.email eq customer.email }.singleOrNull())
        } != null
    }

    override fun get(email: String): CustomerResource? {
        return transaction {
            CustomersDatabaseTable.select { CustomersDatabaseTable.email eq email}.singleOrNull()
        }?.toCustomerResource()
    }

    override fun add(customer: CustomerResource) {
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

    override fun update(customer: CustomerResource) {
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

    override fun remove(customer: CustomerResource) {
        transaction {
            CustomersDatabaseTable.deleteWhere { CustomersDatabaseTable.email eq customer.email }
        }
    }
}
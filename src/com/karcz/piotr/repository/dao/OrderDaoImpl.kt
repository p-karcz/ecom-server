package com.karcz.piotr.repository.dao

import com.karcz.piotr.mappers.toOrderResource
import com.karcz.piotr.repository.resources.OrderResource
import com.karcz.piotr.repository.tables.OrdersDatabaseTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class OrderDaoImpl : OrderDao {

    override fun isIn(order: OrderResource): Boolean {
        return transaction {
            (OrdersDatabaseTable.select { OrdersDatabaseTable.id eq order.id }.singleOrNull())
        } != null
    }

    override fun get(id: Int): OrderResource? {
        return transaction {
            OrdersDatabaseTable.select { OrdersDatabaseTable.id eq id }.singleOrNull()
        }?.toOrderResource()
    }

    override fun getAllForCustomer(customerEmail: String): List<OrderResource> {
        return transaction {
            OrdersDatabaseTable.selectAll().toList()
        }.map { it.toOrderResource() }
    }

    override fun add(order: OrderResource) {
        transaction {
            OrdersDatabaseTable.insert {
                it[id] = order.id
                it[customerEmail] = order.customerEmail
                it[addressId] = order.addressId
                it[totalQuantity] = order.totalQuantity
                it[totalPrice] = order.totalPrice
                it[date] = order.date
            }
        }
    }
}
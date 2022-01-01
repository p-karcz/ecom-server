package com.karcz.piotr.repository.dao

import com.karcz.piotr.data.OrderModel
import com.karcz.piotr.data.toOrderModel
import com.karcz.piotr.repository.tables.OrdersDatabaseTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class OrderDaoImpl : OrderDao {

    override fun isIn(order: OrderModel): Boolean {
        return transaction {
            (OrdersDatabaseTable.select { OrdersDatabaseTable.id eq order.id }.singleOrNull())
        } != null
    }

    override fun get(id: Int): OrderModel? {
        return transaction {
            OrdersDatabaseTable.select { OrdersDatabaseTable.id eq id }.singleOrNull()
        }?.toOrderModel()
    }

    override fun getAllForCustomer(customerEmail: String): List<OrderModel> {
        return transaction {
            OrdersDatabaseTable.selectAll().toList()
        }.map { it.toOrderModel() }
    }

    override fun add(order: OrderModel) {
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

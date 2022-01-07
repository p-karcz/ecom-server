package com.karcz.piotr.repository.dao

import com.karcz.piotr.data.OrderModel
import com.karcz.piotr.data.toOrderModel
import com.karcz.piotr.repository.tables.OrdersDatabaseTable
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class OrderDaoImpl : OrderDao {

    override fun isIn(orderModel: OrderModel): Boolean {
        return transaction {
            (OrdersDatabaseTable.select { OrdersDatabaseTable.id eq orderModel.id }.singleOrNull())
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

    override fun add(orderModel: OrderModel) {
        transaction {
            OrdersDatabaseTable.insert {
                it[id] = orderModel.id
                it[customerEmail] = orderModel.customerEmail
                it[addressId] = orderModel.addressId
                it[totalQuantity] = orderModel.totalQuantity
                it[totalPrice] = orderModel.totalPrice
                it[date] = orderModel.date
            }
        }
    }

    override fun getAllAddressesIdsForCustomer(customerEmail: String): List<Int> {
        return transaction {
            OrdersDatabaseTable.select { OrdersDatabaseTable.customerEmail eq customerEmail }.toList()
        }.map { it[OrdersDatabaseTable.id] }
    }
}

package com.karcz.piotr.repository.dao

import com.karcz.piotr.data.OrderModel
import com.karcz.piotr.data.toOrderModel
import com.karcz.piotr.repository.tables.AddressesDatabaseTable
import com.karcz.piotr.repository.tables.OrdersDatabaseTable
import org.jetbrains.exposed.sql.deleteWhere
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class OrderDaoImpl : OrderDao {

    override fun get(id: Int): OrderModel? {
        return transaction {
            OrdersDatabaseTable.select { OrdersDatabaseTable.id eq id }.singleOrNull()
        }?.toOrderModel()
    }

    override fun getAllForCustomer(customerEmail: String): List<OrderModel> {
        return transaction {
            OrdersDatabaseTable.select { OrdersDatabaseTable.customerEmail eq customerEmail }.toList()
        }.map { it.toOrderModel() }
    }

    override fun add(orderModel: OrderModel): Int {
        return transaction {
            OrdersDatabaseTable.insert {
                it[customerEmail] = orderModel.customerEmail
                it[addressId] = orderModel.addressId
                it[totalQuantity] = orderModel.totalQuantity
                it[totalPrice] = orderModel.totalPrice
                it[date] = orderModel.date
            }
        } get OrdersDatabaseTable.id
    }

    override fun getAllAddressesIdsForCustomer(customerEmail: String): List<Int> {
        return transaction {
            OrdersDatabaseTable.select { OrdersDatabaseTable.customerEmail eq customerEmail }.toList()
        }.map { it[OrdersDatabaseTable.id] }
    }
}

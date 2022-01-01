package com.karcz.piotr.repository.dao

import com.karcz.piotr.data.OrderDetailModel
import com.karcz.piotr.data.toOrderDetailModel
import com.karcz.piotr.repository.tables.OrderDetailsDatabaseTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.transactions.transaction

class OrderDetailDaoImpl : OrderDetailDao {

    override fun isIn(orderDetails: OrderDetailModel): Boolean {
        return transaction { (OrderDetailsDatabaseTable.select {
            (OrderDetailsDatabaseTable.orderId eq orderDetails.orderId) and
                    (OrderDetailsDatabaseTable.productId eq orderDetails.productId)
        }.singleOrNull()) } != null
    }

    override fun getAll(orderId: Int): List<OrderDetailModel> {
        return transaction {
            OrderDetailsDatabaseTable.selectAll().toList()
        }.map { it.toOrderDetailModel() }
    }

    override fun get(orderId: Int, productId: Int): OrderDetailModel? {
        return transaction {
            OrderDetailsDatabaseTable.select {
                (OrderDetailsDatabaseTable.orderId eq orderId) and (OrderDetailsDatabaseTable.productId eq productId)
            }.singleOrNull()
        }?.toOrderDetailModel()
    }

    override fun add(orderDetails: OrderDetailModel) {
        transaction {
            OrderDetailsDatabaseTable.insert {
                it[orderId] = orderDetails.orderId
                it[productId] = orderDetails.productId
                it[quantity] = orderDetails.quantity
                it[price] = orderDetails.price
            }
        }
    }
}

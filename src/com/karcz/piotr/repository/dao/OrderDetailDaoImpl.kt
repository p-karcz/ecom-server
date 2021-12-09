package com.karcz.piotr.repository.dao

import com.karcz.piotr.mappers.toOrderDetailResource
import com.karcz.piotr.repository.resources.OrderDetailResource
import com.karcz.piotr.repository.tables.OrderDetailsDatabaseTable
import org.jetbrains.exposed.sql.and
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class OrderDetailDaoImpl : OrderDetailDao {

    override fun isIn(orderDetails: OrderDetailResource): Boolean {
        return transaction { (OrderDetailsDatabaseTable.select {
            (OrderDetailsDatabaseTable.orderId eq orderDetails.orderId) and
                    (OrderDetailsDatabaseTable.productId eq orderDetails.productId)
        }.singleOrNull()) } != null
    }

    override fun get(orderId: Int, productId: Int): OrderDetailResource? {
        return transaction {
            OrderDetailsDatabaseTable.select {
                (OrderDetailsDatabaseTable.orderId eq orderId) and (OrderDetailsDatabaseTable.productId eq productId)
            }.singleOrNull()
        }?.toOrderDetailResource()
    }

    override fun add(orderDetails: OrderDetailResource) {
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
package com.karcz.piotr.repository.dao

import com.karcz.piotr.data.OrderDetailModel
import com.karcz.piotr.data.toOrderDetailModel
import com.karcz.piotr.repository.tables.OrderDetailsDatabaseTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class OrderDetailDaoImpl : OrderDetailDao {

    override fun isIn(orderDetailModel: OrderDetailModel): Boolean {
        return transaction { (OrderDetailsDatabaseTable.select {
            (OrderDetailsDatabaseTable.orderId eq orderDetailModel.orderId) and
                    (OrderDetailsDatabaseTable.productId eq orderDetailModel.productId)
        }.singleOrNull()) } != null
    }

    override fun getAllForOrder(orderId: Int): List<OrderDetailModel> {
        return transaction {
            OrderDetailsDatabaseTable.select { OrderDetailsDatabaseTable.orderId eq orderId }.toList()
        }.map { it.toOrderDetailModel() }
    }

    override fun get(orderId: Int, productId: Int): OrderDetailModel? {
        return transaction {
            OrderDetailsDatabaseTable.select {
                (OrderDetailsDatabaseTable.orderId eq orderId) and (OrderDetailsDatabaseTable.productId eq productId)
            }.singleOrNull()
        }?.toOrderDetailModel()
    }

    override fun add(orderDetailModel: OrderDetailModel) {
        transaction {
            OrderDetailsDatabaseTable.insert {
                it[orderId] = orderDetailModel.orderId
                it[productId] = orderDetailModel.productId
                it[quantity] = orderDetailModel.quantity
                it[price] = orderDetailModel.price
            }
        }
    }
}

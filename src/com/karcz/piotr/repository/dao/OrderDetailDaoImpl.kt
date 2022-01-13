package com.karcz.piotr.repository.dao

import com.karcz.piotr.domaindata.OrderDetailDomainModel
import com.karcz.piotr.domaindata.toOrderDetailDomainModel
import com.karcz.piotr.repository.tables.OrderDetailsDatabaseTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class OrderDetailDaoImpl : OrderDetailDao {

    override fun isIn(orderDetailDomainModel: OrderDetailDomainModel): Boolean {
        return transaction { (OrderDetailsDatabaseTable.select {
            (OrderDetailsDatabaseTable.orderId eq orderDetailDomainModel.orderId) and
                    (OrderDetailsDatabaseTable.productId eq orderDetailDomainModel.productId)
        }.singleOrNull()) } != null
    }

    override fun getAllForOrder(orderId: Int): List<OrderDetailDomainModel> {
        return transaction {
            OrderDetailsDatabaseTable.select { OrderDetailsDatabaseTable.orderId eq orderId }.toList()
        }.map { it.toOrderDetailDomainModel() }
    }

    override fun get(orderId: Int, productId: Int): OrderDetailDomainModel? {
        return transaction {
            OrderDetailsDatabaseTable.select {
                (OrderDetailsDatabaseTable.orderId eq orderId) and (OrderDetailsDatabaseTable.productId eq productId)
            }.singleOrNull()
        }?.toOrderDetailDomainModel()
    }

    override fun add(orderDetailDomainModel: OrderDetailDomainModel) {
        transaction {
            OrderDetailsDatabaseTable.insert {
                it[orderId] = orderDetailDomainModel.orderId
                it[productId] = orderDetailDomainModel.productId
                it[quantity] = orderDetailDomainModel.quantity
                it[price] = orderDetailDomainModel.price
            }
        }
    }
}

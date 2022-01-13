package com.karcz.piotr.repository.dao

import com.karcz.piotr.domaindata.OrderDomainModel
import com.karcz.piotr.domaindata.toOrderDomainModel
import com.karcz.piotr.repository.tables.OrdersDatabaseTable
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction

class OrderDaoImpl : OrderDao {

    override fun get(id: Int): OrderDomainModel? {
        return transaction {
            OrdersDatabaseTable.select { OrdersDatabaseTable.id eq id }.singleOrNull()
        }?.toOrderDomainModel()
    }

    override fun getAllForCustomer(customerEmail: String): List<OrderDomainModel> {
        return transaction {
            OrdersDatabaseTable.select { OrdersDatabaseTable.customerEmail eq customerEmail }.toList()
        }.map { it.toOrderDomainModel() }
    }

    override fun add(orderDomainModel: OrderDomainModel): Int {
        return transaction {
            OrdersDatabaseTable.insert {
                it[customerEmail] = orderDomainModel.customerEmail
                it[addressId] = orderDomainModel.addressId
                it[totalQuantity] = orderDomainModel.totalQuantity
                it[totalPrice] = orderDomainModel.totalPrice
                it[date] = orderDomainModel.date
            }
        } get OrdersDatabaseTable.id
    }

    override fun getAllAddressesIdsForCustomer(customerEmail: String): List<Int> {
        return transaction {
            OrdersDatabaseTable.select { OrdersDatabaseTable.customerEmail eq customerEmail }.toList()
        }.map { it[OrdersDatabaseTable.id] }
    }
}

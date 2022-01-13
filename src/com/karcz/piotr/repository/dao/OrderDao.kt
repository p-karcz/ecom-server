package com.karcz.piotr.repository.dao

import com.karcz.piotr.domaindata.OrderDomainModel

interface OrderDao {
    fun get(id: Int): OrderDomainModel?
    fun getAllForCustomer(customerEmail: String): List<OrderDomainModel>
    fun add(orderDomainModel: OrderDomainModel): Int
    fun getAllAddressesIdsForCustomer(customerEmail: String): List<Int>
}

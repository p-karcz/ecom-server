package com.karcz.piotr.repository.dao

import com.karcz.piotr.data.OrderModel

interface OrderDao {
    fun isIn(order: OrderModel): Boolean
    fun get(id: Int): OrderModel?
    fun getAllForCustomer(customerEmail: String): List<OrderModel>
    fun add(order: OrderModel)
}

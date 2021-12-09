package com.karcz.piotr.repository.dao

import com.karcz.piotr.repository.resources.OrderResource

interface OrderDao {
    fun isIn(order: OrderResource): Boolean
    fun get(id: Int): OrderResource?
    fun getAllForCustomer(customerEmail: String): List<OrderResource>
    fun add(order: OrderResource)
}
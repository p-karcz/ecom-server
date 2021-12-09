package com.karcz.piotr.repository.dao

import com.karcz.piotr.repository.resources.OrderDetailResource

interface OrderDetailDao {
    fun isIn(orderDetails: OrderDetailResource): Boolean
    fun get(orderId: Int, productId: Int): OrderDetailResource?
    fun add(orderDetails: OrderDetailResource)
}
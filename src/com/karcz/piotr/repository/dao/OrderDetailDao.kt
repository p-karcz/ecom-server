package com.karcz.piotr.repository.dao

import com.karcz.piotr.data.OrderDetailModel

interface OrderDetailDao {
    fun isIn(orderDetails: OrderDetailModel): Boolean
    fun getAll(orderId: Int): List<OrderDetailModel>
    fun get(orderId: Int, productId: Int): OrderDetailModel?
    fun add(orderDetails: OrderDetailModel)
}

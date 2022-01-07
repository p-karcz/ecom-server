package com.karcz.piotr.repository.dao

import com.karcz.piotr.data.OrderDetailModel

interface OrderDetailDao {
    fun isIn(orderDetailModel: OrderDetailModel): Boolean
    fun getAllForOrder(orderId: Int): List<OrderDetailModel>
    fun get(orderId: Int, productId: Int): OrderDetailModel?
    fun add(orderDetailModel: OrderDetailModel)
}

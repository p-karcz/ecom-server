package com.karcz.piotr.repository.dao

import com.karcz.piotr.domaindata.OrderDetailDomainModel

interface OrderDetailDao {
    fun isIn(orderDetailDomainModel: OrderDetailDomainModel): Boolean
    fun getAllForOrder(orderId: Int): List<OrderDetailDomainModel>
    fun get(orderId: Int, productId: Int): OrderDetailDomainModel?
    fun add(orderDetailDomainModel: OrderDetailDomainModel)
}

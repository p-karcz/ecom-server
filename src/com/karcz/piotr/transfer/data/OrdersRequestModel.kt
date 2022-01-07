package com.karcz.piotr.transfer.data

import com.karcz.piotr.data.OrderDetailModel

data class OrdersRequestModel(
    val orderDetailList: List<OrderDetailModel>
)

package com.karcz.piotr.transfer.data

import com.karcz.piotr.data.OrderDetailModel
import com.karcz.piotr.data.ProductModel

data class OrderDetailsResponseModel(
    val orderDetailList: List<OrderDetailModel>,
    val productList: List<ProductModel>
)

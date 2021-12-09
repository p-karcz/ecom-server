package com.karcz.piotr.repository.resources

data class OrderDetailResource(
    val orderId: Int,
    val productId: Int,
    val quantity: Int,
    val price: Double
)

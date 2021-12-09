package com.karcz.piotr.repository.resources

data class OrderResource(
    val id: Int,
    val customerEmail: String,
    val addressId: Int,
    val totalQuantity: Int,
    val totalPrice: Double,
    val date: String
)

package com.karcz.piotr.repository.resources

data class CartResource(
    val customerEmail: String,
    val productId: Int,
    val quantity: Int
)
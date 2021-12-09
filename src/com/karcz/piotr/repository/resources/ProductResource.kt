package com.karcz.piotr.repository.resources

data class ProductResource(
    val id: Int,
    val categoryName: String,
    val name: String,
    val price: Double,
    val image: String,
    val description: String
)
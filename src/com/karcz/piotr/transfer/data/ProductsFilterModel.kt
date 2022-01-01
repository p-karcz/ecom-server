package com.karcz.piotr.transfer.data

data class ProductsFilterModel(
    val categories: List<String>,
    val priceFrom: Double,
    val priceTo: Double,
    val producers: List<String>,
    val sizes: List<String>,
    val colors: List<String>
)

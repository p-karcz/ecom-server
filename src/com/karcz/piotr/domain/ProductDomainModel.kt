package com.karcz.piotr.domain

data class ProductDomainModel(
    val name: String,
    val price: Double,
    val image: String,
    val description: String,
    val category: Category
) {
    enum class Category {
        CATEGORY1, CATEGORY2, CATEGORY3
    }
}

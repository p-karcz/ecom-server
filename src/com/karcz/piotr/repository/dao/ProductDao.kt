package com.karcz.piotr.repository.dao

import com.karcz.piotr.repository.resources.ProductResource

interface ProductDao {
    fun isIn(product: ProductResource): Boolean
    fun get(id: Int): ProductResource?
    fun getAll(): List<ProductResource>
    fun getByCategory(categoryName: String): List<ProductResource>
    fun add(product: ProductResource)
    fun update(product: ProductResource)
    fun remove(product: ProductResource)
}
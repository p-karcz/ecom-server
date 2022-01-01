package com.karcz.piotr.repository.dao

import com.karcz.piotr.data.ProductModel
import com.karcz.piotr.transfer.data.ProductsFilterModel
import com.karcz.piotr.transfer.qparameters.ProductsOrderByQueryParameter

interface ProductDao {
    fun isIn(product: ProductModel): Boolean
    fun get(id: Int): ProductModel?
    fun get(filter: ProductsFilterModel, orderBy: ProductsOrderByQueryParameter): List<ProductModel>
    fun getAll(): List<ProductModel>
    fun getAllCategories(): List<String>
    fun getAllProducers(): List<String>
    fun getAllSizes(): List<String>
    fun getAllColors(): List<String>
    fun add(product: ProductModel)
    fun update(product: ProductModel)
    fun remove(product: ProductModel)
}

package com.karcz.piotr.repository.dao

import com.karcz.piotr.domaindata.ProductDomainModel
import com.karcz.piotr.domaindata.ProductsFilterDomainModel
import com.karcz.piotr.transfer.qparameters.ProductsOrderByQueryParameter

interface ProductDao {
    fun isAvailable(productId: Int, quantity: Int): Boolean
    fun get(id: Int): ProductDomainModel?
    fun get(filter: ProductsFilterDomainModel, orderBy: ProductsOrderByQueryParameter): List<ProductDomainModel>
    fun getOtherSizesForProduct(productDomainModel: ProductDomainModel): List<ProductDomainModel>
    fun getAll(): List<ProductDomainModel>
    fun getAllAvailable(): List<ProductDomainModel>
    fun getAllCategories(): List<String>
    fun getAllProducers(): List<String>
    fun getAllSizes(): List<String>
    fun getAllColors(): List<String>
    fun add(productDomainModel: ProductDomainModel)
    fun update(productDomainModel: ProductDomainModel)
    fun updateQuantity(productId: Int, newQuantity: Int)
    fun remove(productDomainModel: ProductDomainModel)
}

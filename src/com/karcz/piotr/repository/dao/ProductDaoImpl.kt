package com.karcz.piotr.repository.dao

import com.karcz.piotr.domaindata.ProductDomainModel
import com.karcz.piotr.domaindata.ProductsFilterDomainModel
import com.karcz.piotr.domaindata.toProductDomainModel
import com.karcz.piotr.repository.tables.ProductsDatabaseTable
import com.karcz.piotr.transfer.qparameters.ProductsOrderByQueryParameter
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ProductDaoImpl : ProductDao {

    override fun isIn(productDomainModel: ProductDomainModel): Boolean {
        return transaction {
            ProductsDatabaseTable.select { ProductsDatabaseTable.id eq productDomainModel.id }.singleOrNull()
        } != null
    }

    override fun isAvailable(productId: Int, quantity: Int): Boolean {
        return transaction {
            ProductsDatabaseTable.select { ProductsDatabaseTable.id eq productId }.singleOrNull()
        }?.toProductDomainModel()?.quantity?.let { it >= quantity } ?: false
    }

    override fun get(id: Int): ProductDomainModel? {
        return transaction {
            ProductsDatabaseTable.select { ProductsDatabaseTable.id eq id }.singleOrNull()
        }?.toProductDomainModel()
    }

    override fun get(filter: ProductsFilterDomainModel, orderBy: ProductsOrderByQueryParameter): List<ProductDomainModel> {
        return transaction {
            ProductsDatabaseTable.select {
                (ProductsDatabaseTable.category inList filter.categories) and
                        (ProductsDatabaseTable.price.between(filter.priceFrom, filter.priceTo)) and
                        (ProductsDatabaseTable.producer inList filter.producers) and
                        (ProductsDatabaseTable.size inList filter.sizes) and
                        (ProductsDatabaseTable.color inList filter.colors)
            }
                .orderBy(orderBy.value)
                .toList()
        }.map { it.toProductDomainModel() }
    }

    override fun getOtherSizesForProduct(productDomainModel: ProductDomainModel): List<ProductDomainModel> {
        return transaction {
            ProductsDatabaseTable.select {
                (ProductsDatabaseTable.productCode eq productDomainModel.productCode) and
                        (ProductsDatabaseTable.color eq productDomainModel.color)
            }.toList()
        }.map { it.toProductDomainModel() }
    }

    override fun getAll(): List<ProductDomainModel> {
        return transaction {
            ProductsDatabaseTable
                .selectAll()
                .orderBy(ProductsDatabaseTable.popularity)
                .toList()
        }.map { it.toProductDomainModel() }
    }

    override fun getAllAvailable(): List<ProductDomainModel> {
        return transaction {
            ProductsDatabaseTable
                .select { ProductsDatabaseTable.quantity greater 0 }
                .orderBy(ProductsDatabaseTable.popularity)
                .toList()
        }.map { it.toProductDomainModel() }
    }

    override fun getAllCategories(): List<String> {
        return getProductParameterDistinctValues(ProductsDatabaseTable.category)
    }

    override fun getAllProducers(): List<String> {
        return getProductParameterDistinctValues(ProductsDatabaseTable.producer)
    }

    override fun getAllSizes(): List<String> {
        return getProductParameterDistinctValues(ProductsDatabaseTable.size)
    }

    override fun getAllColors(): List<String> {
        return getProductParameterDistinctValues(ProductsDatabaseTable.color)
    }

    override fun add(productDomainModel: ProductDomainModel) {
        transaction {
            ProductsDatabaseTable.insert {
                it[name] = productDomainModel.name
                it[price] = productDomainModel.price
                it[image] = productDomainModel.image
                it[description] = productDomainModel.description
                it[category] = productDomainModel.category
                it[producer] = productDomainModel.producer
                it[size] = productDomainModel.size
                it[color] = productDomainModel.color
                it[popularity] = productDomainModel.popularity
                it[quantity] = productDomainModel.quantity
                it[productCode] = productDomainModel.productCode
            }
        }
    }

    override fun update(productDomainModel: ProductDomainModel) {
        transaction {
            ProductsDatabaseTable.update({ ProductsDatabaseTable.id eq productDomainModel.id }) {
                it[name] = productDomainModel.name
                it[price] = productDomainModel.price
                it[image] = productDomainModel.image
                it[description] = productDomainModel.description
                it[category] = productDomainModel.category
                it[producer] = productDomainModel.producer
                it[size] = productDomainModel.size
                it[color] = productDomainModel.color
                it[popularity] = productDomainModel.popularity
                it[quantity] = productDomainModel.quantity
                it[productCode] = productDomainModel.productCode
            }
        }
    }

    override fun updateQuantity(productId: Int, newQuantity: Int) {
        transaction {
            ProductsDatabaseTable.update({ ProductsDatabaseTable.id eq productId }) {
                it[quantity] = newQuantity
            }
        }
    }

    override fun remove(productDomainModel: ProductDomainModel) {
        transaction {
            ProductsDatabaseTable.deleteWhere {
                ProductsDatabaseTable.id eq productDomainModel.id
            }
        }
    }

    private fun <T> getProductParameterDistinctValues(column: Column<T>): List<T> {
        return transaction {
            ProductsDatabaseTable.slice(column)
                .selectAll()
                .withDistinct()
                .toList()
        }.map { it[column] }
    }
}

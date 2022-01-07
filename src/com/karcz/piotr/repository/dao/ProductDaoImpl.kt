package com.karcz.piotr.repository.dao

import com.karcz.piotr.data.ProductModel
import com.karcz.piotr.data.toProductModel
import com.karcz.piotr.repository.tables.ProductsDatabaseTable
import com.karcz.piotr.transfer.data.ProductsFilterModel
import com.karcz.piotr.transfer.qparameters.ProductsOrderByQueryParameter
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ProductDaoImpl : ProductDao {

    override fun isIn(productModel: ProductModel): Boolean {
        return transaction {
            ProductsDatabaseTable.select { ProductsDatabaseTable.id eq productModel.id }.singleOrNull()
        } != null
    }

    override fun get(id: Int): ProductModel? {
        return transaction {
            ProductsDatabaseTable.select { ProductsDatabaseTable.id eq id }.singleOrNull()
        }?.toProductModel()
    }

    override fun get(filter: ProductsFilterModel, orderBy: ProductsOrderByQueryParameter): List<ProductModel> {
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
        }.map { it.toProductModel() }
    }

    override fun getOtherSizesForProduct(productModel: ProductModel): List<ProductModel> {
        return transaction {
            ProductsDatabaseTable.select {
                (ProductsDatabaseTable.productCode eq productModel.productCode) and
                        (ProductsDatabaseTable.color eq productModel.color)
            }.toList()
        }.map { it.toProductModel() }
    }

    override fun getAll(): List<ProductModel> {
        return transaction {
            ProductsDatabaseTable
                .selectAll()
                .orderBy(ProductsDatabaseTable.popularity)
                .toList()
        }.map { it.toProductModel() }
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

    override fun add(productModel: ProductModel) {
        transaction {
            ProductsDatabaseTable.insert {
                it[name] = productModel.name
                it[price] = productModel.price
                it[image] = productModel.image
                it[description] = productModel.description
                it[category] = productModel.category
                it[producer] = productModel.producer
                it[size] = productModel.size
                it[color] = productModel.color
                it[popularity] = productModel.popularity
                it[quantity] = productModel.quantity
                it[productCode] = productModel.productCode
            }
        }
    }

    override fun update(productModel: ProductModel) {
        transaction {
            ProductsDatabaseTable.update({ ProductsDatabaseTable.id eq productModel.id }) {
                it[name] = productModel.name
                it[price] = productModel.price
                it[image] = productModel.image
                it[description] = productModel.description
                it[category] = productModel.category
                it[producer] = productModel.producer
                it[size] = productModel.size
                it[color] = productModel.color
                it[popularity] = productModel.popularity
                it[quantity] = productModel.quantity
                it[productCode] = productModel.productCode
            }
        }
    }

    override fun remove(productModel: ProductModel) {
        transaction {
            ProductsDatabaseTable.deleteWhere {
                ProductsDatabaseTable.id eq productModel.id
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

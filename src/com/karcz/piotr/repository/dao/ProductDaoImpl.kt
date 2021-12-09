package com.karcz.piotr.repository.dao

import com.karcz.piotr.mappers.toProductResource
import com.karcz.piotr.repository.resources.ProductResource
import com.karcz.piotr.repository.tables.ProductsDatabaseTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class ProductDaoImpl : ProductDao {

    override fun isIn(product: ProductResource): Boolean {
        return transaction {
            ProductsDatabaseTable.select { ProductsDatabaseTable.id eq product.id }.singleOrNull()
        } != null
    }

    override fun get(id: Int): ProductResource? {
        return transaction {
            ProductsDatabaseTable.select { ProductsDatabaseTable.id eq id }.singleOrNull()
        }?.toProductResource()
    }

    override fun getAll(): List<ProductResource> {
        return transaction {
            ProductsDatabaseTable.selectAll().toList()
        }.map { it.toProductResource() }
    }

    override fun getByCategory(categoryName: String): List<ProductResource> {
        return transaction {
            ProductsDatabaseTable.select { ProductsDatabaseTable.categoryName eq categoryName }.toList()
        }.map { it.toProductResource() }
    }

    override fun add(product: ProductResource) {
        transaction {
            ProductsDatabaseTable.insert {
                it[id] = product.id
                it[name] = product.name
                it[price] = product.price
                it[image] = product.image
                it[description] = product.description
                it[categoryName] = product.categoryName
            }
        }
    }

    override fun update(product: ProductResource) {
        transaction {
            ProductsDatabaseTable.update({ ProductsDatabaseTable.id eq product.id }) {
                it[id] = product.id
                it[name] = product.name
                it[price] = product.price
                it[image] = product.image
                it[description] = product.description
                it[categoryName] = product.categoryName
            }
        }
    }

    override fun remove(product: ProductResource) {
        transaction {
            ProductsDatabaseTable.deleteWhere {
                ProductsDatabaseTable.id eq product.id
            }
        }
    }
}
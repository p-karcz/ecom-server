package com.karcz.piotr.repository.dao

import com.karcz.piotr.mappers.toCategoryResource
import com.karcz.piotr.repository.resources.CategoryResource
import com.karcz.piotr.repository.tables.CategoriesDatabaseTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class CategoryDaoImpl : CategoryDao {

    override fun isIn(category: CategoryResource): Boolean {
        return transaction {
            (CategoriesDatabaseTable.select { CategoriesDatabaseTable.name eq category.name }.singleOrNull())
        } != null
    }

    override fun get(name: String): CategoryResource? {
        return transaction {
            CategoriesDatabaseTable.select { CategoriesDatabaseTable.name eq name }.singleOrNull()
        }?.toCategoryResource()
    }

    override fun getAll(): List<CategoryResource> {
        return transaction {
            CategoriesDatabaseTable.selectAll().toList()
        }.map { it.toCategoryResource() }
    }

    override fun add(category: CategoryResource) {
        transaction {
            CategoriesDatabaseTable.insert {
                it[name] = category.name
                it[description] = category.description
            }
        }
    }

    override fun update(category: CategoryResource) {
        transaction {
            CategoriesDatabaseTable.update({ CategoriesDatabaseTable.name eq category.name }) {
                it[name] = category.name
                it[description] = category.description
            }
        }
    }

    override fun remove(category: CategoryResource) {
        transaction {
            CategoriesDatabaseTable.deleteWhere { CategoriesDatabaseTable.name eq category.name }
        }
    }
}
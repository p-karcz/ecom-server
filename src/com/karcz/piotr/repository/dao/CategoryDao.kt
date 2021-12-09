package com.karcz.piotr.repository.dao

import com.karcz.piotr.repository.resources.CategoryResource

interface CategoryDao {
    fun isIn(category: CategoryResource): Boolean
    fun get(name: String): CategoryResource?
    fun getAll(): List<CategoryResource>
    fun add(category: CategoryResource)
    fun update(category: CategoryResource)
    fun remove(category: CategoryResource)
}
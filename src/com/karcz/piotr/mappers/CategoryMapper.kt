package com.karcz.piotr.mappers

import com.karcz.piotr.repository.resources.CategoryResource
import com.karcz.piotr.repository.tables.CategoriesDatabaseTable
import com.karcz.piotr.domain.CategoryDomainModel
import org.jetbrains.exposed.sql.ResultRow

fun CategoryResource.toDomain() = CategoryDomainModel(
    name = this.name,
    description = this.description
)

fun ResultRow.toCategoryResource() = CategoryResource(
    name = this[CategoriesDatabaseTable.name],
    description = this[CategoriesDatabaseTable.description]
)
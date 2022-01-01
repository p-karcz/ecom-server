package com.karcz.piotr.transfer.qparameters

import com.karcz.piotr.repository.tables.ProductsDatabaseTable
import org.jetbrains.exposed.sql.Expression
import org.jetbrains.exposed.sql.SortOrder

sealed class ProductsOrderByQueryParameter(val value: Pair<Expression<*>, SortOrder>) {

    object PriceAsc : ProductsOrderByQueryParameter(ProductsDatabaseTable.price to SortOrder.ASC)
    object PriceDesc : ProductsOrderByQueryParameter(ProductsDatabaseTable.price to SortOrder.DESC)
    object Alphabetic : ProductsOrderByQueryParameter(ProductsDatabaseTable.name to SortOrder.ASC)
    object PopularityAsc : ProductsOrderByQueryParameter(ProductsDatabaseTable.popularity to SortOrder.ASC)
    object PopularityDesc : ProductsOrderByQueryParameter(ProductsDatabaseTable.popularity to SortOrder.DESC)

    companion object {
        fun process(value: String?): ProductsOrderByQueryParameter {
            return when(value) {
                "price_desc" -> PriceDesc
                "alphabetic" -> Alphabetic
                "popularity_asc" -> PopularityAsc
                "popularity_desc" -> PopularityDesc
                else -> PriceAsc
            }
        }
    }
}

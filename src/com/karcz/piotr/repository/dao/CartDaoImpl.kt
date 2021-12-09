package com.karcz.piotr.repository.dao

import com.karcz.piotr.mappers.toCartResource
import com.karcz.piotr.repository.resources.CartResource
import com.karcz.piotr.repository.tables.CartsDatabaseTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class CartDaoImpl : CartDao {

    override fun isIn(cart: CartResource): Boolean {
        return transaction { (CartsDatabaseTable.select {
            (CartsDatabaseTable.customerEmail eq cart.customerEmail) and
                    (CartsDatabaseTable.productId eq cart.productId)
        }.singleOrNull()) } != null
    }

    override fun getAllForClient(clientEmail: String): List<CartResource> {
        return transaction {
            CartsDatabaseTable.select { CartsDatabaseTable.customerEmail eq clientEmail }.toList()
        }.map { it.toCartResource() }
    }

    override fun get(customerEmail: String, productId: Int): CartResource? {
        return transaction {
            CartsDatabaseTable.select {
                (CartsDatabaseTable.customerEmail eq customerEmail) and (CartsDatabaseTable.productId eq productId)
            }.singleOrNull()
        }?.toCartResource()
    }

    override fun add(cart: CartResource) {
        transaction {
            CartsDatabaseTable.insert {
                it[customerEmail] = cart.customerEmail
                it[productId] = cart.productId
                it[quantity] = cart.quantity
            }
        }
    }

    override fun update(cart: CartResource) {
        transaction {
            CartsDatabaseTable.update({
                (CartsDatabaseTable.customerEmail eq cart.customerEmail) and
                        (CartsDatabaseTable.productId eq cart.productId)
            }) {
                it[customerEmail] = cart.customerEmail
                it[productId] = cart.productId
                it[quantity] = cart.quantity
            }
        }
    }

    override fun remove(cart: CartResource) {
        transaction {
            CartsDatabaseTable.deleteWhere {
                (CartsDatabaseTable.customerEmail eq cart.customerEmail) and
                        (CartsDatabaseTable.productId eq cart.productId)
            }
        }
    }
}
package com.karcz.piotr.repository.dao

import com.karcz.piotr.data.CartModel
import com.karcz.piotr.data.toCartModel
import com.karcz.piotr.repository.tables.CartsDatabaseTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class CartDaoImpl : CartDao {

    override fun isIn(cart: CartModel): Boolean {
        return transaction { (CartsDatabaseTable.select {
            (CartsDatabaseTable.customerEmail eq cart.customerEmail) and
                    (CartsDatabaseTable.productId eq cart.productId)
        }.singleOrNull()) } != null
    }

    override fun getAllForClient(clientEmail: String): List<CartModel> {
        return transaction {
            CartsDatabaseTable.select { CartsDatabaseTable.customerEmail eq clientEmail }.toList()
        }.map { it.toCartModel() }
    }

    override fun get(customerEmail: String, productId: Int): CartModel? {
        return transaction {
            CartsDatabaseTable.select {
                (CartsDatabaseTable.customerEmail eq customerEmail) and (CartsDatabaseTable.productId eq productId)
            }.singleOrNull()
        }?.toCartModel()
    }

    override fun add(cart: CartModel) {
        transaction {
            CartsDatabaseTable.insert {
                it[customerEmail] = cart.customerEmail
                it[productId] = cart.productId
                it[quantity] = cart.quantity
            }
        }
    }

    override fun update(cart: CartModel) {
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

    override fun remove(cart: CartModel) {
        transaction {
            CartsDatabaseTable.deleteWhere {
                (CartsDatabaseTable.customerEmail eq cart.customerEmail) and
                        (CartsDatabaseTable.productId eq cart.productId)
            }
        }
    }
}

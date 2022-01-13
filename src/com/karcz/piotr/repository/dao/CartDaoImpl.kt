package com.karcz.piotr.repository.dao

import com.karcz.piotr.domaindata.CartDomainModel
import com.karcz.piotr.domaindata.toCartDomainModel
import com.karcz.piotr.repository.tables.CartsDatabaseTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction

class CartDaoImpl : CartDao {

    override fun isIn(cartDomainModel: CartDomainModel): Boolean {
        return transaction { (CartsDatabaseTable.select {
            (CartsDatabaseTable.customerEmail eq cartDomainModel.customerEmail) and
                    (CartsDatabaseTable.productId eq cartDomainModel.productId)
        }.singleOrNull()) } != null
    }

    override fun getAllForClient(clientEmail: String): List<CartDomainModel> {
        return transaction {
            CartsDatabaseTable.select { CartsDatabaseTable.customerEmail eq clientEmail }.toList()
        }.map { it.toCartDomainModel() }
    }

    override fun get(customerEmail: String, productId: Int): CartDomainModel? {
        return transaction {
            CartsDatabaseTable.select {
                (CartsDatabaseTable.customerEmail eq customerEmail) and (CartsDatabaseTable.productId eq productId)
            }.singleOrNull()
        }?.toCartDomainModel()
    }

    override fun add(cart: CartDomainModel) {
        transaction {
            CartsDatabaseTable.insert {
                it[customerEmail] = cart.customerEmail
                it[productId] = cart.productId
                it[quantity] = cart.quantity
            }
        }
    }

    override fun update(cartDomainModel: CartDomainModel) {
        transaction {
            CartsDatabaseTable.update({
                (CartsDatabaseTable.customerEmail eq cartDomainModel.customerEmail) and
                        (CartsDatabaseTable.productId eq cartDomainModel.productId)
            }) {
                it[quantity] = cartDomainModel.quantity
            }
        }
    }

    override fun remove(cartDomainModel: CartDomainModel) {
        transaction {
            CartsDatabaseTable.deleteWhere {
                (CartsDatabaseTable.customerEmail eq cartDomainModel.customerEmail) and
                        (CartsDatabaseTable.productId eq cartDomainModel.productId)
            }
        }
    }

    override fun removeAllForCustomer(customerEmail: String) {
        transaction {
            CartsDatabaseTable.deleteWhere { CartsDatabaseTable.customerEmail eq customerEmail }
        }
    }
}

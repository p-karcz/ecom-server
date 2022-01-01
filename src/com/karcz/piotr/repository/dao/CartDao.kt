package com.karcz.piotr.repository.dao

import com.karcz.piotr.data.CartModel

interface CartDao {
    fun isIn(cart: CartModel): Boolean
    fun get(customerEmail: String, productId: Int): CartModel?
    fun getAllForClient(clientEmail: String): List<CartModel>
    fun add(cart: CartModel)
    fun update(cart: CartModel)
    fun remove(cart: CartModel)
}

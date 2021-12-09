package com.karcz.piotr.repository.dao

import com.karcz.piotr.repository.resources.CartResource

interface CartDao {
    fun isIn(cart: CartResource): Boolean
    fun get(customerEmail: String, productId: Int): CartResource?
    fun getAllForClient(clientEmail: String): List<CartResource>
    fun add(cart: CartResource)
    fun update(cart: CartResource)
    fun remove(cart: CartResource)
}
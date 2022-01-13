package com.karcz.piotr.repository.dao

import com.karcz.piotr.domaindata.CartDomainModel

interface CartDao {
    fun isIn(cartDomainModel: CartDomainModel): Boolean
    fun get(customerEmail: String, productId: Int): CartDomainModel?
    fun getAllForClient(clientEmail: String): List<CartDomainModel>
    fun add(cart: CartDomainModel)
    fun update(cartDomainModel: CartDomainModel)
    fun remove(cartDomainModel: CartDomainModel)
    fun removeAllForCustomer(customerEmail: String)
}

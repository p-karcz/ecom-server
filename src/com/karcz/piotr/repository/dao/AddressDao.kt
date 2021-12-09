package com.karcz.piotr.repository.dao

import com.karcz.piotr.repository.resources.AddressResource

interface AddressDao {
    fun isIn(address: AddressResource): Boolean
    fun get(id: Int): AddressResource?
    fun add(address: AddressResource)
    fun update(address: AddressResource)
    fun remove(address: AddressResource)
}
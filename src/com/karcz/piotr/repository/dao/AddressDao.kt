package com.karcz.piotr.repository.dao

import com.karcz.piotr.data.AddressModel

interface AddressDao {
    fun isInOrFalse(address: AddressModel): Boolean
    fun get(id: Int): AddressModel?
    fun add(address: AddressModel): Int
    fun update(address: AddressModel)
    fun remove(address: AddressModel)
}

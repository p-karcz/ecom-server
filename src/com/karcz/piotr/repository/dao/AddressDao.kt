package com.karcz.piotr.repository.dao

import com.karcz.piotr.data.AddressModel

interface AddressDao {
    fun isInOrFalse(addressModel: AddressModel): Boolean
    fun get(id: Int): AddressModel?
    fun add(addressModel: AddressModel): Int
    fun update(addressModel: AddressModel)
    fun remove(id: Int)
}

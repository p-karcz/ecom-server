package com.karcz.piotr.repository.dao

import com.karcz.piotr.data.AddressModel

interface AddressDao {
    fun isIn(address: AddressModel): Boolean
    fun get(id: Int): AddressModel?
    fun add(address: AddressModel)
    fun update(address: AddressModel)
    fun remove(address: AddressModel)
}

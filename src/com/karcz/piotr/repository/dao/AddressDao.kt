package com.karcz.piotr.repository.dao

import com.karcz.piotr.domaindata.AddressDomainModel

interface AddressDao {
    fun isInOrFalse(addressDomainModel: AddressDomainModel): Boolean
    fun get(id: Int): AddressDomainModel?
    fun add(addressDomainModel: AddressDomainModel): Int
    fun update(addressDomainModel: AddressDomainModel)
    fun remove(id: Int)
}

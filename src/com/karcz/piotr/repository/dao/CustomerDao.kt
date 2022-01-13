package com.karcz.piotr.repository.dao

import com.karcz.piotr.domaindata.CustomerDomainModel

interface CustomerDao {
    fun isIn(email: String): Boolean
    fun get(email: String): CustomerDomainModel?
    fun add(customerDomainModel: CustomerDomainModel)
    fun update(customerDomainModel: CustomerDomainModel)
    fun remove(email: String)
}

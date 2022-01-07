package com.karcz.piotr.repository.dao

import com.karcz.piotr.data.CustomerModel

interface CustomerDao {
    fun isIn(email: String): Boolean
    fun get(email: String): CustomerModel?
    fun add(customerModel: CustomerModel)
    fun update(customerModel: CustomerModel)
    fun remove(email: String)
}

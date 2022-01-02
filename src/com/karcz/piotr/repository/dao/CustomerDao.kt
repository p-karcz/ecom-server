package com.karcz.piotr.repository.dao

import com.karcz.piotr.data.CustomerModel

interface CustomerDao {
    fun isIn(email: String): Boolean
    fun get(email: String): CustomerModel?
    fun add(customer: CustomerModel)
    fun update(customer: CustomerModel)
    fun remove(customer: CustomerModel)
}

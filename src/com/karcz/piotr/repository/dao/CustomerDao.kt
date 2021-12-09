package com.karcz.piotr.repository.dao

import com.karcz.piotr.repository.resources.CustomerResource

interface CustomerDao {
    fun isIn(customer: CustomerResource): Boolean
    fun get(email: String): CustomerResource?
    fun add(customer: CustomerResource)
    fun update(customer: CustomerResource)
    fun remove(customer: CustomerResource)
}
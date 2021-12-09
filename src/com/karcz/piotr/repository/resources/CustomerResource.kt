package com.karcz.piotr.repository.resources

data class CustomerResource(
    val email: String,
    val addressId: Int?,
    val name: String,
    val surname: String,
    val password: String
)

package com.karcz.piotr.repository.resources

data class AddressResource(
    val id: Int,
    val street: String,
    val streetNumber: Int,
    val flatNumber: Int,
    val postalCode: String,
    val country: String,
    val city: String
)

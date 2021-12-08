package com.karcz.piotr.domain

data class AddressDomainModel(
    val street: String,
    val streetNumber: Int,
    val flatNumber: Int,
    val postalCode: String,
    val country: String,
    val city: String
)

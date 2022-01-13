package com.karcz.piotr.domaindata

import com.karcz.piotr.security.hash

data class CustomerRegistrationDomainModel(
    val email: String,
    val password: String,
    val name: String,
    val surname: String,
    val street: String,
    val streetNumber: Int,
    val flatNumber: Int,
    val postalCode: String,
    val country: String,
    val city: String
) {

    fun toAddressDomainModel() = AddressDomainModel(
        id = DEFAULT_NOT_USED_ADDRESS_ID,
        street = street,
        streetNumber = streetNumber,
        flatNumber = flatNumber,
        postalCode = postalCode,
        country = country,
        city = city
    )

    fun toCustomerDomainModel(addressId: Int) = CustomerDomainModel(
        email = email,
        addressId = addressId,
        name = name,
        surname = surname,
        password = hash(password)
    )

    companion object {
        const val DEFAULT_NOT_USED_ADDRESS_ID = -1
    }
}

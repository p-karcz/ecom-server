package com.karcz.piotr.data.authentication

import com.karcz.piotr.data.AddressModel
import com.karcz.piotr.data.CustomerModel
import com.karcz.piotr.security.hash

data class CustomerRegistrationModel(
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

    fun toAddressModel() = AddressModel(
        id = DEFAULT_NOT_USED_ADDRESS_ID,
        street = street,
        streetNumber = streetNumber,
        flatNumber = flatNumber,
        postalCode = postalCode,
        country = country,
        city = city
    )

    fun toCustomerModel(addressId: Int) = CustomerModel(
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

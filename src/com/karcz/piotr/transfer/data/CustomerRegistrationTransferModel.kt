package com.karcz.piotr.transfer.data

import com.karcz.piotr.domaindata.CustomerRegistrationDomainModel

data class CustomerRegistrationTransferModel(
    val email: String? = null,
    val password: String? = null,
    val name: String? = null,
    val surname: String? = null,
    val street: String? = null,
    val streetNumber: Int? = null,
    val flatNumber: Int? = null,
    val postalCode: String? = null,
    val country: String? = null,
    val city: String? = null
) {

    fun toDomainModel(): CustomerRegistrationDomainModel? {
        return if (listOf(email, password, name, surname, street, streetNumber, flatNumber, postalCode, country, city).any { it == null }) {
            null
        } else {
            CustomerRegistrationDomainModel(
                email = email!!,
                password = password!!,
                name = name!!,
                surname = surname!!,
                street = street!!,
                streetNumber = streetNumber!!,
                flatNumber = flatNumber!!,
                postalCode = postalCode!!,
                country = country!!,
                city = city!!
            )
        }
    }
}

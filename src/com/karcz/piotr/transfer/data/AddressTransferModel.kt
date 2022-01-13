package com.karcz.piotr.transfer.data

import com.karcz.piotr.domaindata.AddressDomainModel

data class AddressTransferModel(
    val id: Int? = null,
    val street: String? = null,
    val streetNumber: Int? = null,
    val flatNumber: Int? = null,
    val postalCode: String? = null,
    val country: String? = null,
    val city: String? = null
) {

    fun toDomainModel(): AddressDomainModel? {
        return if (listOf(id, street, streetNumber, flatNumber, postalCode, country, city).any { it == null }) {
            null
        } else {
            AddressDomainModel(
                id = id!!,
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

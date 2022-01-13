package com.karcz.piotr.transfer.data

import com.karcz.piotr.domaindata.CustomerLoginDomainModel

data class CustomerLoginTransferModel(
    val email: String? = null,
    val password: String? = null
) {

    fun toDomain(): CustomerLoginDomainModel? {
        return if (listOf(email, password).any { it == null }) {
            null
        } else {
            CustomerLoginDomainModel(
                email = email!!,
                password = password!!
            )
        }
    }
}

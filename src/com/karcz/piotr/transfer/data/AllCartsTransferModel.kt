package com.karcz.piotr.transfer.data

data class AllCartsTransferModel(
    val cartList: List<CartTransferModel>? = null,
    val productList: List<ProductTransferModel>? = null
)

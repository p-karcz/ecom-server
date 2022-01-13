package com.karcz.piotr.transfer.data

data class AllOrderDetailsWithProductsTransferModel(
    val orderDetailList: List<OrderDetailTransferModel>? = null,
    val productList: List<ProductTransferModel>? = null
)

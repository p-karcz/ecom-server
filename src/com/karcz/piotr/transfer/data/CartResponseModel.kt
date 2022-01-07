package com.karcz.piotr.transfer.data

import com.karcz.piotr.data.CartModel
import com.karcz.piotr.data.ProductModel

data class CartResponseModel(
    val cartList: List<CartModel>,
    val productList: List<ProductModel>
)

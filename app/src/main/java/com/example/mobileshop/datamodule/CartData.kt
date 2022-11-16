package com.example.mobileshop.datamodule

import com.google.gson.annotations.SerializedName

class CartData {
    @SerializedName("basket")
    lateinit var phones: MutableList<CartPhone>

    lateinit var delivery: String
    lateinit var id: String
    var total: Int = 0
}
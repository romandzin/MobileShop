package com.example.mobileshop.datamodule

data class CartPhone(
    var id: Int?,
    var images: String?,
    override var title: String?,
    var price: Int?,
    var delivery: String?,
    var count: Int?
): Product


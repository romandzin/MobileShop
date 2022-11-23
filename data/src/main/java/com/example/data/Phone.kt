package com.example.data

data class Phone(
    var id: Int?,
    var is_new: Boolean?,
    override var title: String?,
    var subtitle: String?,
    var picture: String?,
    var is_buy: Boolean?,
    var price_without_discount: Int?,
    var discount_price: Int?,

    var CPU: String?,
    var camera: String?,
    var capacity: MutableList<String>?,
    var color: MutableList<String>?,
    var images: MutableList<String>?,
    var is_favorites: Boolean?,
    var price: Int?,
    var rating: Double?,
    var sd: String?,
    var ssd: String?,
): Product
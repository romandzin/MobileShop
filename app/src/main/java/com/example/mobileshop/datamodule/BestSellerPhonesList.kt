package com.example.mobileshop.datamodule

import com.google.gson.annotations.SerializedName

class BestSellerPhonesList {
    @SerializedName("best_seller")
    lateinit var phones: MutableList<Phone>
}
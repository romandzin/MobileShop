package com.example.mobileshop.datamodule

import com.google.gson.annotations.SerializedName

class HotSalesPhonesList {
    @SerializedName("home_store")
    lateinit var phones: MutableList<Phone>
}
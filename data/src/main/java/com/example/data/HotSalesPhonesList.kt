package com.example.data

import com.google.gson.annotations.SerializedName

class HotSalesPhonesList {
    @SerializedName("home_store")
    lateinit var phones: MutableList<Phone>
}
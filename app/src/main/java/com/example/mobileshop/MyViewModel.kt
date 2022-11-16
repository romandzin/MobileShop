package com.example.mobileshop

import androidx.lifecycle.ViewModel
import com.example.mobileshop.datamodule.CartPhone

class MyViewModel: ViewModel() {

    var cartPhonesList: ArrayList<CartPhone> = arrayListOf()

}
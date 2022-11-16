package com.example.mobileshop.cartmodule

import com.example.mobileshop.CartActivity
import com.example.mobileshop.DetailsActivity
import com.example.mobileshop.datamodule.DataModule
import dagger.Component

@Component(modules = [DataModule::class])
interface CartComponent {

    fun inject(cartActivity: CartActivity)
}
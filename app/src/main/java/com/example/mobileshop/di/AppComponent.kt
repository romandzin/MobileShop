package com.example.mobileshop.di

import com.example.mobileshop.ui.activity.CartActivity
import com.example.mobileshop.ui.activity.DetailsActivity
import com.example.mobileshop.ui.activity.MainActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)
    fun inject(detailsActivity: DetailsActivity)
    fun inject(cartActivity: CartActivity)

}
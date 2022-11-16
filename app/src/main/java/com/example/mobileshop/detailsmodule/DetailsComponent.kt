package com.example.mobileshop.detailsmodule

import com.example.mobileshop.DetailsActivity
import com.example.mobileshop.datamodule.DataModule
import dagger.Component
import dagger.Provides

@Component(modules = [DataModule::class])
interface DetailsComponent {

    fun inject(detailsActivity: DetailsActivity)
}
package com.example.mobileshop

import com.example.mobileshop.datamodule.DataModule
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [DataModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

}
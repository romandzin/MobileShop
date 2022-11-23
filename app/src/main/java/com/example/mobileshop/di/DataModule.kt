package com.example.mobileshop.di

import com.example.data.ApiService
import com.example.data.CategoriesList
import com.example.mobileshop.ui.view.FilterView
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    fun provideFilterView(): FilterView = FilterView()

    @Provides
    @Singleton
    fun getRetrofitClient(baseUrl: String): Retrofit {
         return Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    fun getRetrofitService(): ApiService {
        val BASE_URL = "https://run.mocky.io/v3/"
        return getRetrofitClient(BASE_URL).create(ApiService::class.java)
    }

    @Provides
    @Singleton
    fun getCategoryList(): CategoriesList = CategoriesList()
}
package com.example.data

import com.example.mobileshop.R

data class Category(val name: String, val image: Int)

class CategoriesList {

    fun returnCategoriesList(): ArrayList<Category> {
        return arrayListOf(
            Category("Phones", R.drawable.ic_phone),
            Category("Computer", R.drawable.ic_computer),
            Category("Health", R.drawable.ic_health),
            Category("Books", R.drawable.ic_books),
        )}

}


package com.example.mobileshop

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileshop.R
import com.example.mobileshop.adapters.CartAdapter
import com.example.mobileshop.adapters.PhotoDetailsAdapter

class CartActivity : AppCompatActivity() {

    lateinit var userViewModel: MyViewModel
    private var total: Int? = 0
    lateinit var recyclerView: RecyclerView
    lateinit var cartAdapter: CartAdapter
    var phonePrice: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)

        findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            onBackPressed()
        }

        val viewModelFactory = ViewModelFactory()
        userViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            MyViewModel::class.java
        )
        recyclerView = findViewById(R.id.recyclerView)
        cartAdapter = CartAdapter(userViewModel.cartPhonesList, userViewModel, this)
        recyclerView.adapter = cartAdapter
    }

    public override fun onResume() {
        super.onResume()
        total = 0
        if (userViewModel.cartPhonesList.size == 0) findViewById<TextView>(R.id.total_price).text = "$$total us"
        else {
            for (i in userViewModel.cartPhonesList) {
                phonePrice = i.count?.let { i.price?.times(it) }
                total = phonePrice?.let { total?.plus(it) }
            }
            findViewById<TextView>(R.id.total_price).text = "$$total us"
        }

    }
}
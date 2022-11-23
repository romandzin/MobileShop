package com.example.mobileshop.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageButton
import android.widget.TextView
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileshop.MyViewModel
import com.example.mobileshop.R
import com.example.mobileshop.ViewModelFactory
import com.example.mobileshop.adapters.CartAdapter

class CartActivity : AppCompatActivity() {

    lateinit var userViewModel: MyViewModel
    private var total: Int? = 0
    lateinit var recyclerView: RecyclerView
    lateinit var cartAdapter: CartAdapter
    var phonePrice: Int? = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cart)
        val viewModelFactory = ViewModelFactory()
        userViewModel = ViewModelProviders.of(this, viewModelFactory)[MyViewModel::class.java]
        initializeView()
    }

    public override fun onResume() {
        super.onResume()
        setTotalCount()
    }

    private fun initializeView() {
        findViewById<ImageButton>(R.id.back_button).setOnClickListener {
            onBackPressed()
        }
        recyclerView = findViewById(R.id.recyclerView)
        cartAdapter = CartAdapter(userViewModel.cartPhonesList, userViewModel, this)
        recyclerView.adapter = cartAdapter
    }

    private fun setTotalCount() {
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
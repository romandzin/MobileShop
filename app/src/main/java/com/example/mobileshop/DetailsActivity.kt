package com.example.mobileshop

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileshop.adapters.PhotoDetailsAdapter
import com.example.mobileshop.datamodule.ApiService
import com.example.mobileshop.datamodule.Phone
import com.example.mobileshop.detailsmodule.DaggerDetailsComponent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class DetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var myService: ApiService

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        DaggerDetailsComponent.builder().build().inject(this)
        findViewById<ImageView>(R.id.back_button).setOnClickListener {
            onBackPressed()
        }

        findViewById<ImageButton>(R.id.cart_button).setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }

        MainScope().launch {
            val phone: Phone?
            withContext(Dispatchers.Default) {
                phone = myService.getPhonesDetails().execute().body()
            }
            val shopFragment = ShopFragment(phone)
            findViewById<RatingBar>(R.id.stars).rating = phone?.rating?.toFloat()!!
            findViewById<TextView>(R.id.product_name).text = phone.title
            supportFragmentManager.beginTransaction().replace(R.id.choosen_fragment, shopFragment).commit()
            phone.images
            val recyclerView = findViewById<RecyclerView>(R.id.main_photo_recycler_view)
            val photoDetailsAdapter = PhotoDetailsAdapter(phone.images as ArrayList<String?>)
            recyclerView.adapter = photoDetailsAdapter
        }

        val viewModelFactory = ViewModelFactory()
        val userViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            MyViewModel::class.java
        )

    }
}
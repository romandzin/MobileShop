package com.example.mobileshop

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.coordinatorlayout.widget.CoordinatorLayout
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.mobileshop.adapters.BestSellerAdapter
import com.example.mobileshop.datamodule.*
import com.google.android.material.bottomsheet.BottomSheetBehavior
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject lateinit var myService: ApiService
    @Inject lateinit var categoriesList: CategoriesList
    lateinit var bottomSheetContainer: CoordinatorLayout
    lateinit var userViewModel: MyViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val viewModelFactory = ViewModelFactory()
        userViewModel = ViewModelProviders.of(this, viewModelFactory).get(
            MyViewModel::class.java
        )
        DaggerAppComponent.builder().build().inject(this)
        initializeView()
    }

    override fun onResume() {
        super.onResume()
        val cart_count = findViewById<ConstraintLayout>(R.id.bottom_nav).findViewById<TextView>(R.id.cart_count)
        if (userViewModel.cartPhonesList.size == 0) cart_count.visibility = View.GONE
        else cart_count.visibility = View.VISIBLE
        var product_count = 0
        for (i in userViewModel.cartPhonesList) {
                product_count += i.count!!
            }
        cart_count.text = product_count.toString()
    }

    private fun initializeView() {
        val cart_icon = findViewById<ConstraintLayout>(R.id.bottom_nav).findViewById<ImageView>(R.id.ic_cart)
        cart_icon.setOnClickListener {
            val intent = Intent(this, CartActivity::class.java)
            startActivity(intent)
        }
        val filterView = FilterView()
        bottomSheetContainer = findViewById(R.id.bottom_sheet_container)
        supportFragmentManager.beginTransaction().replace(bottomSheetContainer.id, filterView).commit()

        val categoryAdapter = CategoryAdapter(categoriesList.returnCategoriesList(), resources)
        val categoryRecyclerView = findViewById<RecyclerView>(R.id.category_recyclerView)
        categoryRecyclerView.adapter = categoryAdapter

        loadRecyclerViewData(R.id.hot_sales_recyclerView, LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false), "hotSales")
        loadRecyclerViewData(R.id.best_seller_recyclerView, GridLayoutManager(this, 2), "bestSeller")

        findViewById<ImageView>(R.id.ic_filter).setOnClickListener {
            bottomSheetContainer.visibility = View.VISIBLE
            filterView.onChangeState(BottomSheetBehavior.STATE_COLLAPSED)
        }
    }

    private fun getBestSellerPhonesList(): MutableList<Phone>? {
        return myService.getBestSellerPhonesList().execute().body()?.phones
    }

    private fun getHotSalesPhonesList(): MutableList<Phone>? {
        return myService.getHotSalesPhonesList().execute().body()?.phones
    }

    private fun loadRecyclerViewData(id: Int, layoutManager: LayoutManager, type: String) {
        MainScope().launch {
            val recyclerView = findViewById<RecyclerView>(id)
            recyclerView.layoutManager = layoutManager
            if (type == "bestSeller"){
                var bestSellerAdapter: BestSellerAdapter
                withContext(Dispatchers.Default) {
                    bestSellerAdapter = BestSellerAdapter(getBestSellerPhonesList() as ArrayList<Phone>, this@MainActivity)
                }
                recyclerView.adapter = bestSellerAdapter
            }
            else if (type == "hotSales"){
                var hotSalesAdapter: HotSalesAdapter
                withContext(Dispatchers.Default) {
                    hotSalesAdapter = HotSalesAdapter(getHotSalesPhonesList() as ArrayList<Phone>)
                }
                recyclerView.adapter = hotSalesAdapter
            }
        }
    }
}
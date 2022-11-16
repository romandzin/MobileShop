package com.example.mobileshop.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileshop.CartActivity
import com.example.mobileshop.MyViewModel
import com.example.mobileshop.R
import com.example.mobileshop.datamodule.CartPhone
import com.example.mobileshop.datamodule.Phone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class CartViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val title = itemView.findViewById<TextView>(R.id.item_name)
    val counter = itemView.findViewById<TextView>(R.id.item_counter)
    val price = itemView.findViewById<TextView>(R.id.item_price)
    val image = itemView.findViewById<ImageView>(R.id.item_icon)
    val plus_button = itemView.findViewById<ImageView>(R.id.plus_button)
    val minus_button = itemView.findViewById<ImageView>(R.id.minus_button)
    val delete_button = itemView.findViewById<ImageView>(R.id.delete_button)
}

class CartAdapter(private val categoriesList: ArrayList<CartPhone>, var viewModel: MyViewModel, val activity: CartActivity):
    RecyclerView.Adapter<CartViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CartViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_cart, parent, false)
        return CartViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: CartViewHolder, position: Int) {
        holder.title.text = categoriesList[position].title
        MainScope().launch {
            val image: Bitmap
            withContext(Dispatchers.IO) {
                val imageUrl = categoriesList[position].images
                val url = URL(imageUrl)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                val imageStream: InputStream = connection.inputStream
                image = BitmapFactory.decodeStream(imageStream)
            }
            holder.image.setImageBitmap(image)
        }
        holder.counter.text = categoriesList[position].count.toString()
        holder.price.text = "$${categoriesList[position].price}"
        holder.plus_button.setOnClickListener {
            viewModel.cartPhonesList[position].count = viewModel.cartPhonesList[position].count?.plus(1)
            holder.counter.text = viewModel.cartPhonesList[position].count.toString()
            activity.onResume()
        }
        holder.minus_button.setOnClickListener {
            viewModel.cartPhonesList[position].count = viewModel.cartPhonesList[position].count?.minus(1)
            holder.counter.text = viewModel.cartPhonesList[position].count.toString()
            if (viewModel.cartPhonesList[position].count == 0) deleteElement(position)
            activity.onResume()
        }
        holder.delete_button.setOnClickListener {
            viewModel.cartPhonesList[position].count = 0
            holder.counter.text = viewModel.cartPhonesList[position].count.toString()
            if (viewModel.cartPhonesList[position].count == 0) deleteElement(position)
            activity.onResume()
        }
    }

    private fun deleteElement(position: Int) {
        viewModel.cartPhonesList.removeAt(position)
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }
}

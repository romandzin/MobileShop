package com.example.mobileshop.adapters

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileshop.DetailsActivity
import com.example.mobileshop.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class BestSellerViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val title = itemView.findViewById<TextView>(R.id.product_name)
    val discountPrice = itemView.findViewById<TextView>(R.id.product_price_with_discount)
    val oldPrice = itemView.findViewById<TextView>(R.id.product_price_without_discount)
    var image = itemView.findViewById<ImageView>(R.id.product_image)
    var favorite_button = itemView.findViewById<ImageView>(R.id.favorite_button)
    var clicked: Boolean = false
}

class BestSellerAdapter(private val categoriesList: ArrayList<com.example.mobileshop.datamodule.Phone>, private val context: Context):
    RecyclerView.Adapter<BestSellerViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BestSellerViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_best_seller, parent, false)
        return BestSellerViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: BestSellerViewHolder, position: Int) {
        holder.title.text = categoriesList[position].title
        MainScope().launch {
            val image: Bitmap?
            withContext(Dispatchers.IO) {
                val imageUrl = categoriesList[position].picture
                val url = URL(imageUrl)
                val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                if (connection.responseCode == 502) {
                    image = null
                }
                else {
                    val imageStream: InputStream = connection.inputStream
                    image = BitmapFactory.decodeStream(imageStream)
                }

            }
            holder.image.setImageBitmap(image)
        }
        holder.discountPrice.text = "$${categoriesList[position].price_without_discount}"
        holder.oldPrice.text = Html.fromHtml("<s>" + "$${categoriesList[position].discount_price}" + "</s>")
        holder.itemView.setOnClickListener {
            val intent = Intent(context, DetailsActivity::class.java)
            context.startActivity(intent)
        }
        holder.favorite_button.setOnClickListener {
            if (holder.clicked) {
                holder.favorite_button.setImageDrawable(context.getDrawable(R.drawable.ic_heart))
                holder.clicked = false
            }
            else {
                holder.favorite_button.setImageDrawable(context.getDrawable(R.drawable.ic_clicked_heart))
                holder.clicked = true
            }
        }
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }


}
package com.example.mobileshop

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.data.Phone
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


class HotSalesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val title = itemView.findViewById<TextView>(R.id.product_title)
    val subtitle = itemView.findViewById<TextView>(R.id.product_subtitle)
    var image = itemView.findViewById<ImageView>(R.id.image)
}

class HotSalesAdapter(private val categoriesList: ArrayList<com.example.data.Phone>):
    RecyclerView.Adapter<HotSalesViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HotSalesViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_hot_sales, parent, false)
        return HotSalesViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: HotSalesViewHolder, position: Int) {
        holder.title.text = categoriesList[position].title
        MainScope().launch {
            var image: Bitmap?
            withContext(Dispatchers.IO) {
                val imageUrl = categoriesList[position].picture
                val url = URL(imageUrl)
                try {
                    val connection: HttpURLConnection = url.openConnection() as HttpURLConnection
                    val imageStream: InputStream = connection.inputStream
                    image = BitmapFactory.decodeStream(imageStream)
                }
                catch (e: java.lang.Exception) {
                    image = null
                }
            }
            holder.image.setImageBitmap(image)
        }

        holder.subtitle.text = categoriesList[position].subtitle
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }


}
package com.example.mobileshop.adapters

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.mobileshop.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class PhotoDetailsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    var image = itemView.findViewById<ImageView>(R.id.main_photo)
}

class PhotoDetailsAdapter(private val photosList: ArrayList<String?>):
    RecyclerView.Adapter<PhotoDetailsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PhotoDetailsViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_photo, parent, false)
        return PhotoDetailsViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: PhotoDetailsViewHolder, position: Int) {
        MainScope().launch {
            val image: Bitmap?
            withContext(Dispatchers.IO) {
                val imageUrl = photosList[position]
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
    }

    override fun getItemCount(): Int {
        return photosList.size
    }


}
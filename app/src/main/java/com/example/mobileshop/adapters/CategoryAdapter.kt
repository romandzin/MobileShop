package com.example.mobileshop

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.graphics.drawable.DrawableCompat
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter

class MyViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
    val nameText = itemView.findViewById<TextView>(R.id.category_name)
    var image = itemView.findViewById<ImageView>(R.id.ic_category)
}

class CategoryAdapter(private val categoriesList: ArrayList<com.example.data.Category>, val resources: Resources):
    Adapter<MyViewHolder>() {

    val saveInstanceList = ArrayList<View>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        val myViewHolder = MyViewHolder(itemView)
        saveInstanceList.add(itemView)

        itemView.setOnClickListener(object: View.OnClickListener {
            override fun onClick(v: View?) {
                for (i in saveInstanceList){
                    val textTitle = i.findViewById<TextView>(R.id.category_name)
                    val image = i.findViewById<ImageView>(R.id.ic_category)
                    if (saveInstanceList.get(myViewHolder.adapterPosition) == i){
                        textTitle.setTextColor(resources.getColor(R.color.base_orange))
                        image.background = (resources.getDrawable(R.drawable.ellipse_orange))
                        DrawableCompat.setTint(image.drawable, resources.getColor(R.color.white))
                    }
                    else {
                        textTitle.setTextColor(resources.getColor(R.color.grey_text))
                        image.background = (resources.getDrawable(R.drawable.ellipse_white))
                        DrawableCompat.setTint(image.drawable, resources.getColor(R.color.grey_text))
                    }
                }
            }

        })
        return myViewHolder
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.nameText.text = categoriesList[position].name
        holder.image.setImageDrawable(resources.getDrawable(categoriesList[position].image))
    }

    override fun getItemCount(): Int {
        return categoriesList.size
    }


}
package com.example.mobileshop.ui.fragment

import android.content.Intent
import android.graphics.Color
import android.graphics.PorterDuff
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProviders
import com.example.mobileshop.MyViewModel
import com.example.mobileshop.R
import com.example.mobileshop.ViewModelFactory
import com.example.mobileshop.ui.activity.CartActivity

class ShopFragment(val phone: com.example.data.Phone?) : Fragment() {

    lateinit var rootView: View
    lateinit var userViewModel: MyViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        rootView = inflater.inflate(R.layout.fragment_shop, container, false)
        initializeView()

        return rootView
    }

    private fun initializeView() {
        val viewModelFactory = ViewModelFactory()
        userViewModel = ViewModelProviders.of(this, viewModelFactory)[MyViewModel::class.java]
        val image1 = rootView.findViewById<ImageView>(R.id.color1)
        val image2 = rootView.findViewById<ImageView>(R.id.color2)
        val size1 = rootView.findViewById<TextView>(R.id.size1)
        val size2 = rootView.findViewById<TextView>(R.id.size2)
        val checked1 = rootView.findViewById<ImageView>(R.id.checked1)
        val checked2 = rootView.findViewById<ImageView>(R.id.checked2)
        rootView.findViewById<TextView>(R.id.cpu).text = phone?.CPU
        rootView.findViewById<TextView>(R.id.camera).text = phone?.camera
        rootView.findViewById<TextView>(R.id.ozu).text = phone?.ssd
        rootView.findViewById<TextView>(R.id.memory).text = phone?.sd


        image1.background.setColorFilter(Color.parseColor(phone?.color?.get(0)), PorterDuff.Mode.SRC_IN)


        image2.background.setColorFilter(Color.parseColor(phone?.color?.get(1)), PorterDuff.Mode.SRC_IN)

        size1.text = "${phone?.capacity?.get(0)} gb"

        size2.text = "${phone?.capacity?.get(1)} gb"
        rootView.findViewById<TextView>(R.id.product_price).text = "$${phone?.price}"

        size1.setOnClickListener {
            setChosen(size1)
            setToDefault(size2)
        }

        size2.setOnClickListener {
            setChosen(size2)
            setToDefault(size1)
        }

        image1.setOnClickListener {
            setChosen(checked1)
            setToDefault(checked2)
        }

        image2.setOnClickListener {
            setChosen(checked2)
            setToDefault(checked1)
        }

        rootView.findViewById<ConstraintLayout>(R.id.add_to_cart_button).setOnClickListener {
            addToCart()
        }
    }

    fun setToDefault(textView: TextView) {
        textView.background = null
        textView.setTextColor(resources.getColor(R.color.grey_text))
    }

    fun setToDefault(imageView: ImageView) {
        imageView.visibility = View.GONE
    }

    fun setChosen(textView: TextView) {
        textView.background = resources.getDrawable(R.drawable.button_background)
        textView.setTextColor(resources.getColor(R.color.white))
    }

    fun setChosen(imageView: ImageView) {
        imageView.visibility = View.VISIBLE
    }

    fun addToCart() {
        val intent = Intent(activity?.baseContext, CartActivity::class.java)
        userViewModel.cartPhonesList.add(
            com.example.data.CartPhone(
                id = phone?.id,
                images = phone?.images?.get(0),
                price = phone?.price,
                title = phone?.title,
                delivery = "Free",
                count = 1,
            )
        )
        startActivity(intent)
    }


}
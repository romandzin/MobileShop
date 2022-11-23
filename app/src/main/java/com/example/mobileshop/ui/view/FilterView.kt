package com.example.mobileshop.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.ImageButton
import android.widget.Spinner
import androidx.annotation.ArrayRes
import androidx.constraintlayout.widget.ConstraintLayout
import com.example.mobileshop.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment


class FilterView: BottomSheetDialogFragment() {

    private lateinit var bottomSheetBehavior: BottomSheetBehavior<View>

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = inflater.inflate(R.layout.filter_bottom_sheet, container, false)
        val bottomSheet = view.findViewById<ConstraintLayout>(R.id.standard_bottom_sheet)
        bottomSheetBehavior = BottomSheetBehavior.from(bottomSheet)
        bottomSheetBehavior.isHideable = true

        setDataToSpinner(view?.findViewById(R.id.size_spinner), R.array.size)
        setDataToSpinner(view?.findViewById(R.id.brand_spinner), R.array.brand)
        setDataToSpinner(view?.findViewById(R.id.price_spinner), R.array.price)

        view?.findViewById<ImageButton>(R.id.close_button)?.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
        view?.findViewById<Button>(R.id.done_button)?.setOnClickListener {
            bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        }
        return view
    }

    private fun setDataToSpinner(spinner: Spinner?, @ArrayRes array: Int) {
        val adapter: ArrayAdapter<CharSequence>? = activity?.let {
            ArrayAdapter.createFromResource(
                it.baseContext, array,
                android.R.layout.simple_spinner_item
            )
        }
        adapter?.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        spinner?.adapter = adapter
    }

    @Deprecated("Deprecated in Java")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        bottomSheetBehavior.state = BottomSheetBehavior.STATE_HIDDEN
    }

    fun onChangeState(state: Int) {
        if (state == BottomSheetBehavior.STATE_HIDDEN) {
            view?.visibility = View.GONE
        }
        bottomSheetBehavior.state = state
    }
}
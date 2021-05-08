package com.example.starwarscharacters.utils

import android.content.Context
import android.graphics.Color
import android.view.Gravity
import android.view.View
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Lorenzo Suarez on 3/5/2021.
 */

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

fun Context.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    Toast.makeText(this, message, duration).show()
}

fun Fragment.showToast(message: String, duration: Int = Toast.LENGTH_SHORT) {
    requireContext().showToast(message, duration)
}

fun onSNACK(view: View, string: String) {
    val snackbar: Snackbar = Snackbar.make(
        view, string,
        Snackbar.LENGTH_LONG
    )
    val textView =
        snackbar.view.findViewById(com.google.android.material.R.id.snackbar_text) as TextView
    textView.textSize = 18f
    textView.textAlignment = View.TEXT_ALIGNMENT_CENTER
    snackbar.show()
}
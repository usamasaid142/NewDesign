package com.example.newdesign.utils

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Toast
import androidx.databinding.BindingAdapter

object DataBinding {

    @JvmStatic
    @BindingAdapter("phoneNumber")
    fun call(view: View, number: String?) {
        val context = view.context
        view.setOnClickListener {
            if(number==null)
                view.context.displayToastText("no_phone number")
            else {
                val intent = Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", number, null))
                context.startActivity(intent)
            }
        }

    }

    fun Context.displayToastText(txt:String){
        Toast.makeText(this,txt, Toast.LENGTH_SHORT).show()
    }
}
package com.example.lojagames.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import java.lang.Exception

object TextViewBindingToDouble {

    @JvmStatic
    @BindingAdapter("android:text")
    fun setTextFromDouble(textView: TextView, value: Double){
        if(getTextAsDouble(textView) != value){
            textView.text = value.toString()
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "android:text")
    fun getTextAsDouble(textView: TextView): Double{
        return try {
            textView.text.toString().toDouble()
        } catch (e: Exception){
            0.0
        }
    }
}
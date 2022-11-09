package com.example.lojagames.binding

import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.databinding.InverseBindingAdapter
import java.lang.Exception

object TextViewBinding {

    @JvmStatic
    @BindingAdapter("android:text")
    fun setTextFromInt(textView: TextView, value: Int){
        if(getTextAsInt(textView) != value){
            textView.text = value.toString()
        }
    }

    @JvmStatic
    @InverseBindingAdapter(attribute = "android:text")
    fun getTextAsInt(textView: TextView): Int{
        return try {
            Integer.parseInt(textView.text.toString())
        } catch (e: Exception){
            0
        }
    }
}
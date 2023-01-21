package com.example.ujinsia

import android.app.Activity

data class Item(val name: String, val image: Int,val description:String, val activity: Class<out Activity>)

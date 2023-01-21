package com.example.ujinsia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView


class LearnActivity : AppCompatActivity() {

    private lateinit var wbview: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_learn)
        wbview=findViewById(R.id.webView)
        openweb()
    }

     private fun openweb(){
        wbview.loadUrl("https://www.unicef.org/protection/gender-based-violence-in-emergencies")
        wbview.settings.javaScriptEnabled = true
        wbview.settings.domStorageEnabled = true

    }

}



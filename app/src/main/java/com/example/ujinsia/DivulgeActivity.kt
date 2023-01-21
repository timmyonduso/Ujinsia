package com.example.ujinsia

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView

class DivulgeActivity : AppCompatActivity() {
    private lateinit var wbview: WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_divulge)
        wbview=findViewById(R.id.webView2)
        openweb()
    }

    private fun openweb(){
        wbview.loadUrl("https://ee.kobotoolbox.org/x/Qg8r4Pe2")
        wbview.settings.javaScriptEnabled = true
        wbview.settings.domStorageEnabled = true

    }

}
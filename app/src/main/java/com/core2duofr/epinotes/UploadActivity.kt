package com.core2duofr.epinotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView

class UploadActivity : AppCompatActivity() {
    lateinit var login_imgView : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        var url = "https://epinotes.core2duo.fr/upload.php"

        val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true


        webView.loadUrl(url)
    }
    override fun onBackPressed() {
        super.onBackPressed()

    }
}
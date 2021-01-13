package com.core2duo.epinotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class WebSiteActivity : AppCompatActivity() {
    val webView: WebView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_site)
        var url_activity = EpinotesAccueilActivity().url_activity

        val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true


        webView.loadUrl(url_activity)
    }
    override fun onBackPressed() {
        super.onBackPressed()

    }
}

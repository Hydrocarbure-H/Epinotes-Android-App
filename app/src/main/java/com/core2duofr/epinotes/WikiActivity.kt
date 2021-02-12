package com.core2duofr.epinotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature

class WikiActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_wiki)
        val url_activity = "https://epinotes.core2duo.fr/epinotes_wiki.html"

        val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url_activity)
    }
}
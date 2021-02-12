package com.core2duofr.epinotes

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.webkit.CookieManager
import android.webkit.URLUtil
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Toast
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature

class PegasusActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pegasus)
        var url_activity = "https://prepa-epita.helvetius.net/pegasus/index.php#"

        val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        // Chargement du theme
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val theme = preferences.getString("web_view_theme_pegasus", "LIGHT")

        if (theme == "DARK")
        {
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK))
            {
                WebSettingsCompat.setForceDark(webView.getSettings(), WebSettingsCompat.FORCE_DARK_ON)
            }
        }
        if (theme == "LIGHT")
        {
            if (WebViewFeature.isFeatureSupported(WebViewFeature.FORCE_DARK))
            {
                WebSettingsCompat.setForceDark(webView.getSettings(), WebSettingsCompat.FORCE_DARK_OFF)
            }
        }


        webView.loadUrl(url_activity)

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
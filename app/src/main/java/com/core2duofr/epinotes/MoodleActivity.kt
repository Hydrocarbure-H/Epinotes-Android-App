package com.core2duofr.epinotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature

class MoodleActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_moodle)
        var url_activity = "https://moodle.cri.epita.fr/my/"

        val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        // Chargement du theme
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val theme = preferences.getString("web_view_theme_moodle", "DARK")

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
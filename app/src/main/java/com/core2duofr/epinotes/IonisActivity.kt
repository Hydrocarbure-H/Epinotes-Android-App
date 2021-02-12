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

class IonisActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_ionis)
        var url_activity = "https://ionisx.com/dashboard"

        val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        // Chargement du theme
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val theme = preferences.getString("web_view_theme_ionis", "DARK")

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

        webView.setDownloadListener({ url, userAgent, contentDisposition, mimeType, contentLength ->
            val request = DownloadManager.Request(Uri.parse(url))
            request.setMimeType(mimeType)
            request.addRequestHeader("cookie", CookieManager.getInstance().getCookie(url))
            request.addRequestHeader("User-Agent", userAgent)
            request.setDescription("Téléchargement...")
            request.setTitle(URLUtil.guessFileName(url, contentDisposition, mimeType))
            request.allowScanningByMediaScanner()
            request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
            request.setDestinationInExternalFilesDir(this@IonisActivity, Environment.DIRECTORY_DOWNLOADS, ".png")
            val dm = getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
            dm.enqueue(request)
            Toast.makeText(applicationContext, "Fichier téléchargé !", Toast.LENGTH_LONG).show()
        })

    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
}
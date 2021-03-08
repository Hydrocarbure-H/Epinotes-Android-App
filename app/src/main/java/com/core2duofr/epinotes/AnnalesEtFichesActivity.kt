package com.core2duofr.epinotes

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
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

class AnnalesEtFichesActivity : AppCompatActivity() {
    val webView: WebView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_annales_et_fiches)

        val url_activity = "https://login.microsoftonline.com/3534b3d7-316c-4bc9-9ede-605c860f49d2/oauth2/authorize?client_id=91cf8aca-0f01-4ae1-9f6b-3d234f55adae&response_type=code&redirect_uri=https://epita-share.core2duo.fr/connect.php"

        val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true

        webView.loadUrl(url_activity)

        webView.setDownloadListener({ url, userAgent, contentDisposition, mimeType, contentLength ->
            val request = DownloadManager.Request(Uri.parse(url))
            request.setMimeType(mimeType)
            request.addRequestHeader("cookie", CookieManager.getInstance().getCookie(url))
            request.addRequestHeader("User-Agent", userAgent)
            // Envoyer l'url entière du fichier, pour pouvoir le charger directement depuis PdfViewerActivity
            val filename = URLUtil.guessFileName(url, contentDisposition, mimeType)
            val intent = Intent(this, PdfViewerActivity::class.java)
            intent.putExtra("file_name_url", url)
            startActivity(intent)
        })
    }

    override fun onBackPressed() {
         super.onBackPressed()
    }
}

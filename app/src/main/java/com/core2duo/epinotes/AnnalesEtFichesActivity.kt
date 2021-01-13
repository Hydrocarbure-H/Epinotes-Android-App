package com.core2duo.epinotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import android.webkit.WebViewClient

class AnnalesEtFichesActivity : AppCompatActivity() {
    val webView: WebView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_annales_et_fiches)

        var url_activity = "https://login.microsoftonline.com/3534b3d7-316c-4bc9-9ede-605c860f49d2/oauth2/authorize?client_id=91cf8aca-0f01-4ae1-9f6b-3d234f55adae&response_type=code&redirect_uri=https://epita-share.core2duo.fr/connect.php"

        val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url_activity)
    }

    override fun onBackPressed() {
         super.onBackPressed()
    }
}

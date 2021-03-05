package com.core2duofr.epinotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.preference.PreferenceManager
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.Button
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import java.util.ResourceBundle.clearCache

class NotesActivity : AppCompatActivity() {
    lateinit var url : String
    private  val verification_code = "djhfbezqilbfiuyezbf15q16qreqerg54bj654kuyl654iuys65v1q6fv5atr651grtb65ytrdgn1dsf6h5dhj4ds6b4dn4bds1s681"
    lateinit var s1_button : Button
    lateinit var s2_button : Button
    lateinit var s3_button : Button
    lateinit var s4_button : Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val mail = preferences.getString("user_mail", "not_connected")

        var promo = request(mail, "promo")
        var url_activity = ""
        if (promo == "2025")
        {
            url_activity = "https://epinotes.core2duo.fr/Notes/sm2.php"
        }
        else
        {
            url_activity = "https://epinotes.core2duo.fr/Notes/sm4.php"
        }

        s1_button = findViewById(R.id.s1_button) as Button
        s2_button = findViewById(R.id.s2_button) as Button
        s3_button = findViewById(R.id.s3_button) as Button
        s4_button = findViewById(R.id.s4_button) as Button

        val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                    view: WebView,
                    request: WebResourceRequest
            ): Boolean {
                return if(request.url.lastPathSegment == "accueil_mobile.php") {
                    webView.loadUrl("https://epinotes.core2duo.fr/Notes/sm1.php")
                    true
                } else {
                    false
                }
            }
        }
        webView.clearCache(true);
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url_activity)

        s1_button.setOnClickListener {
            webView.loadUrl("https://epinotes.core2duo.fr/Notes/sm1.php")
        }
        s2_button.setOnClickListener {
            webView.loadUrl("https://epinotes.core2duo.fr/Notes/sm2.php")
        }
        s3_button.setOnClickListener {
            webView.loadUrl("https://epinotes.core2duo.fr/Notes/sm3.php")
        }
        s4_button.setOnClickListener {
            webView.loadUrl("https://epinotes.core2duo.fr/Notes/sm4.php")
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

    fun request(mail_address: String?, request: String): String {
        try {
            url = "https://epinotes.core2duo.fr/connect_android.php?mail=" + mail_address + "&code_verification=" + verification_code + "&requete=" + request
            val memeURL = URL(url)
            val bufferedReader = BufferedReader(InputStreamReader(memeURL.openConnection().getInputStream()))
            var lines: String?
            val response = StringBuffer()
            while (bufferedReader.readLine().also { lines = it } != null) {
                response.append(lines)
            }
            return response.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

}
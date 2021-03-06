package com.core2duofr.epinotes

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.FileProvider
import androidx.webkit.WebSettingsCompat
import androidx.webkit.WebViewFeature
import com.github.barteksc.pdfviewer.PDFView
import java.io.File


class PdfViewerActivity : AppCompatActivity() {
         var file_name_url = ""
        lateinit var  pdfView : PDFView
        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_pdf_viewer)
            val webView = findViewById<WebView>(R.id.webView)
            webView.webViewClient = WebViewClient()
            webView.settings.javaScriptEnabled = true

            if (intent.hasExtra("file_name_url")) {
                file_name_url = intent.getStringExtra("file_name_url").toString(); // on récupère la valeur associée à la clé
            }
            println("######")
            println(file_name_url)
            webView.loadUrl("https://drive.google.com/viewerng/viewer?embedded=true&url=" + file_name_url)

            // Récupérer URL du fichier PDF
            // L'afficher dans la webview de google drive
            // Ou faire le lecteur de PDF avec ce lien : https://www.geeksforgeeks.org/how-to-load-pdf-from-url-in-android/

        }

        }
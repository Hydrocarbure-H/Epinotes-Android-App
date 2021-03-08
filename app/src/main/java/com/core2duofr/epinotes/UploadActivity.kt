package com.core2duofr.epinotes

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.media.Image
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast

class UploadActivity : AppCompatActivity() {
    lateinit var login_imgView : ImageView
    val REQUEST_CODE = 200
    lateinit  var image_view : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_upload)
        image_view = findViewById(R.id.imageView) as ImageView
        var url = "https://epinotes.core2duo.fr/upload.php"

        /*val webView = findViewById<WebView>(R.id.webView)
        webView.webViewClient = WebViewClient()
        webView.settings.javaScriptEnabled = true
        webView.loadUrl(url)*/

        //capturePhoto()
        //openGalleryForImage()


    }
//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (resultCode == Activity.RESULT_OK && requestCode == REQUEST_CODE && data != null){
//            image_view.setImageBitmap(data.extras?.get("data") as Bitmap)
//
//            val uriPathHelper = URIPathHelper()
//            val filePath = uriPathHelper.getPath(this, data.extras?.get("data") as Uri)
//        }
//    }

//    fun capturePhoto() {
//
//        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
//        startActivityForResult(cameraIntent, REQUEST_CODE)
//    }
//
//    private fun openGalleryForImage() {
//        val intent = Intent(Intent.ACTION_PICK)
//        intent.type = "image/*"
//        startActivityForResult(intent, REQUEST_CODE)
//    }

    override fun onBackPressed() {
        super.onBackPressed()

    }

}
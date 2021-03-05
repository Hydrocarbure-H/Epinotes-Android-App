package com.core2duofr.epinotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.pdfview.PDFView

class PdfViewerActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pdf_viewer)

        findViewById<PDFView>(R.id.activityMainPdfView).fromAsset("great-expectations.pdf").show()
        findViewById<PDFView>(R.id.activityMainPdfView).fromAsset("paper.pdf").show()

    }
}
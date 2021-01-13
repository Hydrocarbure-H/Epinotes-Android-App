package com.core2duo.epinotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import com.squareup.picasso.Picasso

class ExamensActivity : AppCompatActivity() {
    lateinit var login_imgView : ImageView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_examens)

        login_imgView = findViewById(R.id.login_img) as ImageView
        Picasso.get().load("https://photos.cri.epita.fr/thomas.peugnet").into(login_imgView);


    }
}
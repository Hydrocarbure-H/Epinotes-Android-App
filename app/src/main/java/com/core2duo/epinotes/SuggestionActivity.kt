package com.core2duo.epinotes

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.*
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.squareup.picasso.Picasso

class SuggestionActivity : AppCompatActivity() {

    lateinit var url : String
    lateinit var answer : String
    private  val verification_code = "djhfbezqilbfiuyezbf15q16qreqerg54bj654kuyl654iuys65v1q6fv5atr651grtb65ytrdgn1dsf6h5dhj4ds6b4dn4bds1s681";
    private val mail_adress = "thomas.peugnet@epita.fr"

    lateinit var suggestion_send_button : Button
    lateinit var login_imgView : ImageView
    lateinit var suggestion_corps : EditText
    lateinit var suggestion_title : EditText
    lateinit var suggestion_response_text : TextView





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestion)

        login_imgView = findViewById(R.id.login_img) as ImageView
        Picasso.get().load("https://photos.cri.epita.fr/thomas.peugnet").into(login_imgView);


        suggestion_send_button = findViewById(R.id.suggestion_send_button) as Button
        suggestion_corps = findViewById(R.id.suggestion_corps) as EditText
        suggestion_title = findViewById(R.id.suggestion_title) as EditText
        suggestion_response_text = findViewById(R.id.suggestion_response_text) as TextView





        suggestion_send_button.setOnClickListener {

            var suggestion_body_request = suggestion_corps.getText().toString()
            val suggestion_title_request = suggestion_title.getText().toString()

            //suggestionRequest("suggestion_title_request", "suggestion_body_request" )


            suggestion_response_text.text = "Votre idée a bien été envoyée ! Merci !"
            //println(answer)
        }
    }


    fun suggestionRequest(title : String, body : String)
    {//Do the request to the server
        val queue = Volley.newRequestQueue(this)

        //url = "https://epinotes.core2duo.fr/connect_android.php?mail=" + mail_adress + "&code_verification=" + verification_code + "&requete=idea_box&title=" + title + "&body=" + body
        url = "https://epinotes.core2duo.fr/connect_android.php?mail=thomas.peugnet@epita.fr&code_verification=djhfbezqilbfiuyezbf15q16qreqerg54bj654kuyl654iuys65v1q6fv5atr651grtb65ytrdgn1dsf6h5dhj4ds6b4dn4bds1s681&requete=idea_box&title=test"
        var stringRequest = StringRequest(
            Request.Method.GET, url,
            Response.Listener{ response ->
                answer = response
            },
            {
                fun Context.toast(message: CharSequence) =
                    Toast.makeText(this, "Erreur : Impossible d'effectuer la requete.", Toast.LENGTH_SHORT).show()
            })
        queue.add(stringRequest)
    }
}
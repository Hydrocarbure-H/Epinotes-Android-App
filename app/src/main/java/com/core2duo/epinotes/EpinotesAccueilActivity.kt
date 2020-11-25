package com.core2duo.epinotes

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.nimbusds.jose.Header.parse
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.nio.ByteBuffer
import java.util.concurrent.Executor
import java.util.concurrent.Executors


class EpinotesAccueilActivity : AppCompatActivity() {
    lateinit var request_button : Button
    lateinit var name_text_view : TextView
    lateinit var campus_text_view : TextView
    lateinit var mail : String
    lateinit var request : String
    lateinit var url : String
    lateinit var campus : String
    lateinit var answer : String
    private  val verification_code = "djhfbezqilbfiuyezbf15q16qreqerg54bj654kuyl654iuys65v1q6fv5atr651grtb65ytrdgn1dsf6h5dhj4ds6b4dn4bds1s681";






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_epinotes_accueil2)
        request_button = findViewById(R.id.request_button)
        name_text_view= findViewById(R.id.name_text_view) as TextView
        campus_text_view = findViewById(R.id.campus_text_view) as TextView

        requestToDo("thomas.peugnet@epita.fr", "nom", name_text_view)

        request_button.setOnClickListener {

            //Mail verification;  if mail is in the admin array -> redirection to EpinotesAccueilADMINActvitiy

           // var mail = MainActivity().data_response.getString("mail")

            //requestToDo("thomas.peugnet@epita.fr", "campus", campus_text_view)

            url = "https://epinotes.core2duo.fr/connect_android.php?mail=thomas.peugnet@epita.fr&code_verification=" + verification_code + "&requete=name"
            println(answer)

        }

    }



    fun requestToDo( mail : String, request : String, text_view : TextView)
    {
        val queue = Volley.newRequestQueue(this)

        url = "https://epinotes.core2duo.fr/connect_android.php?mail=" + mail + "&code_verification=" + verification_code + "&requete=" + request

        var stringRequest = StringRequest(Request.Method.GET, url,
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

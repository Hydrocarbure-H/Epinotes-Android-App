package com.core2duo.epinotes

import android.app.Activity
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.nimbusds.jose.Header.parse
import org.json.JSONObject


class EpinotesAccueilActivity : AppCompatActivity() {
    lateinit var request_button : Button
    lateinit var requete_retour_TextView : TextView
    //var data_response : JSONObject = MainActivity().data_response




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_epinotes_accueil2)
        request_button = findViewById(R.id.request_button)
        requete_retour_TextView= findViewById(R.id.requete_text_view) as TextView


        request_button.setOnClickListener {




// Instantiate the RequestQueue.
            val queue = Volley.newRequestQueue(this)
//            var mail = data_response.getString("displayName")
//            println("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF")
//            println(mail)
            var url = "https://epinotes.core2duo.fr/connect_android.php?var="

// Request a string response from the provided URL.
            val stringRequest = StringRequest(Request.Method.GET, url,
                    { response ->
                        // Display the first 500 characters of the response string.
                        requete_retour_TextView.text = response
                    },
                    { requete_retour_TextView.text = "That didn't work!" })

// Add the request to the RequestQueue.
            queue.add(stringRequest)






        }



    }
}




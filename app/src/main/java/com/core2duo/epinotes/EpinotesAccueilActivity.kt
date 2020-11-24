package com.core2duo.epinotes

import android.app.Activity
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.nimbusds.jose.Header.parse
import org.json.JSONObject


class EpinotesAccueilActivity : AppCompatActivity() {
    lateinit var request_button : Button
    lateinit var requete_retour_TextView : TextView
    lateinit var mail : String
    lateinit var request : String
    lateinit var url : String
    lateinit var campus : String
    private  val verification_code = "djhfbezqilbfiuyezbf15q16qreqerg54bj654kuyl654iuys65v1q6fv5atr651grtb65ytrdgn1dsf6h5dhj4ds6b4dn4bds1s681";




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_epinotes_accueil2)
        request_button = findViewById(R.id.request_button)
        requete_retour_TextView= findViewById(R.id.requete_text_view) as TextView


        request_button.setOnClickListener {

            //Mail verification;  if mail is in the admin array -> redirection to EpinotesAccueilADMINActvitiy

            val queue = Volley.newRequestQueue(this)
           // var mail = MainActivity().data_response.getString("mail")


            // Scheme of a typical request. The auth on the server is allowed thanks to the mail and the verification_code. This request scheme will get the campus
            // There is another type of argument : the mySQL request.




            fun requestToDo( mail : String, request : String) : String
            {
                
                lateinit var answer : String
                url = "https://epinotes.core2duo.fr/connect_android.php?mail=" + mail + "&code_verification=" + verification_code + "&requete=" + request

                var stringRequest_campus = StringRequest(Request.Method.GET, url,
                        Response.Listener{
                            response ->
                            var answer = response.toString()
                            println(answer)


                        },
                        {
                            fun Context.toast(message: CharSequence) =
                                    Toast.makeText(this, "Erreur : Impossible d'effectuer la requete.", Toast.LENGTH_SHORT).show()
                        })
                queue.add(stringRequest_campus)
                println(answer)


                return answer

            }
            campus = requestToDo( "thomas.peugnet@epita.fr", "campus")
            println(campus)










        }



    }
}




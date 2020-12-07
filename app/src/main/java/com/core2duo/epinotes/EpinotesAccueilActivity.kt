package com.core2duo.epinotes

import android.app.Activity
import android.content.Context
import android.os.AsyncTask
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.nimbusds.jose.Header.parse
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.nio.ByteBuffer
import java.util.concurrent.Executor
import java.util.concurrent.Executors



class EpinotesAccueilActivity : AppCompatActivity() {
    lateinit var login_imgView : ImageView
    lateinit var annales_et_fiches_button : Button
    lateinit var cours_du_jour_button : Button
    lateinit var suggestion_button : Button
    lateinit var upload_cours_button : Button
    lateinit var examens_button : Button
    lateinit var emploi_du_temps_button : Button
    lateinit var parametres_button : Button

    lateinit var url : String
    lateinit var answer : String
    private  val verification_code = "djhfbezqilbfiuyezbf15q16qreqerg54bj654kuyl654iuys65v1q6fv5atr651grtb65ytrdgn1dsf6h5dhj4ds6b4dn4bds1s681";






    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_epinotes_accueil2)
        login_imgView = findViewById(R.id.login_img) as ImageView
        annales_et_fiches_button = findViewById(R.id.annalesEtFiches_button) as Button
        cours_du_jour_button = findViewById(R.id.coursDuJour_button) as Button
        suggestion_button = findViewById(R.id.suggestion_button) as Button
        upload_cours_button = findViewById(R.id.uploadCours_button) as Button
        examens_button = findViewById(R.id.examens_button) as Button
        emploi_du_temps_button = findViewById(R.id.emploiDuTemps_button) as Button
        parametres_button = findViewById(R.id.parametres_button) as Button

        requestToDo("thomas.peugnet@epita.fr", "login")

            //Mail verification;  if mail is in the admin array -> redirection to EpinotesAccueilADMINActvitiy
           // var mail = MainActivity().data_response.getString("mail")

        //url = "https://epinotes.core2duo.fr/connect_android.php?mail=thomas.peugnet@epita.fr&code_verification=" + verification_code + "&requete=name"
        Picasso.get().load("https://photos.cri.epita.fr/thomas.peugnet").into(login_imgView);

    }



    fun requestToDo( mail : String, request : String)
    {//Do the request to the server
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

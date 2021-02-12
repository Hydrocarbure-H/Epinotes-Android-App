package com.core2duofr.epinotes

/*import kotlinx.serialization.*
import kotlinx.serialization.json.JSON*/

import android.app.Activity
import android.app.AlertDialog
import android.app.Service
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.os.Bundle
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.microsoft.identity.client.*
import com.microsoft.identity.client.exception.MsalClientException
import com.microsoft.identity.client.exception.MsalException
import com.microsoft.identity.client.exception.MsalServiceException
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL
import kotlin.system.exitProcess


public class MainActivity : AppCompatActivity() {
    lateinit var connexion_button: Button
    lateinit var wiki_button: Button
    private  val verification_code = "djhfbezqilbfiuyezbf15q16qreqerg54bj654kuyl654iuys65v1q6fv5atr651grtb65ytrdgn1dsf6h5dhj4ds6b4dn4bds1s681";
    lateinit var url : String


    var is_connected = false
    var context = this
    var connectivity: ConnectivityManager? = null
    var info: NetworkInfo? = null

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connexion_button = findViewById(R.id.connexion_button)
        wiki_button = findViewById(R.id.wiki_button)
        connectivity = context.getSystemService(Service.CONNECTIVITY_SERVICE) as ConnectivityManager

        if (connectivity != null) {
            info = connectivity!!.activeNetworkInfo

            if (info != null) {
                if (info!!.state == NetworkInfo.State.CONNECTED) {
                    Toast.makeText(context, "Bienvenue sur Epinotes ! ", Toast.LENGTH_SHORT).show()
                }
            } else {
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("Connexion Internet Requise !")
                alertDialogBuilder.setMessage("Une connexion internet est requise pour utiliser Epinotes.")
                alertDialogBuilder.setPositiveButton("Quitter") { alertDialogBuilder, which -> this@MainActivity.finish(); exitProcess(0) }
                alertDialogBuilder.show()
            }
        }

        /*
        * Chargement du mail enregistré dans les préférences.
        * Si l'utilisateur n'est pas connecté, le mail est à not_connected.
        * Si l'utilisateur est connecté, mail contient l'adresse mail.
        */

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val mail = preferences.getString("user_mail", "not_connected")

        if (mail != "not_connected") {
            is_connected = true
        }

        /*
        * Ouverture de l'activité de connexion.
        * Vérification que l'utilisateur ne s'est pas déjà connecté.
        */

        connexion_button.setOnClickListener {

            if (mail == "not_connected") {
                val intent_epinotes_authentication: Intent = Intent(this, EpinotesAuthenticationActivity::class.java)
                startActivity(intent_epinotes_authentication)
            }
            if (is_connected == true) {
                val intent_epinotes_access: Intent = Intent(this, EpinotesAccueilActivity::class.java)
                startActivity(intent_epinotes_access)
            }
        }

        wiki_button.setOnClickListener {
            val intent_wiki: Intent = Intent(this, WikiActivity::class.java)
            startActivity(intent_wiki)

        }
    }
}
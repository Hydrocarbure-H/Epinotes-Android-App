package com.core2duofr.epinotes

import android.content.Intent
import android.os.Bundle
import android.os.StrictMode
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL


class EpinotesAuthenticationActivity : AppCompatActivity() {
    private  val verification_code = "djhfbezqilbfiuyezbf15q16qreqerg54bj654kuyl654iuys65v1q6fv5atr651grtb65ytrdgn1dsf6h5dhj4ds6b4dn4bds1s681";
    lateinit var url : String
    lateinit var token_client : EditText
    lateinit var token_verify_button : Button
    lateinit var token_response : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_epinotes_authentication)
        /*
            * Récupération du mail provenant du JSON réponse de Microsoft.
            * Enregistrement du mail dans les paramètres de l'application.
            * Changement de is_connected. L'utilisateur peut désormais se connecter.
            */
        token_client = findViewById(R.id.token_client) as EditText
        token_verify_button = findViewById(R.id.token_verify_button) as Button
        token_response = findViewById(R.id.token_response) as TextView

        token_verify_button.setOnClickListener {

            var token = token_client.getText().toString()

            val policy = StrictMode.ThreadPolicy.Builder().permitNetwork().build()
            StrictMode.setThreadPolicy(policy)

            var email = request(token)
            if (email == "token_invalid")
            {
                Toast.makeText(applicationContext, "Erreur : Connexion EPINOTES impossible... Impossible de récupérer vos données.", Toast.LENGTH_LONG).show()
                token_response.text = "Vous avez deux options : \n - Votre identifiant EpinotesID est incorrect\n - Vous ne vous êtes pas encore inscrits sur le site.\n\nPour rappel : Vous devez obligatoirement vous inscrire sur le site avant d'utiliser l'application."
            }
            else
            {
                Toast.makeText(applicationContext, "Connexion EPINOTES établie !", Toast.LENGTH_SHORT).show()
                val preferences = PreferenceManager.getDefaultSharedPreferences(this)
                val editor = preferences.edit()
                editor.putString("user_mail", email)
                editor.commit()

                token_response.text = "Connexion réussie !"
                Thread.sleep(1000);
                val intent_epinotes_access: Intent = Intent(this, EpinotesAccueilActivity::class.java)
                startActivity(intent_epinotes_access)
            }
        }
    }


    fun request(token: String?): String {
        try {
            url = "https://epinotes.core2duo.fr/connect_android.php?token=" + token + "&code_verification=djhfbezqilbfiuyezbf15q16qreqerg54bj654kuyl654iuys65v1q6fv5atr651grtb65ytrdgn1dsf6h5dhj4ds6b4dn4bds1s681&requete=authentication"
            val memeURL = URL(url)
            val bufferedReader = BufferedReader(InputStreamReader(memeURL.openConnection().getInputStream()))
            var lines: String?
            val response = StringBuffer()
            while (bufferedReader.readLine().also { lines = it } != null) {
                response.append(lines)
            }
            return response.toString()
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }

}
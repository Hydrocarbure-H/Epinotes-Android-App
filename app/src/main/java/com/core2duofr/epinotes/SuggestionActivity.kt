package com.core2duofr.epinotes

import android.os.Bundle
import android.os.StrictMode
import android.preference.PreferenceManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class SuggestionActivity : AppCompatActivity() {

    lateinit var url : String
    private  val verification_code = "djhfbezqilbfiuyezbf15q16qreqerg54bj654kuyl654iuys65v1q6fv5atr651grtb65ytrdgn1dsf6h5dhj4ds6b4dn4bds1s681";

    lateinit var suggestion_send_button : Button
    lateinit var login_imgView : ImageView
    lateinit var suggestion_corps : EditText
    lateinit var suggestion_title : EditText
    lateinit var suggestion_response_text : TextView





    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_suggestion)


// Chargement du mail
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val mail = preferences.getString("user_mail", "not_connected")
// Requêtes au serveur
        val policy = StrictMode.ThreadPolicy.Builder().permitNetwork().build()
        StrictMode.setThreadPolicy(policy)
        var name = request(mail, "name")


        suggestion_send_button = findViewById(R.id.suggestion_send_button) as Button
        suggestion_corps = findViewById(R.id.suggestion_corps) as EditText
        suggestion_title = findViewById(R.id.suggestion_title) as EditText
        suggestion_response_text = findViewById(R.id.suggestion_response_text) as TextView

        suggestion_send_button.setOnClickListener {

            var suggestion_body = suggestion_corps.getText().toString()
            val suggestion_title = suggestion_title.getText().toString()

            var response_suggestion = send_idea(mail, suggestion_body, name, suggestion_title)

            suggestion_response_text.text = response_suggestion
        }
    }

// Récupérer les différentes informations
    fun request(mail_address: String?, request: String): String {
        try {
            url = "https://epinotes.core2duo.fr/connect_android.php?mail=" + mail_address + "&code_verification=" + verification_code + "&requete=" + request
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
// Envoyer une suggestion
    fun send_idea(mail_address: String?, suggestion_body: String, auteur: String, suggestion_title: String): String {
        try {
            url = "https://epinotes.core2duo.fr/connect_android.php?mail=" + mail_address + "&code_verification=" + verification_code + "&requete=send_suggestion&suggestion_body=" + suggestion_body + "&auteur=" + auteur + "&suggestion_title=" + suggestion_title
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
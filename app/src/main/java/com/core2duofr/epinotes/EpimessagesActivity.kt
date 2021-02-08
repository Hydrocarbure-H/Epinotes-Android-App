package com.core2duofr.epinotes

import android.app.ActionBar
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.os.StrictMode
import android.preference.PreferenceManager
import android.util.TypedValue
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL


class EpimessagesActivity : AppCompatActivity() {
    lateinit var url : String
    private  val verification_code = "djhfbezqilbfiuyezbf15q16qreqerg54bj654kuyl654iuys65v1q6fv5atr651grtb65ytrdgn1dsf6h5dhj4ds6b4dn4bds1s681";

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_epimessages)
        var linear_layout = findViewById(R.id.linear_layout) as LinearLayout
        var message_body = findViewById(R.id.epimessage_body) as EditText
        var message_send = findViewById(R.id.epimessage_send_button) as Button
        var message_refresh = findViewById(R.id.epimessage_refresh_button) as Button


// Chargement du mail
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val mail = preferences.getString("user_mail", "not_connected")
// Requêtes au serveur
        val policy = StrictMode.ThreadPolicy.Builder().permitNetwork().build()
        StrictMode.setThreadPolicy(policy)
        var name = request(mail, "name")
        var login = request(mail, "login")
        var promo = request(mail, "promo")
        var campus = request(mail, "campus")




        val json_response = JSONTokener(request(mail, "epimessages")).nextValue() as JSONArray
// Chargement des messages
        for (i in 0 until json_response.length()) {
            var response_json : JSONObject = json_response.getJSONObject(i)
            val auteur = TextView(this)
            val message = TextView(this)
            val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
            params.setMargins(20, 20, 20, 20)
            message.setLayoutParams(params)
// Création TextView Auteur + date
            auteur.setPadding(20, 20, 20, 20)
            auteur.setTextColor(Color.WHITE)
            auteur.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
            auteur.text = response_json.getString("auteur") + " -- " + response_json.getString("date")
// Création TextView Message
            message.setPadding(20, 20, 20, 20)
            message.setTextColor(Color.BLACK)
            message.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
            message.background = getResources().getDrawable(R.drawable.accueil_buttons_background)
            message.text = response_json.getString("message")
// Ajout des 2 TextView dans le linear_layout
            linear_layout.addView(auteur)
            linear_layout.addView(message)
        }
// Actualisation des messages
        message_refresh.setOnClickListener {
            linear_layout.removeAllViews()

            val json_response = JSONTokener(request(mail, "epimessages")).nextValue() as JSONArray
// Chargement des messages
            for (i in 0 until json_response.length()) {
                var response_json : JSONObject = json_response.getJSONObject(i)
                val auteur = TextView(this)
                val message = TextView(this)
                val params: LinearLayout.LayoutParams = LinearLayout.LayoutParams(ActionBar.LayoutParams.WRAP_CONTENT, ActionBar.LayoutParams.WRAP_CONTENT)
                params.setMargins(20, 20, 20, 20)
                message.setLayoutParams(params)
// Création TextView Auteur + date
                auteur.setPadding(20, 20, 20, 20)
                auteur.setTextColor(Color.WHITE)
                auteur.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12f)
                auteur.text = response_json.getString("auteur") + " -- " + response_json.getString("date")
// Création TextView Message
                message.setPadding(20, 20, 20, 20)
                message.setTextColor(Color.BLACK)
                message.setTextSize(TypedValue.COMPLEX_UNIT_SP, 16f)
                message.background = getResources().getDrawable(R.drawable.accueil_buttons_background)
                message.text = response_json.getString("message")
// Ajout des 2 TextView dans le linear_layout
                linear_layout.addView(auteur)
                linear_layout.addView(message)
            }
        }
// Envoi d'un message
        message_send.setOnClickListener {
            var message_send_string = message_body.text.toString()
            var send_message_response = send_message(mail, "send_message", message_send_string, name, login, campus)
            if ( send_message_response != "") {
                Toast.makeText(applicationContext, send_message_response, Toast.LENGTH_LONG).show()
                message_body.setText("")
            }
            else
            {
                Toast.makeText(applicationContext, "Erreur : Impossible d'envoyer le message...", Toast.LENGTH_LONG).show()
            }
            val intent_message_refresh: Intent = Intent(this, EpimessagesActivity::class.java)
            startActivity(intent_message_refresh)
        }


    }

// Récupérer les différents messages
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
// Envoyer un message
    fun send_message(mail_address: String?, request: String, message: String, auteur: String, login: String, campus: String): String {
        try {
            url = "https://epinotes.core2duo.fr/connect_android.php?mail=" + mail_address + "&code_verification=" + verification_code + "&requete=send_message&message=" + message + "&auteur=" + auteur + "&login=" + login + "&campus=" + campus
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
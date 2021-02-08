package com.core2duofr.epinotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.StrictMode
import android.preference.PreferenceManager
import android.widget.TextView
import org.json.JSONObject
import org.json.JSONTokener
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL

class ExamensActivity : AppCompatActivity() {
    lateinit var url : String
    private  val verification_code = "djhfbezqilbfiuyezbf15q16qreqerg54bj654kuyl654iuys65v1q6fv5atr651grtb65ytrdgn1dsf6h5dhj4ds6b4dn4bds1s681";


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_examens)

        var qcm_physique = findViewById(R.id.physique_qcm) as TextView
        var qcm_archi = findViewById(R.id.archi_qcm) as TextView
        var qcm_electronique = findViewById(R.id.electronique_qcm) as TextView
        var qcm_algo = findViewById(R.id.algo_qcm) as TextView
        var qcm_maths = findViewById(R.id.maths_qcm) as TextView
        var qcm_anglais = findViewById(R.id.anglais_qcm) as TextView
        var partiel_physique = findViewById(R.id.physique_partiel) as TextView
        var partiel_archi = findViewById(R.id.archi_partiel) as TextView
        var partiel_electronique = findViewById(R.id.electronique_partiel) as TextView
        var partiel_algo = findViewById(R.id.algo_partiel) as TextView
        var partiel_maths = findViewById(R.id.maths_partiel) as TextView
        var partiel_anglais = findViewById(R.id.anglais_partiel) as TextView
        var midterm_physique = findViewById(R.id.physique_midterm) as TextView
        var midterm_archi = findViewById(R.id.archi_midterm) as TextView
        var midterm_electronique = findViewById(R.id.electronique_midterm) as TextView
        var midterm_algo = findViewById(R.id.algo_midterm) as TextView
        var midterm_maths = findViewById(R.id.maths_midterm) as TextView
        var midterm_anglais = findViewById(R.id.anglais_midterm) as TextView



// Chargement du mail
        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val mail = preferences.getString("user_mail", "not_connected")
// Requêtes au serveur
        val policy = StrictMode.ThreadPolicy.Builder().permitNetwork().build()
        StrictMode.setThreadPolicy(policy)
        var promo = request(mail, "promo")

//Chargement du programme des Examens

        var json_response_qcm = JSONTokener(request(mail, "examens_qcm_spe")).nextValue() as JSONObject
        var json_response_midterm = JSONTokener(request(mail, "examens_midterm_spe")).nextValue() as JSONObject
        var json_response_partiel = JSONTokener(request(mail, "examens_partiel_spe")).nextValue() as JSONObject

        if (promo == "2024")
        {
             var json_response_qcm = JSONTokener(request(mail, "examens_qcm_spe")).nextValue() as JSONObject
             var json_response_midterm = JSONTokener(request(mail, "examens_midterm_spe")).nextValue() as JSONObject
             var json_response_partiel = JSONTokener(request(mail, "examens_partiel_spe")).nextValue() as JSONObject
        }
        if (promo == "2025")
        {
             var json_response_qcm = JSONTokener(request(mail, "examens_qcm_sup")).nextValue() as JSONObject
             var json_response_midterm = JSONTokener(request(mail, "examens_midterm_sup")).nextValue() as JSONObject
             var json_response_partiel = JSONTokener(request(mail, "examens_partiel_sup")).nextValue() as JSONObject
        }
        if (promo != "2024" && promo != "2025")
        {
            val intent_epinotes_access : Intent =  Intent(this, EpinotesAccueilActivity::class.java)
            startActivity(intent_epinotes_access)
        }
// Mise à jour des TextView
            qcm_physique.text = json_response_qcm.getString("physique")
            qcm_archi.text = json_response_qcm.getString("archi")
            qcm_electronique.text = json_response_qcm.getString("elec")
            qcm_algo.text = json_response_qcm.getString("algo")
            qcm_maths.text = json_response_qcm.getString("maths")
            qcm_anglais.text = json_response_qcm.getString("anglais")

            midterm_physique.text = json_response_midterm.getString("physique")
            midterm_archi.text = json_response_midterm.getString("archi")
            midterm_electronique.text = json_response_midterm.getString("elec")
            midterm_algo.text = json_response_midterm.getString("algo")
            midterm_maths.text = json_response_midterm.getString("maths")
            midterm_anglais.text = json_response_midterm.getString("anglais")

            partiel_physique.text = json_response_partiel.getString("physique")
            partiel_archi.text = json_response_partiel.getString("archi")
            partiel_electronique.text = json_response_partiel.getString("elec")
            partiel_algo.text = json_response_partiel.getString("algo")
            partiel_maths.text = json_response_partiel.getString("maths")
            partiel_anglais.text = json_response_partiel.getString("anglais")




    }

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
}
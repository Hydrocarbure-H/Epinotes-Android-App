package com.core2duofr.epinotes

import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.StrictMode
import android.preference.PreferenceManager
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.squareup.picasso.Picasso
import org.json.JSONObject
import java.io.BufferedReader
import java.io.InputStreamReader
import java.net.URL


class EpinotesAccueilActivity : AppCompatActivity() {
    lateinit var login_imgView : ImageView
    lateinit var annales_et_fiches_button : Button
    lateinit var cours_du_jour_button : Button
    lateinit var suggestion_button : Button
    lateinit var upload_cours_button : Button
    lateinit var examens_button : Button
    lateinit var epimessages_button : Button
    lateinit var emploi_du_temps_button : Button
    lateinit var notes_button : Button
    lateinit var pegasus_button : Button
    lateinit var moodle_button : Button
    lateinit var ionis_button : Button
    lateinit var parametres_button : Button
    lateinit var drivetoulouse_button : Button
    lateinit var json_response : JSONObject
    lateinit var answer : String


    lateinit var mail_address: String
    lateinit var login: String
    lateinit var edt_id: String
    lateinit var name: String
    var url_activity = "https://toulouse.epita.fr/plannings/toulouse/9a05d9d2264bff818afca506c7fb8ec0.php"

    lateinit var url : String
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
        epimessages_button = findViewById(R.id.epimessages_button) as Button
        emploi_du_temps_button = findViewById(R.id.emploiDuTemps_button) as Button
        notes_button = findViewById(R.id.notes_button) as Button
        pegasus_button = findViewById(R.id.pegasus_button) as Button
        ionis_button = findViewById(R.id.ionis_button) as Button
        moodle_button = findViewById(R.id.moodle_button) as Button
        drivetoulouse_button = findViewById(R.id.drivetoulouse_button) as Button
        parametres_button = findViewById(R.id.parametres_button) as Button


        emploi_du_temps_button.setOnClickListener {
//            val intent_emploi_du_temps: Intent = Intent(this, WebSiteActivity::class.java)
//            startActivity(intent_emploi_du_temps) TEST
            val i: Intent?
            try {
                i = packageManager.getLaunchIntentForPackage("com.google.android.apps.calendar")
                if (i == null) throw PackageManager.NameNotFoundException()
                i.addCategory(Intent.CATEGORY_LAUNCHER)
                startActivity(i)
            } catch (e: PackageManager.NameNotFoundException) {
                // affiche que l'appli n'es pas présente sur le tel
            }
            Toast.makeText(applicationContext,"Pas encore disponible... Google Calendar n'est pas opérationnel...! :-)", Toast.LENGTH_LONG).show()

        }
        suggestion_button.setOnClickListener {
            val intent_suggestion: Intent = Intent(this, SuggestionActivity::class.java)
            startActivity(intent_suggestion)
        }
        annales_et_fiches_button.setOnClickListener {
            val intent_annales: Intent = Intent(this, AnnalesEtFichesActivity::class.java)
            startActivity(intent_annales)
        }
        cours_du_jour_button.setOnClickListener {
            val intent_cours: Intent = Intent(this, CoursDuJourActivity::class.java)
            startActivity(intent_cours)
        }
        examens_button.setOnClickListener {
            val intent_examens: Intent = Intent(this, ExamensActivity::class.java)
            startActivity(intent_examens)
        }
        epimessages_button.setOnClickListener {
            val intent_epimessage: Intent = Intent(this, EpimessagesActivity::class.java)
            startActivity(intent_epimessage)
        }
        upload_cours_button.setOnClickListener {
//            val intent_upload: Intent = Intent(this, UploadActivity::class.java)
//            startActivity(intent_upload)
            Toast.makeText(applicationContext,"Pas encore disponible... On y travaille !!", Toast.LENGTH_LONG).show()
        }
        notes_button.setOnClickListener {
            val intent_notes: Intent = Intent(this, NotesActivity::class.java)
            startActivity(intent_notes)
            Toast.makeText(applicationContext,"Version BETA : En cours de développement.", Toast.LENGTH_LONG).show()

        }
        pegasus_button.setOnClickListener {
            val intent_pegasus: Intent = Intent(this, PegasusActivity::class.java)
            startActivity(intent_pegasus)
        }
        moodle_button.setOnClickListener {
            val intent_moodle: Intent = Intent(this, MoodleActivity::class.java)
            startActivity(intent_moodle)
            Toast.makeText(applicationContext,"(BETA) : Une seule connexion est nécessaire. La session est conservée en cache.", Toast.LENGTH_LONG).show()
        }
        ionis_button.setOnClickListener {
            val intent_ionis: Intent = Intent(this, IonisActivity::class.java)
            startActivity(intent_ionis)
        }
        drivetoulouse_button.setOnClickListener {
            val intent_drivetoulouse: Intent = Intent(this, DriveToulouseActivity::class.java)
            startActivity(intent_drivetoulouse)
        }
        parametres_button.setOnClickListener {
            val intent_parametres: Intent = Intent(this, ParametresActivity::class.java)
            startActivity(intent_parametres)
//            Toast.makeText(applicationContext,"Pas encore disponible dans votre région. Ça le sera surement pour la prochaine mise à jour...! :-)", Toast.LENGTH_LONG).show()
        }
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
        var id_calendar = request(mail, "id_calendar")


        Picasso.get().load("https://photos.cri.epita.fr/" + login).into(login_imgView);
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

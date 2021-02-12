package com.core2duofr.epinotes

import android.os.Bundle
import android.os.Environment
import android.preference.PreferenceManager
import android.view.View
import android.widget.Button
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import java.io.BufferedReader
import java.io.File
import java.io.InputStreamReader
import java.net.URL
import java.security.AccessController.getContext


class ParametresActivity : AppCompatActivity() {
    lateinit var params_theme_button: Button
    lateinit var params_theme_ionis_radio_group: RadioGroup
    lateinit var params_theme_pegasus_radio_group: RadioGroup
    lateinit var params_theme_moodle_radio_group: RadioGroup
    lateinit var params_theme_epitashare_radio_group: RadioGroup
    lateinit var url : String
    private  val verification_code = "djhfbezqilbfiuyezbf15q16qreqerg54bj654kuyl654iuys65v1q6fv5atr651grtb65ytrdgn1dsf6h5dhj4ds6b4dn4bds1s681";
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_parametres)

        params_theme_button = findViewById(R.id.webview_theme_button) as Button
        params_theme_pegasus_radio_group = findViewById(R.id.webview_theme_radio_group_pegasus) as RadioGroup
        params_theme_moodle_radio_group = findViewById(R.id.webview_theme_radio_group_moodle) as RadioGroup
        params_theme_ionis_radio_group = findViewById(R.id.webview_theme_radio_group_ionis) as RadioGroup
        params_theme_epitashare_radio_group = findViewById(R.id.webview_theme_radio_group_epitashare) as RadioGroup

        params_theme_button.setOnClickListener{
            // Get the checked radio button id from radio group
            var pegasus_id: Int = params_theme_pegasus_radio_group.checkedRadioButtonId
            if (pegasus_id!=-1){ // If any radio button checked from radio group
                // Get the instance of radio button using pegasus_id
                val radio: RadioButton = findViewById(pegasus_id)
                val preferences = PreferenceManager.getDefaultSharedPreferences(this)
                val editor = preferences.edit()
                editor.putString("web_view_theme_pegasus", radio.text.toString())
                editor.commit()
            }
            var moodle_id: Int = params_theme_moodle_radio_group.checkedRadioButtonId
            if (moodle_id!=-1){ // If any radio button checked from radio group
                // Get the instance of radio button using moodle_id
                val radio: RadioButton = findViewById(moodle_id)
                val preferences = PreferenceManager.getDefaultSharedPreferences(this)
                val editor = preferences.edit()
                editor.putString("web_view_theme_moodle", radio.text.toString())
                editor.commit()
            }
            var ionis_id: Int = params_theme_ionis_radio_group.checkedRadioButtonId
            if (ionis_id!=-1){ // If any radio button checked from radio group
                // Get the instance of radio button using ionis_id
                val radio: RadioButton = findViewById(ionis_id)
                val preferences = PreferenceManager.getDefaultSharedPreferences(this)
                val editor = preferences.edit()
                editor.putString("web_view_theme_ionis", radio.text.toString())
                editor.commit()
            }
            var epitashare_id: Int = params_theme_epitashare_radio_group.checkedRadioButtonId
            if (epitashare_id!=-1){ // If any radio button checked from radio group
                // Get the instance of radio button using epitashare_id
                val radio: RadioButton = findViewById(epitashare_id)
                val preferences = PreferenceManager.getDefaultSharedPreferences(this)
                val editor = preferences.edit()
                editor.putString("web_view_theme_epitashare", radio.text.toString())
                editor.commit()
            }
            Toast.makeText(applicationContext, "Thèmes sauvegardés !", Toast.LENGTH_SHORT).show()

        }

    }

//    fun radio_button_click_pegasus(view: View){
//        // Get the clicked radio button instance
//        val radio: RadioButton = findViewById(params_theme_pegasus_radio_group.checkedRadioButtonId)
//    }
//    fun radio_button_click_moodle(view: View){
//        // Get the clicked radio button instance
//        val radio: RadioButton = findViewById(params_theme_moodle_radio_group.checkedRadioButtonId)
//        Toast.makeText(applicationContext,"On click : ${radio.text}",
//            Toast.LENGTH_SHORT).show()
//    }
//    fun radio_button_click_ionis(view: View){
//        // Get the clicked radio button instance
//        val radio: RadioButton = findViewById(params_theme_ionis_radio_group.checkedRadioButtonId)
//        Toast.makeText(applicationContext,"On click : ${radio.text}",
//            Toast.LENGTH_SHORT).show()
//    }

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
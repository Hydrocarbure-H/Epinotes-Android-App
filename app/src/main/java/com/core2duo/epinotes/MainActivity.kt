package com.core2duo.epinotes

/*import kotlinx.serialization.*
import kotlinx.serialization.json.JSON*/

import android.app.Activity
import android.content.Context
import android.content.Intent
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



public class MainActivity : AppCompatActivity() {
    lateinit var connexion_button : Button
    lateinit var epinotes_access_button : Button
    lateinit var data_response : JSONObject


    var mSingleAccountApp: ISingleAccountPublicClientApplication? = null
    var is_connected = false

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        connexion_button = findViewById(R.id.connexion_button)
        epinotes_access_button = findViewById(R.id.epinotes_access_button)


        PublicClientApplication.createSingleAccountPublicClientApplication(
                this as Context,
                R.raw.auth_config_single_account,
                object : IPublicClientApplication.ISingleAccountApplicationCreatedListener {
                    override fun onCreated(application: ISingleAccountPublicClientApplication) {
                        mSingleAccountApp = application
                        loadAccount()
                    }
                    override fun onError(exception: MsalException) {
                    }
                })

        /*
        * Chargement du mail enregistré dans les préférences.
        * Si l'utilisateur n'est pas connecté, le mail est à not_connected.
        * Si l'utilisateur est connecté, mail contient l'adresse mail.
        */

        val preferences = PreferenceManager.getDefaultSharedPreferences(this)
        val mail = preferences.getString("user_mail", "not_connected")

        if (mail != "not_connected")
        {
            is_connected = true
        }

        /*
        * Ouverture de l'activité de connexion.
        * Vérification que l'utilisateur ne s'est pas déjà connecté.
        */

        connexion_button.setOnClickListener {

            if (mail == "not_connected")
            {
                mSingleAccountApp!!.signIn(this as Activity, "", arrayOf("user.read"), getAuthInteractiveCallback())
            }
            else
            {
                Toast.makeText(applicationContext,"Vous êtes déjà connecté !",Toast.LENGTH_LONG).show()
            }
        }

        /*
        * Ouverture de l'activité Accueil Epinotes.
        * Vérification que l'utilisateur est déjà connecté.
        */

        epinotes_access_button.setOnClickListener {

            if (is_connected)
            {
                val intent_epinotes_access : Intent =  Intent(this, EpinotesAccueilActivity::class.java)
                startActivity(intent_epinotes_access)
            }
            else
            {
                Toast.makeText(applicationContext,"Vous n'êtes pas encore connecté !",Toast.LENGTH_LONG).show()
            }


        }

    }



    private fun getAuthInteractiveCallback(): AuthenticationCallback {
        return object : AuthenticationCallback {

            override fun onSuccess(authenticationResult: IAuthenticationResult) {

                connexion_button.isEnabled = true

                callGraphAPI(authenticationResult)
            }

            override fun onError(exception: MsalException) {

                if (exception is MsalClientException) {
                    /* Exception inside MSAL, more info inside MsalError.java */
                } else if (exception is MsalServiceException) {
                    /* Exception when communicating with the STS, likely config issue */
                }
            }

            override fun onCancel() {
                /* User canceled the authentication */
            }
        }
    }

     fun callGraphAPI(authenticationResult: IAuthenticationResult) {
        MSGraphRequestWrapper.callGraphAPIWithVolley(
                this as Context,
                "https://graph.microsoft.com/v1.0/me",
                authenticationResult.accessToken,
                { response ->

                    convertGraphResult(response)
                },
                { error ->

                })

    }
    private fun loadAccount() {
        if (mSingleAccountApp == null) {
            return
        }

        mSingleAccountApp!!.getCurrentAccountAsync(object :
                ISingleAccountPublicClientApplication.CurrentAccountCallback {
            override fun onAccountLoaded(activeAccount: IAccount?) {
                updateUI(activeAccount)
            }

            override fun onAccountChanged(priorAccount: IAccount?, currentAccount: IAccount?) {
                if (currentAccount == null) {
                    performOperationOnSignOut()
                }
            }
            override fun onError(exception: MsalException) {
//                txt_log.text = exception.toString()
            }
        })
    }

    private fun convertGraphResult(graphResponse: JSONObject) {
        data_response = graphResponse


        /*
        * Récupération du mail provenant du JSON réponse de Microsoft.
        * Enregistrement du mail dans les paramètres de l'application.
        * Changement de is_connected. L'utilisateur peut désormais se connecter.
        */

        var mail = data_response.getString("mail")

        if (mail != null)
        {
            Toast.makeText(applicationContext,"Connexion EPITA établie !",Toast.LENGTH_SHORT).show()
            val preferences = PreferenceManager.getDefaultSharedPreferences(this)
            val editor = preferences.edit()
            editor.putString("user_mail", mail)
            editor.commit()
            is_connected = true
        }
        else
        {
            Toast.makeText(applicationContext,"Erreur : Connexion EPITA impossible... Impossible de récupérer vos données.",Toast.LENGTH_LONG).show()
        }

    }

    private fun updateUI(account: IAccount?) {

        if (account != null) {

            println("***************** UpdateUI")
        } else {

            println("***************** UpdateUI")
        }
    }
    private fun performOperationOnSignOut() {
        val signOutText = "Signed Out."
//    current_user.text = ""
        Toast.makeText(this, signOutText, Toast.LENGTH_SHORT)
                .show()
    }


}
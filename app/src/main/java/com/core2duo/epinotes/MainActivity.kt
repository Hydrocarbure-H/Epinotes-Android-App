package com.core2duo.epinotes

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import com.android.volley.Response
import com.microsoft.identity.client.*
import com.microsoft.identity.client.exception.MsalClientException
import com.microsoft.identity.client.exception.MsalException
import com.microsoft.identity.client.exception.MsalServiceException
import com.microsoft.identity.client.exception.MsalUiRequiredException
/*import kotlinx.serialization.*
import kotlinx.serialization.json.JSON*/
import org.json.JSONObject

public class MainActivity : AppCompatActivity() {
    lateinit var connexion_button : Button
    lateinit var epinotes_access_button : Button
    lateinit var data_response : JSONObject

    var mSingleAccountApp: ISingleAccountPublicClientApplication? = null


    override fun onCreate(savedInstanceState: Bundle?) {



        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        PublicClientApplication.createSingleAccountPublicClientApplication(
                this as Context,
                R.raw.auth_config_single_account,
                object : IPublicClientApplication.ISingleAccountApplicationCreatedListener {
                    override fun onCreated(application: ISingleAccountPublicClientApplication) {
                        /**
                         * This test app assumes that the app is only going to support one account.
                         * This requires "account_mode" : "SINGLE" in the config json file.
                         *
                         */
                        mSingleAccountApp = application

                        loadAccount()
                    }

                    override fun onError(exception: MsalException) {
//                    txt_log.text = exception.toString()
                    }
                })

        connexion_button = findViewById(R.id.connexion_button)
        connexion_button.setOnClickListener {

           mSingleAccountApp!!.signIn(this as Activity, "", arrayOf("user.read"), getAuthInteractiveCallback())



        }

        epinotes_access_button = findViewById(R.id.epinotes_access_button)
        val intent_epinotes_access : Intent =  Intent(this,EpinotesAccueilActivity::class.java)
        epinotes_access_button.setOnClickListener {

           startActivity(intent_epinotes_access)

        }

    }



    private fun getAuthInteractiveCallback(): AuthenticationCallback {
        return object : AuthenticationCallback {

            override fun onSuccess(authenticationResult: IAuthenticationResult) {
                /* Successfully got a token, use it to call a protected resource - MSGraph */
//                Log.d(TAG, "Successfully authenticated")
//                Log.d(TAG, "ID Token: " + authenticationResult.account.claims!!["id_token"])

                /* Update account */
                connexion_button.isEnabled = true

                /* call graph */
                callGraphAPI(authenticationResult)
            }

            override fun onError(exception: MsalException) {
                /* Failed to acquireToken */
//                Log.d(TAG, "Authentication failed: $exception")
//                displayError(exception)

                if (exception is MsalClientException) {
                    /* Exception inside MSAL, more info inside MsalError.java */
                } else if (exception is MsalServiceException) {
                    /* Exception when communicating with the STS, likely config issue */
                }
            }

            override fun onCancel() {
                /* User canceled the authentication */
//                Log.d(TAG, "User cancelled login.")
            }
        }
    }

     fun callGraphAPI(authenticationResult: IAuthenticationResult) {
        MSGraphRequestWrapper.callGraphAPIWithVolley(
                this as Context,
                "https://graph.microsoft.com/v1.0/me",
//            msgraph_url.text.toString(),
                authenticationResult.accessToken,
                { response ->
                    /* Successfully called graph, process data and send to UI */
//                Log.d(TAG, "Response: $response")
                    convertGraphResult(response)
                },
                { error ->
//                Log.d(TAG, "Error: $error")"J'ai appuye sur le bouton."
//                displayError(error)
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
                    // Perform a cleanup task as the signed-in account changed.
                    performOperationOnSignOut()
                }
            }

            override fun onError(exception: MsalException) {
//                txt_log.text = exception.toString()
            }
        })
    }

    private fun convertGraphResult(graphResponse: JSONObject) {
        println("Recuperation du JSON")
        // Rendre le JSON accessible
        data_response = graphResponse
        // Prepaprer le JSON a etre ecrit dans un fichier texte
        var data_respons_stringe = graphResponse.toString()

    }

    private fun updateUI(account: IAccount?) {

        if (account != null) {
//            btn_signIn.isEnabled = false
//            btn_removeAccount.isEnabled = true
//            btn_callGraphInteractively.isEnabled = true
//            btn_callGraphSilently.isEnabled = true
//            current_user.text = account.username
            println("***************** UpdateUI")
        } else {
//            btn_signIn.isEnabled = true
//            btn_removeAccount.isEnabled = false
//            btn_callGraphInteractively.isEnabled = false
//            btn_callGraphSilently.isEnabled = false
//            current_user.text = ""
            println("***************** UpdateUI")
        }
    }
    private fun performOperationOnSignOut() {
        val signOutText = "Signed Out."
//    current_user.text = ""
        Toast.makeText(this, signOutText, Toast.LENGTH_SHORT)
                .show()
    }

    /*private fun updateUI(connected : Boolean) {

        if (connected != false) {
            connexion_button.isEnabled = false
            epinotes_access_button.isEnabled = true

        } else {
            connexion_button.isEnabled = true
            epinotes_access_button.isEnabled = false

        }
    }*/
}
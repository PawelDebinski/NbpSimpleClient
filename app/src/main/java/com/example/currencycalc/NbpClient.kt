package com.example.currencycalc

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class NbpClient: AsyncTask<String, Void, String>() {
    private val TAG = "NbpClient"

    override fun doInBackground(vararg params: String?): String {
        Log.d(TAG, ".doInBackground started")
        if (params[0].isNullOrEmpty()) {
            return "No URL specified"
        }

        try {
            return URL(params[0]).readText()
        } catch (e: Exception) {
            val errorMessage = when(e) {
                is MalformedURLException -> "doInBackground: Invalid URL ${e.message}"
                is IOException -> "doInBackground: IO Exception reading data: ${e.message}"
                is SecurityException -> "doInBackground: Security exception: ${e.message}"
                else -> "Unknown error: ${e.message}"
            }
            Log.e(TAG, errorMessage)
            return errorMessage
        }
    }


    override fun onPostExecute(result: String?) {
        super.onPostExecute(result)

        if (result.isNullOrEmpty()) {
            return
        }
        Log.d(TAG, result)
    }
}
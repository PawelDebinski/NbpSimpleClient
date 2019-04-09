package com.example.currencycalc

import android.os.AsyncTask
import android.util.Log
import java.io.IOException
import java.net.MalformedURLException
import java.net.URL

class NbpClient(private val listener: OnDownloadComplete): AsyncTask<String, Void, String>() {
    private val TAG = "NbpClient"

    interface OnDownloadComplete {
        fun onDownloadComplete(jsonString: String)
    }

    override fun doInBackground(vararg params: String?): String {
        Log.d(TAG, ".doInBackground started")

        if (params[0].isNullOrEmpty()) {
            return "No URL specified"
        }
        return returnJsonString(params[0])
    }

    private fun returnJsonString(stringUrl: String?): String {
        return try {
            URL(stringUrl).readText()
        } catch (e: Exception) {
            val errorMessage = when (e) {
                is MalformedURLException -> "doInBackground: Invalid URL ${e.message}"
                is IOException -> "doInBackground: IO Exception reading data: ${e.message}"
                is SecurityException -> "doInBackground: Security exception: ${e.message}"
                else -> "Unknown error: ${e.message}"
            }
            Log.e(TAG, errorMessage)
            errorMessage
        }
    }

    override fun onPostExecute(result: String) {
        Log.d(TAG, ".onPostExecute started")
            listener.onDownloadComplete(result)
    }
}
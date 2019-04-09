package com.example.currencycalc

import android.os.AsyncTask
import android.util.Log
import org.json.JSONArray
import org.json.JSONException

class JsonParser(private val listener: OnJsonAvailable) : AsyncTask<String, Void, ArrayList<CurrencyData>>() {
    private val TAG = "JsonParser"

    interface OnJsonAvailable {
        fun onJsonAvailable(jsonData: ArrayList<CurrencyData>)
    }

    override fun doInBackground(vararg params: String?): ArrayList<CurrencyData> {
        Log.d(TAG, ".doInBackground started")

        val currencyList = ArrayList<CurrencyData>()

        convertJsonToCurrencyList(params, currencyList)
        Log.d(TAG, ".doInBackground ends")
        return currencyList
    }

    private fun convertJsonToCurrencyList(params: Array<out String?>, currencyList: ArrayList<CurrencyData>) {
        try {
            val json = JSONArray(params[0])
            val jsonObject = json.getJSONObject(0)
            val ratesArray = jsonObject.getJSONArray("rates")

            for (i in 0 until ratesArray.length()) {
                val rate = ratesArray.getJSONObject(i)
                val currency = rate.getString("currency")
                val code = rate.getString("code")
                val averageRate = rate.getString("mid")

                val currencyData = CurrencyData(currency, code, averageRate)
                currencyList.add(currencyData)
                Log.d(TAG, "Currency: $currencyData")
            }
        } catch (e: JSONException) {
            e.printStackTrace()
            Log.e(TAG, ".doInBackground: Error processing Json data. ${e.message}")
            cancel(true)
        }
    }

    override fun onPostExecute(result: ArrayList<CurrencyData>) {
        listener.onJsonAvailable(result)
    }
}
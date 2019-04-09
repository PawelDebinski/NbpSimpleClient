package com.example.currencycalc

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.View
import android.widget.ListView
import kotlinx.android.synthetic.main.activity_main.*
import android.widget.ArrayAdapter

class MainActivity : AppCompatActivity(), NbpClient.OnDownloadComplete, JsonParser.OnJsonAvailable {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, ".onCreate started")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    fun onButtonClicked(view: View) {
        Log.d(TAG, ".onButtonClicked started")
        val date = dateField.text

        val stringUrl = "http://api.nbp.pl/api/exchangerates/tables/A/$date/?format=json"
        val nbpClient = NbpClient(this)
        nbpClient.execute(stringUrl)
    }

    override fun onDownloadComplete(jsonString: String) {
        Log.d(TAG, ".onDownloadComplete started")
        val jsonParser = JsonParser(this)
        jsonParser.execute(jsonString)
    }

    override fun onJsonAvailable(jsonData: ArrayList<CurrencyData>) {
        Log.d(TAG, ".onJsonAvailable started")

        val listView = findViewById<ListView>(R.id.list_view)
        val adapter = ArrayAdapter<CurrencyData>(this, android.R.layout.simple_list_item_1, jsonData)
        listView.adapter = adapter
    }
}

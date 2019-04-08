package com.example.currencycalc

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log

class MainActivity : AppCompatActivity() {
    private val TAG = "MainActivity"

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(TAG, ".onCreate started")
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val stringUrl = "http://api.nbp.pl/api/exchangerates/tables/A/2019-03-13/?format=json"
        val nbpClient = NbpClient()
        nbpClient.execute(stringUrl)
    }
}

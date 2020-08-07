package com.hfridland.currencyconverter

import android.content.Context
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import org.json.JSONObject
import java.io.Serializable
import java.net.URL
import java.util.*
import android.content.Context.MODE_PRIVATE
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream


object CurrencyDataProcessor {
    data class CurrencyData(val date: Date, val quotes: Map<String, Double>) : Serializable

    fun getKoeff(currencyData: CurrencyData?, codeFrom: String, codeTo: String): Double {
        currencyData?.let {
            val koeffFrom = 1 / it.quotes.getValue("USD$codeFrom")
            val koeffTo = it.quotes.getValue("USD$codeTo")
            return koeffFrom * koeffTo
        }
        throw Exception("Operation can't be processed")
    }


    fun createCurrencyData(currencyJsonData: String): CurrencyData? {
        val mainObj = JSONObject(currencyJsonData)
        if (!mainObj.getBoolean("success"))
            return null
        val ts: Long = mainObj.getString("timestamp").toLong() * 1000
        val date = Date(ts)
        val quotesProp = mutableMapOf<String, Double>()
        val quotes = mainObj.getJSONObject("quotes")
        for(key in quotes.keys()) {
            val q = quotes.getDouble(key)
            quotesProp[key] = q
        }
        return CurrencyData(date, quotesProp.toMap())
    }


}

private const val CURRENCY_DATA_API = "http://api.currencylayer.com/live?access_key=763b43af77d9f45d9d0094e7641f6cfc"

fun fetchCurrencyJsonData(): Deferred<CurrencyDataProcessor.CurrencyData?> {
    return async {
        val currencyJsonData = URL(CURRENCY_DATA_API).readText()
        val ret = CurrencyDataProcessor.createCurrencyData(currencyJsonData)
        ret
    }
}


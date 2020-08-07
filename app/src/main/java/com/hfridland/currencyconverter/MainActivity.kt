package com.hfridland.currencyconverter

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.launch
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.util.*
import android.app.Activity
import android.view.Menu
import android.view.MenuItem


class MainActivity : AppCompatActivity() {
    private var currencyData: CurrencyDataProcessor.CurrencyData? = null

    private val REQUEST_CODE_FROM = 0
    private val REQUEST_CODE_TO = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Params.load(this)
        savedInstanceState?.let {
            val isCurrencyDataExists = it.getBoolean("isCurrencyDataExists")
            if (isCurrencyDataExists)
                currencyData = it.getSerializable("CurrencyData") as CurrencyDataProcessor.CurrencyData
        }
        if (currencyData == null)
            requestCurrencyData()



        btnCalc.setOnClickListener{
            try {
                val k: Double? = CurrencyDataProcessor.getKoeff(currencyData, Params.data.curCodeFrom, Params.data.curCodeTo)
                k?.let {
                    val res: Double = etFromVal.text.toString().toDouble() * k
                    tvCurVal.text = "%.2f".format(res)
                }
            } catch(ex: NoSuchElementException) {
                Toast.makeText(applicationContext,"Currency does not found in database", Toast.LENGTH_LONG).show()
            }
        }

        btnChangeCurFrom.setOnClickListener{
            val intent = Intent(this, SelCurrencyActivity::class.java)
            intent.putExtra("code", Params.data.curCodeFrom)
            startActivityForResult(intent, REQUEST_CODE_FROM)
        }

        btnChangeCurTo.setOnClickListener{
            val intent = Intent(this, SelCurrencyActivity::class.java)
            intent.putExtra("code", Params.data.curCodeTo)
            startActivityForResult(intent, REQUEST_CODE_TO)
        }

        //miA

        displayData()
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)

        savedInstanceState.let {
            it.putBoolean("isCurrencyDataExists", currencyData != null)
            if (currencyData != null)
                it.putSerializable("CurrencyData", currencyData)
        }
    }

    private fun getCurString(curCode: String): String {
        val code = CurrencyCodes.data[curCode]?.code
        val descr = CurrencyCodes.data[curCode]?.descr
        return "$code - $descr"
    }

    private fun displayData() {
        tvCurFrom.text = getCurString(Params.data.curCodeFrom)
        tvCurTo.text = getCurString(Params.data.curCodeTo)
    }

    private fun requestCurrencyData() {
        try {
            openFileInput("data.dat").use {
                ObjectInputStream(it).use {
                    currencyData = it.readObject() as CurrencyDataProcessor.CurrencyData
                    currencyData = currencyData?.let{
                        // if after last request passed > 1 day need to request data
                        if ((Date().time - (it.date.time)) / (1000 * 60 * 60 * 24) > 1) null else it
                    }
                }
            }
        } catch (ex: IOException) { }

        if (currencyData == null) {
            launch(UI) {
                val data = fetchCurrencyJsonData().await()
                currencyData = data

                openFileOutput("data.dat", Context.MODE_PRIVATE).use {
                    ObjectOutputStream(it).use {
                        it.writeObject(currencyData)
                    }
                }
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (resultCode != Activity.RESULT_OK) {
            return
        }
        when(requestCode) {
            REQUEST_CODE_FROM -> {
                data?.let {
                    Params.data.curCodeFrom = it.getStringExtra("code")
                    tvCurFrom.text = getCurString(Params.data.curCodeFrom)
                }
            }
            REQUEST_CODE_TO -> {
                data?.let {
                    Params.data.curCodeTo = it.getStringExtra("code")
                    tvCurTo.text = getCurString(Params.data.curCodeTo)
                }
            }
        }
        Params.save(this)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.miAbout -> {
                startActivity(Intent(this, AboutActivity::class.java))
            }
        }
        return super.onOptionsItemSelected(item)
    }

}

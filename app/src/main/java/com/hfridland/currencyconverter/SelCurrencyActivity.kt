package com.hfridland.currencyconverter

import android.os.Bundle
import android.app.Activity
import android.widget.ListView

import kotlinx.android.synthetic.main.activity_sel_currency.*
import android.content.Intent



class SelCurrencyActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sel_currency)

        var code = intent.getStringExtra("code")
        savedInstanceState?.let {
            code = it.getString("code")
        }

        val adapter: CurrencyCodesAdapter = CurrencyCodesAdapter(this, R.layout.currency_item, CurrencyCodes.data.values.toList(), code)
        lv.adapter = adapter
        lv.choiceMode = ListView.CHOICE_MODE_SINGLE
        //lv.setItemChecked(40, true)
        lv.smoothScrollToPosition(CurrencyCodes.data.values.toList().indexOfFirst { it.code == code })

        btnSearch.setOnClickListener{
            val selInd = (lv.adapter as CurrencyCodesAdapter).search(etSearch.text.toString())
            if (selInd >= 0)
                lv.smoothScrollToPosition(selInd)
        }

        btnCancel.setOnClickListener{
            setResult(Activity.RESULT_CANCELED)
            finish()
        }
        btnOk.setOnClickListener{
            setResult((lv.adapter as CurrencyCodesAdapter).selCurCode)
            finish()
        }
    }

    override fun onSaveInstanceState(savedInstanceState: Bundle) {
        super.onSaveInstanceState(savedInstanceState)
        savedInstanceState.putString("code", (lv.adapter as CurrencyCodesAdapter).selCurCode)
    }

    fun setResult(code: String) {
        val data = Intent()
        data.putExtra("code", code)
        setResult(Activity.RESULT_OK, data)
    }

}
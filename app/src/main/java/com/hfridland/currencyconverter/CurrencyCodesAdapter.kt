package com.hfridland.currencyconverter

import android.content.Context
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import kotlinx.android.synthetic.main.currency_item.view.*
import android.content.Intent
import android.widget.TextView
import java.util.*


class CurrencyCodesAdapter(context: Context, val resource: Int, val currencyCodes: List<CurrencyCodes.Currency>, _selCurCode: String) :
        ArrayAdapter<CurrencyCodes.Currency>(context, resource, currencyCodes)  {

    private val lif: LayoutInflater = LayoutInflater.from(context)
    var selCurCode = _selCurCode

    fun search(substr: String): Int {
        if (substr.trim().isEmpty())
            return -1
        val selInd = currencyCodes.indexOfFirst { it.code == selCurCode }
        for(i in selInd+1..(currencyCodes.size-1)) {
            val cur = currencyCodes[i]
            if (cur.toString().toLowerCase().indexOf(substr.toLowerCase()) >= 0) {
                selCurCode = cur.code
                (context as SelCurrencyActivity).setResult(selCurCode)
                notifyDataSetInvalidated()
                return i
            }
        }
        for(i in 0..selInd-1) {
            val cur = currencyCodes[i]
            if (cur.toString().toLowerCase().indexOf(substr.toLowerCase()) >= 0) {
                selCurCode = cur.code
                (context as SelCurrencyActivity).setResult(selCurCode)
                notifyDataSetInvalidated()
                return i
            }
        }
        return -1
    }

    public override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var res: View =
            if (convertView == null)
                lif.inflate(this.resource, parent, false)
            else
                convertView

        val currency: CurrencyCodes.Currency = currencyCodes[position]
        res.tvCode.text = currency.code
        res.tvDescr.text = currency.descr
        res.root.setOnClickListener {
            selCurCode = currency.code
            notifyDataSetChanged()
            (context as SelCurrencyActivity).setResult(selCurCode)
        }



        if (currency.code == selCurCode) {
            res.tvCode.setTextColor(ContextCompat.getColor(context, R.color.colorSelText))
            res.tvDescr.setTextColor(ContextCompat.getColor(context, R.color.colorSelText))
            res.root.setBackgroundColor(ContextCompat.getColor(context, R.color.colorSelBg))
        } else {
            res.tvCode.setTextColor(ContextCompat.getColor(context, R.color.colorText))
            res.tvDescr.setTextColor(ContextCompat.getColor(context, R.color.colorText))
            res.root.setBackgroundColor(ContextCompat.getColor(context, R.color.colorBg))
        }

        return res;
    }

}
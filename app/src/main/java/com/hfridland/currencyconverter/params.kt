package com.hfridland.currencyconverter

import android.content.Context
import java.io.IOException
import java.io.ObjectInputStream
import java.io.ObjectOutputStream
import java.io.Serializable

object Params {
    data class Data(var curCodeFrom: String, var curCodeTo: String) : Serializable

    var data: Data = Data("USD", "CAD")

    fun load(context: Context) {
        try {
            context.openFileInput("parms.dat").use {
                ObjectInputStream(it).use {
                    data = it.readObject() as Data
                }
            }
        } catch (ex: IOException) { }
    }

    fun save(context: Context) {
        context.openFileOutput("parms.dat", Context.MODE_PRIVATE).use {
            ObjectOutputStream(it).use {
                it.writeObject(data)
            }
        }

    }
}
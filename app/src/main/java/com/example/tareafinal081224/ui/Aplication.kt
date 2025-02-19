package com.example.tareafinal081224.ui

import android.app.Application
import android.content.Context
import com.example.tareafinal081224.data.MyDatabase

class Aplication : Application() {
    companion object {
        const val VERSION = 6
        const val DB = "Reviews_DB"
        const val TABLA = "Reviews"
        lateinit var contexto: Context
        lateinit var llave: MyDatabase
    }

    override fun onCreate() {
        super.onCreate()
        contexto = applicationContext
        llave = MyDatabase()
    }
}
package com.example.tareafinal081224

import android.app.Application
import android.content.Context
import com.example.tareafinal081224.providers.MyDatabase

class Aplication : Application() {
    companion object {
        const val VERSION = 1
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
package com.example.tareafinal081224.providers

import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.tareafinal081224.Aplication

class MyDatabase() :
    SQLiteOpenHelper(Aplication.contexto, Aplication.DB, null, Aplication.VERSION) {
    private val q = "create table ${Aplication.TABLA}(" +
            "id integer primary key autoincrement, " +
            "seriePoster text, " +
            "serieTitle text, " +
            "comment text, " +
            "rating integer);"

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(q)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        if (oldVersion < newVersion) {
            db?.execSQL("drop table if exists ${Aplication.TABLA}")
            db?.execSQL(q)
        }
    }
}
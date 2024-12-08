package com.example.tareafinal081224.providers

import android.database.sqlite.SQLiteDatabase
import com.example.tareafinal081224.Aplication
import com.example.tareafinal081224.models.Review
import android.content.ContentValues

class CrudReviews {
    // CREATE --------------------------------------------------------------------------------------
    fun create(r: Review): Long {
        val con = Aplication.llave.writableDatabase
        return try {
            // Al insertar un registro que ya existe, se ignora el registro y no se inserta
            con.insertWithOnConflict(
                Aplication.TABLA,
                null,
                r.toContentValues(),
                SQLiteDatabase.CONFLICT_IGNORE
            )
        } catch (ex: Exception) {
            ex.printStackTrace()
            -1L
        } finally {
            con.close()
        }
    }

    // Para mapear el objeto Review a un ContentValues para poder insertarlo en la base de datos
    private fun Review.toContentValues(): ContentValues {
        return ContentValues().apply {
            put("seriePoster", seriePoster)
            put("serieTitle", serieTitle)
            put("rating", rating)
            put("comment", comment)
        }
    }

    // READ ----------------------------------------------------------------------------------------
    fun read(): MutableList<Review> {
        val lista = mutableListOf<Review>()
        val con = Aplication.llave.readableDatabase
        try {
            val cursor = con.query(
                Aplication.TABLA,
                arrayOf("id", "seriePoster", "serieTitle", "rating", "comment"),
                null,
                null,
                null,
                null,
                null
            )
            while (cursor.moveToNext()) {
                val review = Review(
                    cursor.getInt(0),
                    cursor.getString(1),
                    cursor.getString(2),
                    cursor.getInt(3),
                    cursor.getString(4)
                )
                lista.add(review)
            }
        } catch (ex: Exception) {
            ex.printStackTrace()
        } finally {
            con.close()
        }
        return lista
    }

    // DELETE --------------------------------------------------------------------------------------
    public fun delete(id: Int): Boolean {
        val con = Aplication.llave.writableDatabase
        val reviewDeleted = con.delete(Aplication.TABLA, "id=?", arrayOf(id.toString()))
        con.close()
        return reviewDeleted > 0
    }

    // UPDATE --------------------------------------------------------------------------------------
    public fun update(r: Review): Boolean {
        // Abrir la base de datos en modo escritura
        val con = Aplication.llave.writableDatabase
        val values = r.toContentValues()
        var filasAfectadas = 0

        filasAfectadas = con.update(
            Aplication.TABLA,
            values,
            "id=?",
            arrayOf(r.id.toString())
        )
        con.close()
        return filasAfectadas > 0
    }
}
package com.example.ggapplication

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class ConexionSql(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "usuarios.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "usuarios"
        private const val COLUMN_ID = "id"
        private const val COLUMN_NOMBRE = "nombre"
        private const val COLUMN_APELLIDO = "apellido"
        private const val COLUMN_SEXO = "sexo"
        private const val COLUMN_TRANSPORTE = "transporte"
        private const val COLUMN_RANGO_EDAD = "rango_edad"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTable = ("CREATE TABLE $TABLE_NAME ("
                + "$COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "$COLUMN_NOMBRE TEXT, "
                + "$COLUMN_APELLIDO TEXT, "
                + "$COLUMN_SEXO TEXT, "
                + "$COLUMN_TRANSPORTE TEXT, "
                + "$COLUMN_RANGO_EDAD TEXT)")
        db?.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    fun insertUsuario(nombre: String, apellido: String, sexo: String, transporte: String, rangoEdad: String) {
        val db = this.writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_NOMBRE, nombre)
            put(COLUMN_APELLIDO, apellido)
            put(COLUMN_SEXO, sexo)
            put(COLUMN_TRANSPORTE, transporte)
            put(COLUMN_RANGO_EDAD, rangoEdad)
        }
        db.insert(TABLE_NAME, null, contentValues)
        db.close()
    }

    fun getAllUsuarios(): List<String> {
        val usuarios = mutableListOf<String>()
        val db = this.readableDatabase
        val cursor = db.rawQuery("SELECT * FROM $TABLE_NAME", null)
        if (cursor.moveToFirst()) {
            do {
                val nombre = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_NOMBRE))
                val apellido = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_APELLIDO))
                val sexo = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_SEXO))
                val transporte = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TRANSPORTE))
                val rangoEdad = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_RANGO_EDAD))
                val usuario = "$nombre $apellido ($sexo, Transporte: $transporte, Edad: $rangoEdad)"
                usuarios.add(usuario)
            } while (cursor.moveToNext())
        }
        cursor.close()
        /*db.close()*/
        return usuarios
    }
}

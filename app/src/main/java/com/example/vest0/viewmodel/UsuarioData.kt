package com.example.vest0.viewmodel

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class UsuarioData(context: Context) : SQLiteOpenHelper(
    context,
    DATABASE_NAME,
    null,
    DATABASE_VERSION
) {
    override fun onCreate(db: SQLiteDatabase) {
        val createTable = """
            CREATE TABLE $TABLE_USUARIOS (
                $COLUMN_EMAIL TEXT PRIMARY KEY,
                $COLUMN_CONTRASENA TEXT,
                $COLUMN_NOMBRE TEXT,
                $COLUMN_APELLIDO TEXT,
                $COLUMN_PERFIL TEXT
            )
        """.trimIndent()
        db.execSQL(createTable)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS $TABLE_USUARIOS")
        onCreate(db)
    }

    companion object {
        const val DATABASE_NAME = "vesto.db"
        const val DATABASE_VERSION = 1

        const val TABLE_USUARIOS = "usuarios"
        const val COLUMN_EMAIL = "email"
        const val COLUMN_CONTRASENA = "contrasena"
        const val COLUMN_NOMBRE = "nombre"
        const val COLUMN_APELLIDO = "apellido"
        const val COLUMN_PERFIL = "perfilUser"
    }
}
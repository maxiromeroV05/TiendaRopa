package com.example.vest0.repository

import android.content.ContentValues
import android.content.Context
import com.example.vest0.viewmodel.UsuarioData
import com.example.vest0.model.Usuario

class UsuarioRepositorySQLite(context: Context) {
    private val dbHelper = UsuarioData(context)

    fun insertar(usuario: Usuario): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(UsuarioData.COLUMN_EMAIL, usuario.email)
            put(UsuarioData.COLUMN_CONTRASENA, usuario.contraseña)
            put(UsuarioData.COLUMN_NOMBRE, usuario.nombre)
            put(UsuarioData.COLUMN_APELLIDO, usuario.apellido)
            put(UsuarioData.COLUMN_PERFIL, usuario.perfilUser)
        }
        val result = db.insert(UsuarioData.TABLE_USUARIOS, null, values)
        db.close()
        return result != -1L
    }

    fun obtenerPorEmail(email: String): Usuario? {
        val db = dbHelper.readableDatabase
        val cursor = db.query(
            UsuarioData.TABLE_USUARIOS,
            null,
            "${UsuarioData.COLUMN_EMAIL} = ?",
            arrayOf(email),
            null,
            null,
            null
        )
        val usuario = if (cursor.moveToFirst()) {
            Usuario(
                email = cursor.getString(cursor.getColumnIndexOrThrow(UsuarioData.COLUMN_EMAIL)),
                contraseña = cursor.getString(cursor.getColumnIndexOrThrow(UsuarioData.COLUMN_CONTRASENA)),
                nombre = cursor.getString(cursor.getColumnIndexOrThrow(UsuarioData.COLUMN_NOMBRE)),
                apellido = cursor.getString(cursor.getColumnIndexOrThrow(UsuarioData.COLUMN_APELLIDO)),
                perfilUser = cursor.getString(cursor.getColumnIndexOrThrow(UsuarioData.COLUMN_PERFIL))
            )
        } else null
        cursor.close()
        db.close()
        return usuario
    }

    fun actualizar(usuario: Usuario): Boolean {
        val db = dbHelper.writableDatabase
        val values = ContentValues().apply {
            put(UsuarioData.COLUMN_CONTRASENA, usuario.contraseña)
            put(UsuarioData.COLUMN_NOMBRE, usuario.nombre)
            put(UsuarioData.COLUMN_APELLIDO, usuario.apellido)
            put(UsuarioData.COLUMN_PERFIL, usuario.perfilUser)
        }
        val result = db.update(
            UsuarioData.TABLE_USUARIOS,
            values,
            "${UsuarioData.COLUMN_EMAIL} = ?",
            arrayOf(usuario.email)
        )
        db.close()
        return result > 0
    }

    fun eliminar(email: String): Boolean {
        val db = dbHelper.writableDatabase
        val result = db.delete(
            UsuarioData.TABLE_USUARIOS,
            "${UsuarioData.COLUMN_EMAIL} = ?",
            arrayOf(email)
        )
        db.close()
        return result > 0
    }
}
package com.example.gl_app


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteConstraintException
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteException
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList

class UsersDBHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {
    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL(SQL_CREATE_ENTRIES)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // This database is only a cache for online data, so its upgrade policy is
        // to simply to discard the data and start over
        db.execSQL(SQL_DELETE_ENTRIES)
        onCreate(db)
    }

    override fun onDowngrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        onUpgrade(db, oldVersion, newVersion)
    }

    @Throws(SQLiteConstraintException::class)
    fun insertUser(user: UserModel): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase

        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        values.put(DBContract.UserEntry.COLUMN_NAME, user.name)
        values.put(DBContract.UserEntry.COLUMN_EMAIL, user.email)
        values.put(DBContract.UserEntry.COLUMN_PASSWORD, user.password)
        values.put(DBContract.UserEntry.COLUMN_NIVEAU, user.niveau)

        // Insert the new row, returning the primary key value of the new row
        val newRowId = db.insert(DBContract.UserEntry.TABLE_NAME, null, values)
        return true
    }

    @Throws(SQLiteConstraintException::class)
    fun deleteUser(name: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.UserEntry.COLUMN_NAME + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(name)
        // Issue SQL statement.
        db.delete(DBContract.UserEntry.TABLE_NAME, selection, selectionArgs)

        return true
    }
    @Throws(SQLiteConstraintException::class)
    fun updateUser(email: String): Boolean {
        // Gets the data repository in write mode
        val db = writableDatabase
        // Define 'where' part of query.
        val selection = DBContract.UserEntry.COLUMN_EMAIL + " LIKE ?"
        // Specify arguments in placeholder order.
        val selectionArgs = arrayOf(email)
        // Issue SQL statement.
        // Create a new map of values, where column names are the keys
        val values = ContentValues()
        val usr = readUser(email)

        values.put(DBContract.UserEntry.COLUMN_NAME, usr.elementAt(0).name)
        values.put(DBContract.UserEntry.COLUMN_EMAIL, usr.elementAt(0).email)
        values.put(DBContract.UserEntry.COLUMN_PASSWORD, usr.elementAt(0).password)
        values.put(DBContract.UserEntry.COLUMN_NIVEAU, usr.elementAt(0).niveau)
        values.put(DBContract.UserEntry.COLUMN_REG, usr.elementAt(0).reg)


        db.update(DBContract.UserEntry.TABLE_NAME,values, selection, selectionArgs)

        return true
    }

    fun readUser(userid: String): ArrayList<UserModel> {
        val users = ArrayList<UserModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME + " WHERE " + DBContract.UserEntry.COLUMN_EMAIL + "='" + userid + "'", null)
        } catch (e: SQLiteException) {
            // if table not yet present, create it
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var name: String
        var email: String
        var niveau : String
        var password : String
        var reg : String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                name = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NAME))
                email = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_EMAIL))
                niveau = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NIVEAU))
                password = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_PASSWORD))
                reg = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_REG))


                users.add(UserModel(name, email, password, niveau,reg))
                cursor.moveToNext()
            }
        }
        return users
    }

    fun readAllUsers(): ArrayList<UserModel> {
        val users = ArrayList<UserModel>()
        val db = writableDatabase
        var cursor: Cursor? = null
        try {
            cursor = db.rawQuery("select * from " + DBContract.UserEntry.TABLE_NAME, null)
        } catch (e: SQLiteException) {
            db.execSQL(SQL_CREATE_ENTRIES)
            return ArrayList()
        }

        var email: String
        var name: String
        var password: String
        var niveau : String
        var reg : String
        if (cursor!!.moveToFirst()) {
            while (cursor.isAfterLast == false) {
                email = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_EMAIL))
                name = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NAME))
                password = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_PASSWORD))
                niveau = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_NIVEAU))
                reg = cursor.getString(cursor.getColumnIndex(DBContract.UserEntry.COLUMN_REG))
                users.add(UserModel(name, email, password, niveau,reg))
                cursor.moveToNext()
            }
        }
        return users
    }

    companion object {
        // If you change the database schema, you must increment the database version.
        val DATABASE_VERSION = 1
        val DATABASE_NAME = "GL.db"

        private val SQL_CREATE_ENTRIES =
            "CREATE TABLE " + DBContract.UserEntry.TABLE_NAME + " (" +
                    DBContract.UserEntry.COLUMN_EMAIL + " TEXT PRIMARY KEY," +
                    DBContract.UserEntry.COLUMN_NAME + " TEXT," +
                    DBContract.UserEntry.COLUMN_PASSWORD + " TEXT," +
                    DBContract.UserEntry.COLUMN_NIVEAU + " TEXT," +
                    DBContract.UserEntry.COLUMN_REG + " TEXT)"


        private val SQL_DELETE_ENTRIES = "DROP TABLE IF EXISTS " + DBContract.UserEntry.TABLE_NAME
    }

}
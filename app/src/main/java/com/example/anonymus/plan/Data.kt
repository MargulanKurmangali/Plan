package com.example.anonymus.plan

/**
 * Created by Anonymus on 14.02.2018.
 */
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

import java.util.ArrayList

/**
 * Created by reale on 06/10/2016.
 */

class Data(context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VER) {

    val taskList: ArrayList<String>
        get() {
            val taskList = ArrayList<String>()
            val db = this.readableDatabase
            val cursor = db.query(DB_TABLE, arrayOf(DB_COLUMN), null, null, null, null, null)
            while (cursor.moveToNext()) {
                val index = cursor.getColumnIndex(DB_COLUMN)
                taskList.add(cursor.getString(index))
            }
            cursor.close()
            db.close()
            return taskList
        }

    override fun onCreate(db: SQLiteDatabase) {
        val query = String.format("CREATE TABLE %s (ID INTEGER PRIMARY KEY AUTOINCREMENT,%s TEXT NOT NULL);", DB_TABLE, DB_COLUMN)
        db.execSQL(query)

    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        val query = String.format("DELETE TABLE IF EXISTS %s", DB_TABLE)
        db.execSQL(query)
        onCreate(db)

    }

    fun insertNewTask(task: String) {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(DB_COLUMN, task)
        db.insertWithOnConflict(DB_TABLE, null, values, SQLiteDatabase.CONFLICT_REPLACE)
        db.close()
    }

    fun deleteTask(task: String) {
        val db = this.writableDatabase
        db.delete(DB_TABLE, DB_COLUMN + " = ?", arrayOf(task))
        db.close()
    }

    fun getTaskLists(): ArrayList<String> {
        val taskList = ArrayList<String>()
        val db = this.readableDatabase
        val cursor = db.query(DB_TABLE, arrayOf(DB_COLUMN), null, null, null, null, null)
        while (cursor.moveToNext()) {
            val index = cursor.getColumnIndex(DB_COLUMN)
            taskList.add(cursor.getString(index))
        }
        cursor.close()
        db.close()
        return taskList
    }
    companion object {

        private val DB_NAME = "EDMTDev"
        private val DB_VER = 1
        val DB_TABLE = "Task"
        val DB_COLUMN = "TaskName"
    }
}
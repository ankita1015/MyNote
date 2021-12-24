package com.example.mynotes.DBHandler

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.example.mynotes.Model.Notes

class DatabaseHandler(context:Context):SQLiteOpenHelper(context, DATABASE_NAME,null, DATABASE_VERSION) {

        companion object{
            private const val DATABASE_NAME="note_db"
            private const val DATABASE_VERSION=1
            private const val TABLE_NOTE="note_tbl"

            //column for notetbl

            private const val NOTE_ID="_id"
            private const val NOTE_TITLE="note_title"
            private const val NOTE_TEXT="note_text"
        }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE_NOTE="CREATE TABLE $TABLE_NOTE" +
                "($NOTE_ID INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL," +
                "$NOTE_TITLE TEXT," +
                "$NOTE_TEXT TEXT)"
        db!!.execSQL(CREATE_TABLE_NOTE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NOTE")
        onCreate(db)
    }

    fun addNotes(note: Notes):Long{
        val db = this.writableDatabase

        val contentValues = ContentValues()
        contentValues.put(NOTE_TITLE,note.title)
        contentValues.put(NOTE_TEXT,note.note)

        val response = db.insert(TABLE_NOTE,null,contentValues)

        db.close()
        return response
    }

    fun readnotes():ArrayList<Notes>{
        val db = this.readableDatabase

        val notesCursor = db.rawQuery("SELECT * FROM $TABLE_NOTE",null)

        val notesList:ArrayList<Notes> = ArrayList()

        if(notesCursor.moveToFirst()){
            do {
                notesList.add(Notes(notesCursor.getInt(0),
                                    notesCursor.getString(1),
                                    notesCursor.getString(2)))
            }while (notesCursor.moveToNext())
        }
        return notesList
    }
    fun readspecificNotes(id:Int):Notes{
       val  db = this.readableDatabase

        val notesCursor = db.rawQuery("SELECT * FROM $TABLE_NOTE WHERE $NOTE_ID=$id",null)

        notesCursor.moveToFirst()

        val noteList : Notes = Notes(
            notesCursor.getInt(0),
            notesCursor.getString(1),
            notesCursor.getString(2)
        )
        db.close()
        return noteList
    }

    fun deleteNotes(_id: Int): Int {
       var db = this.writableDatabase

        var rows = db.delete(TABLE_NOTE,"$NOTE_ID=?", arrayOf<String>(_id.toString()))
        return rows
    }
    fun updateNotes(note: Notes):Int{
        var db = this.writableDatabase

        var contentValues = ContentValues()
        contentValues.put(NOTE_TITLE,note.title)
        contentValues.put(NOTE_TEXT,note.note)

        var rows = db.update(TABLE_NOTE,contentValues,"$NOTE_ID=?", arrayOf<String>(note._id.toString()))
        return rows
    }
}
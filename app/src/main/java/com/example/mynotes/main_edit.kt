package com.example.mynotes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.example.mynotes.DBHandler.DatabaseHandler
import com.example.mynotes.Model.Notes

class main_edit : AppCompatActivity() {
    lateinit var toolbar:androidx.appcompat.widget.Toolbar
    var _id:Int =0
    lateinit var edtupdateTitle: EditText
    lateinit var edtupdateNotes:EditText
    lateinit var btnupdate: Button
    lateinit var notes: Notes

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_edit)

        toolbar =findViewById(R.id.toolbar)
        edtupdateTitle =findViewById(R.id.edt_title)
        edtupdateNotes =findViewById(R.id.edt_note)
        btnupdate =findViewById(R.id.btnAdd)



        setSupportActionBar(toolbar)

        _id = intent.getIntExtra("_id",0)
    }

    override fun onStart() {
        super.onStart()
        var dbHandler = DatabaseHandler(this)
        notes = dbHandler.readspecificNotes(_id)
        edtupdateTitle.setText(notes.title)
        edtupdateNotes.setText(notes.note)

        btnupdate.setOnClickListener {
            updateNotes()
        }


    }

    fun updateNotes() {
        if(edtupdateTitle.text.toString() == notes.title &&
            edtupdateNotes.text.toString() == notes.note){
            finish()
        }else{
            var dbHandler = DatabaseHandler(this)

            var rows= dbHandler.updateNotes(
                Notes(_id,
                    edtupdateTitle.text.toString(),
                    edtupdateNotes.text.toString()))

            if(rows > 0){

                Toast.makeText(this, "Update succesfull", Toast.LENGTH_SHORT).show()
                finish()
            }
        }

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            android.R.id.home->{
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}

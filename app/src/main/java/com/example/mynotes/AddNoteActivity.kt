package com.example.mynotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.widget.Toolbar
import com.example.mynotes.DBHandler.DatabaseHandler
import com.example.mynotes.Model.Notes

class AddNoteActivity : AppCompatActivity() {
    lateinit var toolbar:Toolbar
    lateinit var edtTitle:EditText
    lateinit var edtText:EditText
    lateinit var btnAddNotes:Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)
        toolbar=findViewById(R.id.toolbar)
        edtTitle=findViewById(R.id.edtTitle)
        edtText=findViewById(R.id.edtNote)
        btnAddNotes=findViewById(R.id.btnAddNote)

        setSupportActionBar(toolbar)
    }

    override fun onStart() {
        super.onStart()
        btnAddNotes.setOnClickListener {
            addNote()
        }
    }
    private fun addNote() {
        var title:String = edtTitle.text.toString()
        var text:String = edtText.text.toString()

           if(title.isEmpty() && text.isEmpty()){
               edtTitle.error = "Title is Required"
               edtText.error = "Notes is Required"
           }else {
               val dbHandler = DatabaseHandler(this)
               val rows = dbHandler.addNotes(Notes(null, title, text))

               if (rows > -1) {
                   Toast.makeText(this, "Notes Added Successfully.", Toast.LENGTH_LONG).show()
                   Log.d("AddNoteActivity", "data inserted with rows: $rows")
                   finish()
               } else {
                   Toast.makeText(this, "Notes Failed!!.", Toast.LENGTH_LONG).show()
               }
           }
        }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            android.R.id.home -> {
               // Log.d("AddNoteActivity","In Home Icon")
//              var intent: Intent = Intent(this,MainActivity::class.java)
//               startActivity(intent)
                finish()
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
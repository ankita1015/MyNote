package com.example.mynotes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.DBHandler.DatabaseHandler
import com.example.mynotes.Model.Notes
import com.example.mynotes.RecyclerAdapter.NotesAdapter
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    lateinit var toolbar:androidx.appcompat.widget.Toolbar
    lateinit var fab:FloatingActionButton
    lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        toolbar=findViewById(R.id.toolbar)
        recyclerView=findViewById(R.id.recycleView)
        fab=findViewById(R.id.fab)

        setSupportActionBar(toolbar)

        viewnotes()
    }

    private fun viewnotes() {
        val dbHandler = DatabaseHandler(this)
        val noteList:ArrayList<Notes> = dbHandler.readnotes()

        recyclerView.adapter = NotesAdapter(this,noteList)

    }

    override fun onResume() {
        super.onResume()
        viewnotes()
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }
    override fun onStart() {
        super.onStart()

        fab.setOnClickListener {
            var intent:Intent = Intent(this,AddNoteActivity::class.java)
            // intent.flags=Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }
         viewnotes()
    }
}
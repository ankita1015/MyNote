package com.example.mynotes.RecyclerAdapter

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.view.menu.MenuView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.DBHandler.DatabaseHandler
import com.example.mynotes.Model.Notes
import com.example.mynotes.R
import com.example.mynotes.main_edit

class NotesAdapter (val context: Context,val notesList: ArrayList<Notes>):RecyclerView.Adapter<NotesAdapter.ViewHolder>(){

    class ViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        var txtTitle:TextView = itemView.findViewById(R.id.txtNoteTitle)
        var txtNotes:TextView = itemView.findViewById(R.id.txtNoteText)
        var parentlayout:LinearLayout=itemView.findViewById(R.id.parentlayout)
        var imgdelete:ImageView=itemView.findViewById(R.id.imgdelete)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflate = LayoutInflater.from(context).inflate(R.layout.note_item,parent,false)
        return ViewHolder(inflate)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.txtTitle.text = notesList[position].title
        holder.txtNotes.text=notesList[position].note
         holder.imgdelete.setOnClickListener {
             var dbHandler = DatabaseHandler(context)
             var rows = dbHandler.deleteNotes(notesList[position]._id !!)

             if (rows > 0){
                 Toast.makeText(context,"Note Deleted..!" ,Toast.LENGTH_SHORT).show()
                 notesList.removeAt(position)
                 notifyItemRemoved(position)
                 notifyItemRangeChanged(position,notesList.size)
             }
         }
        holder.parentlayout.setOnClickListener {
            var intent:Intent = Intent(context,main_edit::class.java)
            intent.putExtra("_id",notesList[position]._id)
            context.startActivity(intent)
        }
    }

    override fun getItemCount(): Int {
        return notesList.size
    }
}

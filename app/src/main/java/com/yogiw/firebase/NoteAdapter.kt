package com.yogiw.firebase

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast

class NoteAdapter(val context: Context, val listTodo:List<NoteClass>): RecyclerView.Adapter<NoteAdapter.ViewHolder>(){


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder{
        val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.item_note, parent, false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listTodo.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = listTodo[position]

        holder.tvTitle.text = item.title
        holder.tvDesc.text = item.description
    }

    class ViewHolder (item: View): RecyclerView.ViewHolder(item){
        val tvTitle = item.findViewById<View>(R.id.tvTitle) as TextView
        val tvDesc = item.findViewById<View>(R.id.tvDesc) as TextView
    }

}
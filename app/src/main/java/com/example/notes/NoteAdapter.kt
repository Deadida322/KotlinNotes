package com.example.notes

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import java.util.*
/** Класс адаптера для RecyclerView */
class NoteAdapter(private val onItemClick: ((Note) -> Unit)?): RecyclerView.Adapter<NoteAdapter.ViewHolder>() {

    private val values = mutableListOf<Note>()//инициализация списка заметок для RecyclerView

    override fun getItemCount() = values.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteAdapter.ViewHolder {
        val itemView = LayoutInflater.from(parent?.context).inflate(R.layout.notes_item, parent, false) //Связывание notes_item layout с кодом
        return ViewHolder(itemView)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
//        var timeStart = value
        var dateStart = values[position].date_start
        var dateFinish = values[position].date_finish
        holder?.noteTime?.text = "${Formater(dateStart).getTimeFromString()} - ${Formater(dateFinish).getTimeFromString()}"
        holder?.noteTitle?.text = values[position].name
        holder?.noteDate?.text = Formater(dateStart).getDateFromString()
    }
    inner class ViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        //Инициализация необходимых переменных
        var noteTime: TextView? = null
        var noteTitle: TextView? = null
        var noteDate: TextView? = null
        init {
            //Отображение содержимого заметок при инициализации
            noteTime = itemView?.findViewById(R.id.noteTime)
            noteTitle = itemView?.findViewById(R.id.noteTitle)
            noteDate = itemView?.findViewById(R.id.noteDate)
            //байндинг функции при клике (переход на другое активити)
            itemView.setOnClickListener{
                onItemClick?.invoke(values[adapterPosition])
            }
        }
    }
    //Метод, выполняющий обновление списка заметок в представлении
    @SuppressLint("NotifyDataSetChanged")
    fun setData(copy: MutableList<Note>){
        values.clear()
        values.addAll(copy)
        notifyDataSetChanged()
    }
}
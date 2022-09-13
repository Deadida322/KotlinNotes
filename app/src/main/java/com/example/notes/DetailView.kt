package com.example.notes

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.notes.databinding.ActivityDetailViewBinding
import java.util.*
/*
* Класс отображения детальной информации по заметке
* */
class DetailView : AppCompatActivity() {
    private lateinit var detailViewBinding: ActivityDetailViewBinding
    private lateinit var id: String
    private lateinit var name: String
    private lateinit var description: String
    private var from: Long = 0
    private var to: Long = 0

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        //Получение значений, переданных в интент по клику
        id = intent.extras?.get("id").toString()
        name = intent.extras?.get("name").toString()
        description = intent.extras?.get("description").toString()
        from = intent.extras?.get("from").toString().toLong()
        to = intent.extras?.get("to").toString().toLong()
        detailViewBinding = ActivityDetailViewBinding.inflate(layoutInflater)
        supportActionBar?.setDisplayHomeAsUpEnabled(true) //Отображение кнопки назад

        super.onCreate(savedInstanceState)
        setContentView(detailViewBinding.root)
        detailViewBinding.noteId.text = id
        detailViewBinding.noteTtl.text = name
        detailViewBinding.noteDesc.text = description
        detailViewBinding.noteFromTo.text = "${Formater(from).convertLongToTime()} - ${Formater(to).convertLongToTime()}"

    }
    /*
    * Функция вызывающаяся при нажатии на кноку назад и закрыващая текущее активити
    * */
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
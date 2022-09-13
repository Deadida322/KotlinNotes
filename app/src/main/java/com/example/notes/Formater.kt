package com.example.notes

import android.icu.text.SimpleDateFormat
import java.util.*

/**
 * Класс для форматирования дат из Long в другие представления
 * */
class Formater(date: Long = 0) {
    var date = date

    init {
        if (date == 0.toLong()){
            this.date = getCurrentDateLong()
        }
    }
    /*
    * Функция, прервщающая Long в Дату в String-формате
    * */
    fun convertLongToTime(): String {
        var newdate = Date(date.toLong())
        val format = SimpleDateFormat("yyyy.MM.dd HH:mm")
        return format.format(newdate)
    }
    /*
    * Функция, извлекающая дату из строки
    * */
    fun getDateFromString(): String = convertLongToTime().split(" ").toTypedArray()[0].toString()
    /*
    * Функция, извлекающая время из строки
    * */
    fun getTimeFromString(): String = convertLongToTime().split(" ").toTypedArray()[1].toString()
    /*
    * Получение даты Long формате
    * */
    fun getCurrentDateLong(): Long {
        var format = java.text.SimpleDateFormat("dd-MM-yyyy")
        return format.parse(format.format(Date())).time.toLong()
    }
    fun getCurrentDateTimeLong(): Long {
        var format = java.text.SimpleDateFormat("dd-MM-yyyy hh:mm")
        return format.parse(format.format(Date())).time.toLong()
    }

    fun getCurrentTimeLong(): Long {
        var format = java.text.SimpleDateFormat("hh:mm")
        return format.parse(format.format(Date())).time.toLong()+4*3600000
    }



}
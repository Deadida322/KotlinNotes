package com.example.notes

import android.icu.text.SimpleDateFormat
import java.util.*

/**
 * Класс для форматирования дат из Long в другие представления
 * */
class Formater(date: Long) {

    var date = date
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

}
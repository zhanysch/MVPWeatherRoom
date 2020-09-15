package com.example.asynctascretrofit.utilites


import java.text.SimpleDateFormat
import java.util.*

object DateTimeUtils {

    private const val DATE = "d"
    private const val MOHNTS = "MMMM\nyyyy"
    private const val H_MM = "H:mm"


    fun getDay(): String {
        val sfDay = SimpleDateFormat(DATE , Locale.getDefault())
        val date = Date()
        return sfDay.format(date)
    }

    fun getMonth(): String {
        val date = Date()
        val sfMonth = SimpleDateFormat(MOHNTS, Locale.getDefault())
        return sfMonth.format(date)

    }

    fun formatDate(date: Int?): String {
        val newdata = date?.toLong()?:0 // ?:0 <- оператор элвиса ,  длтгчтб (date: Int?) date : int? может быть null , a строчка
        // 113 туда для умножения нужен обьязательно не null и выходила ошибка поэтому , даем оператор элвиса
        return SimpleDateFormat(H_MM, Locale.getDefault()).format(Date(newdata * 1000)) //  Locale.getDefault()) это часовой пояс котор установлен на телефоне
        // он будет давать данные исходя из часового пояся
    }

}
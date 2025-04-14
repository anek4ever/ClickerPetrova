package com.example.clickerapp.storage

import android.content.Context
import java.text.SimpleDateFormat
import java.util.*

data class Stats(val today: Int, val week: Int, val month: Int, val year: Int)

class ClickStorage(context: Context) {
    private val prefs = context.getSharedPreferences("clicks", Context.MODE_PRIVATE)

    fun saveClick() {
        val now = Calendar.getInstance()
        val key = getDateKey(now.time)
        val current = prefs.getInt(key, 0)
        prefs.edit().putInt(key, current + 1).apply()
        prefs.edit().putInt("total", getTotalClicks() + 1).apply()
    }

    fun getTotalClicks(): Int = prefs.getInt("total", 0)

    fun getStats(): Stats {
        val now = Calendar.getInstance()
        var today = 0
        var week = 0
        var month = 0
        var year = 0

        for (i in 0..365) {
            val date = Calendar.getInstance().apply { add(Calendar.DATE, -i) }
            val key = getDateKey(date.time)
            val count = prefs.getInt(key, 0)

            if (i == 0) today += count
            if (i < 7) week += count
            if (date.get(Calendar.MONTH) == now.get(Calendar.MONTH) &&
                date.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
                month += count
            }
            if (date.get(Calendar.YEAR) == now.get(Calendar.YEAR)) {
                year += count
            }
        }

        return Stats(today, week, month, year)
    }

    private fun getDateKey(date: Date): String {
        val sdf = SimpleDateFormat("yyyyMMdd", Locale.US)
        return sdf.format(date)
    }
}

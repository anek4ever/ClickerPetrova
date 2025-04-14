package com.example.clickerapp

import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.clickerapp.storage.ClickStorage
import java.text.SimpleDateFormat
import java.util.*

class StatsActivity : AppCompatActivity() {
    private lateinit var todayStats: TextView
    private lateinit var weekStats: TextView
    private lateinit var monthStats: TextView
    private lateinit var yearStats: TextView
    private lateinit var backButton: Button
    private lateinit var title: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_stats)

        val storage = ClickStorage(this)

        todayStats = findViewById(R.id.todayStats)
        weekStats = findViewById(R.id.weekStats)
        monthStats = findViewById(R.id.monthStats)
        yearStats = findViewById(R.id.yearStats)
        backButton = findViewById(R.id.backButton)
        title = findViewById(R.id.appTitle)

        val stats = storage.getStats()
        val monthName = SimpleDateFormat("MMMM", Locale.getDefault()).format(Date())

        todayStats.text = getString(R.string.today_clicks, stats.today)
        weekStats.text = getString(R.string.weekly_clicks, stats.week)
        monthStats.text = getString(R.string.monthly_clicks, monthName, stats.month)
        yearStats.text = getString(R.string.yearly_clicks, stats.year)

        backButton.setOnClickListener {
            finish()
        }
    }
}

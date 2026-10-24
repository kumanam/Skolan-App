package com.example.skolanapp.activities

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.skolanapp.R
import com.example.skolanapp.api.RetrofitClient
import com.example.skolanapp.databinding.ActivityMonthBinding
import com.example.skolanapp.models.CalendarEvent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar
import java.util.Locale

class MonthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMonthBinding

    private var calendarEvents: List<CalendarEvent> = emptyList()
    private val displayedMonth = Calendar.getInstance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMonthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadCalendarEvents()
    }

    private fun loadCalendarEvents() {
        lifecycleScope.launch {
            try {
                calendarEvents = withContext(Dispatchers.IO) {
                    RetrofitClient.schoolDataApi.getSchoolData().calendar
                }

                renderMonth()

            } catch (e: Exception) {
                Log.e("MonthActivity", "Error loading calendar", e)

                renderMonth()
            }
        }
    }

    private fun renderMonth() {
        val year = displayedMonth.get(Calendar.YEAR)
        val month = displayedMonth.get(Calendar.MONTH)

        val monthName = displayedMonth.getDisplayName(
            Calendar.MONTH,
            Calendar.LONG,
            Locale("sv", "SE")
        )

        binding.textMonthTitle.text = getString(
            R.string.month_title,
            monthName ?: "",
            year
        )

        createMonthCalendar(year, month)
    }

    private fun createMonthCalendar(year: Int, month: Int) {
        binding.monthGrid.removeAllViews()

        val today = Calendar.getInstance()
        val todayYear = today.get(Calendar.YEAR)
        val todayMonth = today.get(Calendar.MONTH)
        val todayDay = today.get(Calendar.DAY_OF_MONTH)

        val eventsByDate = calendarEvents.groupBy { it.date }

        val header = LinearLayout(this).apply {
            orientation = LinearLayout.HORIZONTAL
            layoutParams = LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dpToPx(36)
            )
        }

        addHeaderCell(header, getString(R.string.vecka), isWeekNumber = true)

        val days = listOf(
            getString(R.string.man),
            getString(R.string.tis),
            getString(R.string.ons),
            getString(R.string.tor),
            getString(R.string.fre),
            getString(R.string.lor),
            getString(R.string.son)
        )

        days.forEach { day ->
            addHeaderCell(header, day)
        }

        binding.monthGrid.addView(header)

        val calendar = Calendar.getInstance().apply {
            set(Calendar.YEAR, year)
            set(Calendar.MONTH, month)
            set(Calendar.DAY_OF_MONTH, 1)
            set(Calendar.HOUR_OF_DAY, 12)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val firstDay = calendar.get(Calendar.DAY_OF_WEEK)
        val mondayBasedDay = (firstDay + 5) % 7

        calendar.add(Calendar.DAY_OF_MONTH, -mondayBasedDay)

        repeat(6) {
            val weekRow = LinearLayout(this).apply {
                orientation = LinearLayout.HORIZONTAL
                layoutParams = LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    dpToPx(82)
                )
            }

            addWeekNumber(
                weekRow,
                calendar.get(Calendar.WEEK_OF_YEAR).toString()
            )

            repeat(7) {
                val cellYear = calendar.get(Calendar.YEAR)
                val cellMonth = calendar.get(Calendar.MONTH)
                val dayNumber = calendar.get(Calendar.DAY_OF_MONTH)

                val isToday =
                    cellYear == todayYear &&
                            cellMonth == todayMonth &&
                            dayNumber == todayDay

                val dateKey = String.format(
                    Locale.ROOT,
                    "%04d-%02d-%02d",
                    cellYear,
                    cellMonth + 1,
                    dayNumber
                )

                val dayEvents = eventsByDate[dateKey].orEmpty()

                val cell = createDayCell(
                    dayNumber = dayNumber,
                    isCurrentMonth = cellMonth == month,
                    isToday = isToday,
                    events = dayEvents
                )

                weekRow.addView(cell)
                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }

            binding.monthGrid.addView(weekRow)
        }
    }

    private fun addHeaderCell(
        row: LinearLayout,
        text: String,
        isWeekNumber: Boolean = false
    ) {
        val cell = TextView(this).apply {
            layoutParams = if (isWeekNumber) {
                LinearLayout.LayoutParams(
                    dpToPx(34),
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            } else {
                LinearLayout.LayoutParams(
                    0,
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    1f
                )
            }

            gravity = Gravity.CENTER
            this.text = text
            textSize = 12f
            setTypeface(null, Typeface.BOLD)
        }

        row.addView(cell)
    }

    private fun addWeekNumber(row: LinearLayout, week: String) {
        val cell = TextView(this).apply {
            layoutParams = LinearLayout.LayoutParams(
                dpToPx(34),
                ViewGroup.LayoutParams.MATCH_PARENT
            )
            gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL
            setPadding(0, dpToPx(6), 0, 0)
            text = week
            textSize = 10f
        }

        row.addView(cell)
    }

    private fun createDayCell(
        dayNumber: Int,
        isCurrentMonth: Boolean,
        isToday: Boolean,
        events: List<CalendarEvent>
    ): LinearLayout {

        val cell = LinearLayout(this).apply {
            orientation = LinearLayout.VERTICAL
            gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL

            layoutParams = LinearLayout.LayoutParams(
                0,
                ViewGroup.LayoutParams.MATCH_PARENT,
                1f
            )

            setPadding(
                dpToPx(2),
                dpToPx(4),
                dpToPx(2),
                dpToPx(2)
            )

            if (isToday) {
                setBackgroundColor(
                    ContextCompat.getColor(
                        this@MonthActivity,
                        R.color.calendar_today_background
                    )
                )
            }
        }

        val dayText = TextView(this).apply {
            text = dayNumber.toString()
            textSize = 12f
            gravity = Gravity.CENTER

            if (isToday) {
                setTypeface(null, Typeface.BOLD)
            }

            if (!isCurrentMonth) {
                setTextColor(Color.LTGRAY)
            }
        }

        cell.addView(
            dayText,
            LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                dpToPx(20)
            )
        )

        events.take(2).forEach { event ->
            val eventText = TextView(this).apply {
                text = event.title
                textSize = 8f
                maxLines = 2
                gravity = Gravity.CENTER
                setTypeface(null, Typeface.BOLD)
            }

            cell.addView(
                eventText,
                LinearLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT
                )
            )
        }

        if (events.size > 2) {
            val moreText = TextView(this).apply {
                text = "+${events.size - 2}"
                textSize = 8f
                gravity = Gravity.CENTER
            }

            cell.addView(moreText)
        }

        return cell
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}

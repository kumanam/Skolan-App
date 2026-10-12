
package com.example.skolanapp.activities

import android.os.Bundle
import android.view.Gravity
import android.widget.LinearLayout
import android.widget.TextView
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.skolanapp.R
import com.example.skolanapp.databinding.ActivityMonthBinding
import java.util.Calendar
import java.util.Locale

class MonthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMonthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMonthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val today = Calendar.getInstance()

        val month = today.get(Calendar.MONTH)
        val year = today.get(Calendar.YEAR)

        val monthName = today.getDisplayName(
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

        // Encabezado
        val header = LinearLayout(this)
        header.orientation = LinearLayout.HORIZONTAL

        addCell(header, getString(R.string.vecka), 40, true)

        val days = listOf(
            getString(R.string.man),
            getString(R.string.tis),
            getString(R.string.ons),
            getString(R.string.tor),
            getString(R.string.fre),
            getString(R.string.lor),
            getString(R.string.son)
        )

        for (day in days) {
            addCell(header, day, 75, true)
        }

        binding.monthGrid.addView(header)

        val calendar = Calendar.getInstance()
        calendar.set(year, month, 1)
        calendar.set(Calendar.HOUR_OF_DAY, 12)
        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)
        calendar.set(Calendar.MILLISECOND, 0)

        val firstDay = calendar.get(Calendar.DAY_OF_WEEK)

        val mondayBasedDay = when (firstDay) {
            Calendar.MONDAY -> 0
            Calendar.TUESDAY -> 1
            Calendar.WEDNESDAY -> 2
            Calendar.THURSDAY -> 3
            Calendar.FRIDAY -> 4
            Calendar.SATURDAY -> 5
            Calendar.SUNDAY -> 6
            else -> 0
        }


        calendar.add(Calendar.DAY_OF_MONTH, -mondayBasedDay)

        repeat(6) {

            val weekRow = LinearLayout(this)
            weekRow.orientation = LinearLayout.HORIZONTAL

            val weekNumber = calendar.get(Calendar.WEEK_OF_YEAR)

            addCell(
                weekRow,
                weekNumber.toString(),
                40,
                true
            )

            repeat(7) {

                val dayNumber = calendar.get(Calendar.DAY_OF_MONTH)

                val isToday =
                    calendar.get(Calendar.YEAR) == todayYear &&
                            calendar.get(Calendar.MONTH) == todayMonth &&
                            dayNumber == todayDay

                val cell = createDayCell(dayNumber, isToday)

                weekRow.addView(cell)

                calendar.add(Calendar.DAY_OF_MONTH, 1)
            }

            binding.monthGrid.addView(weekRow)
        }
    }

    private fun addCell(
        row: LinearLayout,
        text: String,
        width: Int,
        bold: Boolean
    ) {

        val cell = TextView(this)

        cell.layoutParams = LinearLayout.LayoutParams(
            dpToPx(width),
            dpToPx(80)
        )

        cell.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL

        cell.setPadding(
            dpToPx(6),
            dpToPx(6),
            dpToPx(6),
            dpToPx(6)
        )

        cell.text = text
        cell.textSize = 14f

        if (bold) {
            cell.setTypeface(null, Typeface.BOLD)
        }

        row.addView(cell)
    }

    private fun createDayCell(
        dayNumber: Int,
        isToday: Boolean
    ): TextView {

        val cell = TextView(this)

        cell.layoutParams = LinearLayout.LayoutParams(
            dpToPx(75),
            dpToPx(80)
        )

        cell.gravity = Gravity.TOP or Gravity.CENTER_HORIZONTAL

        cell.setPadding(
            dpToPx(6),
            dpToPx(6),
            dpToPx(6),
            dpToPx(6)
        )

        cell.text = dayNumber.toString()
        cell.textSize = 14f

        if (isToday) {
            cell.setTypeface(null, Typeface.BOLD)
            cell.setBackgroundColor(
                ContextCompat.getColor(this, R.color.calendar_today_background)
            )
        }

        return cell
    }

    private fun dpToPx(dp: Int): Int {
        return (dp * resources.displayMetrics.density).toInt()
    }
}
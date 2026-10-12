package com.example.skolanapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skolanapp.adapters.CalendarAdapter
import com.example.skolanapp.databinding.ActivityCalenderBinding
import android.widget.Toast
import com.example.skolanapp.R
import com.example.skolanapp.api.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalenderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCalenderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerMonday.layoutManager = LinearLayoutManager(this)
        binding.recyclerTuesday.layoutManager = LinearLayoutManager(this)
        binding.recyclerWednesday.layoutManager = LinearLayoutManager(this)
        binding.recyclerThursday.layoutManager = LinearLayoutManager(this)
        binding.recyclerFriday.layoutManager = LinearLayoutManager(this)

        binding.buttonMonth.setOnClickListener {
            val intent = Intent(this, MonthActivity::class.java)
            startActivity(intent)
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val schoolData = withContext(Dispatchers.IO) {
                    RetrofitClient.schoolDataApi.getSchoolData()
                }

                val calendarEvents = schoolData.calendar

                binding.recyclerMonday.adapter =
                    CalendarAdapter(calendarEvents.filter { it.day == getString(R.string.mandag) })

                binding.recyclerTuesday.adapter =
                    CalendarAdapter(calendarEvents.filter { it.day == getString(R.string.tisdag) })

                binding.recyclerWednesday.adapter =
                    CalendarAdapter(calendarEvents.filter { it.day == getString(R.string.onsdag) })

                binding.recyclerThursday.adapter =
                    CalendarAdapter(calendarEvents.filter { it.day == getString(R.string.torsdag) })

                binding.recyclerFriday.adapter =
                    CalendarAdapter(calendarEvents.filter { it.day == getString(R.string.fredag) })

            } catch (e: Exception) {
                e.printStackTrace()

                Toast.makeText(
                    this@CalendarActivity,
                    "Error: ${e.javaClass.simpleName}: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}
package com.example.skolanapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skolanapp.adapters.CalendarAdapter
import com.example.skolanapp.databinding.ActivityCalenderBinding
import com.example.skolanapp.models.CalendarEvent

class CalendarActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCalenderBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCalenderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val calendarEvents = listOf(
            CalendarEvent("Måndag", "08:00", "Svenska"),
            CalendarEvent("Måndag", "10:00", "Matematik"),

            CalendarEvent("Tisdag", "09:00", "Matematik"),
            CalendarEvent("Tisdag", "11:00", "Svenska"),

            CalendarEvent("Onsdag", "08:00", "Svenska"),
            CalendarEvent("Onsdag", "15:30", "Möte med läraren"),

            CalendarEvent("Torsdag", "09:00", "Engelska"),
            CalendarEvent("Torsdag", "13:00", "Matematik"),

            CalendarEvent("Fredag", "10:00", "Idrott"),
            CalendarEvent("Fredag", "12:00", "Musik")
        )

        binding.recyclerMonday.layoutManager =
            LinearLayoutManager(this)

        binding.recyclerMonday.adapter =
            CalendarAdapter(
                calendarEvents.filter { it.day == "Måndag" }
            )
        binding.recyclerTuesday.layoutManager =
            LinearLayoutManager(this)

        binding.recyclerTuesday.adapter =
            CalendarAdapter(
                calendarEvents.filter { it.day == "Tisdag" }
            )


        binding.recyclerWednesday.layoutManager =
            LinearLayoutManager(this)

        binding.recyclerWednesday.adapter =
            CalendarAdapter(
                calendarEvents.filter { it.day == "Onsdag" }
            )


        binding.recyclerThursday.layoutManager =
            LinearLayoutManager(this)

        binding.recyclerThursday.adapter =
            CalendarAdapter(
                calendarEvents.filter { it.day == "Torsdag" }
            )


        binding.recyclerFriday.layoutManager =
            LinearLayoutManager(this)

        binding.recyclerFriday.adapter =
            CalendarAdapter(
                calendarEvents.filter { it.day == "Fredag" }
            )
    }

}
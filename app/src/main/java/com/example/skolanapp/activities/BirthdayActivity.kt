package com.example.skolanapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.skolanapp.databinding.ActivityBirthdayBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skolanapp.models.Birthday
import com.example.skolanapp.adapters.BirthdayAdapter

class BirthdayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBirthdayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBirthdayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val birthdays = listOf(
            Birthday("Thomas Lövkvist", "30 september"),
            Birthday("Anna Andersson", "12 oktober"),
            Birthday("Lucas Svensson", "25 oktober"),
            Birthday("Maria Johansson", "3 november")
        )

        binding.recyclerBirthdays.layoutManager = LinearLayoutManager(this)
        binding.recyclerBirthdays.adapter = BirthdayAdapter(birthdays)
    }
}
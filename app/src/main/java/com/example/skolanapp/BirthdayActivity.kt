package com.example.skolanapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.skolanapp.databinding.ActivityBirthdayBinding
import androidx.recyclerview.widget.LinearLayoutManager

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
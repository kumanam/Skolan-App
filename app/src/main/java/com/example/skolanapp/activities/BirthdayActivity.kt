package com.example.skolanapp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skolanapp.R
import com.example.skolanapp.adapters.BirthdayAdapter
import com.example.skolanapp.api.RetrofitClient
import com.example.skolanapp.databinding.ActivityBirthdayBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BirthdayActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBirthdayBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBirthdayBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerBirthdays.layoutManager =
            LinearLayoutManager(this)

        loadBirthdays()
    }

    private fun loadBirthdays() {
        lifecycleScope.launch {
            try {
                val schoolData = withContext(Dispatchers.IO) {
                    RetrofitClient.schoolDataApi.getSchoolData()
                }

                val selectedClass = intent.getStringExtra("child_class")
                val classBirthdays = schoolData.birthdays.filter {
                    it.className == selectedClass
                }

                binding.recyclerBirthdays.adapter =
                    BirthdayAdapter(classBirthdays)

            } catch (e: Exception) {
                Toast.makeText(
                    this@BirthdayActivity,
                    getString(R.string.birthdays_load_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
        }
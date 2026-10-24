
package com.example.skolanapp.activities

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skolanapp.R
import com.example.skolanapp.adapters.BirthdayContactAdapter
import com.example.skolanapp.api.RetrofitClient
import com.example.skolanapp.databinding.ActivityBirthdayContactsBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class BirthdayContactsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBirthdayContactsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityBirthdayContactsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerBirthdayContacts.layoutManager =
            LinearLayoutManager(this)

        loadBirthdayContacts()
    }

    private fun loadBirthdayContacts() {
        lifecycleScope.launch {
            try {
                val schoolData = withContext(Dispatchers.IO) {
                    RetrofitClient.schoolDataApi.getSchoolData()
                }

                val selectedClass = intent.getStringExtra("child_class")

                val classBirthdays = schoolData.birthdays.filter {
                    it.className == selectedClass
                }

                val birthdayContacts = classBirthdays.mapNotNull { birthday ->
                    val childId = birthday.childId ?: return@mapNotNull null

                    val guardian = schoolData.contacts.firstOrNull { contact ->
                        contact.childId == childId &&
                                contact.role == "guardian"
                    }

                    guardian?.let { birthday to it }
                }

                binding.recyclerBirthdayContacts.adapter =
                    BirthdayContactAdapter(birthdayContacts)

                binding.recyclerBirthdayContacts.visibility =
                    if (birthdayContacts.isEmpty()) View.GONE else View.VISIBLE

                if (birthdayContacts.isEmpty()) {
                    Toast.makeText(
                        this@BirthdayContactsActivity,
                        "Inga vårdnadshavare hittades för klassen.",
                        Toast.LENGTH_LONG
                    ).show()
                }

            } catch (e: Exception) {
                Toast.makeText(
                    this@BirthdayContactsActivity,
                    getString(R.string.birthdays_load_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }
}

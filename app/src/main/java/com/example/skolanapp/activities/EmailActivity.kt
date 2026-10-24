package com.example.skolanapp.activities

import android.content.ActivityNotFoundException
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skolanapp.R
import com.example.skolanapp.adapters.ContactAdapter
import com.example.skolanapp.api.RetrofitClient
import com.example.skolanapp.databinding.ActivityEmailBinding
import com.example.skolanapp.models.Contact
import com.example.skolanapp.models.ContactType
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class EmailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEmailBinding

    private var allContacts: List<Contact> = emptyList()
    private var contactTypes: List<ContactType> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityEmailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recyclerContacts.layoutManager =
            LinearLayoutManager(this)

        loadContacts()
    }

    private fun loadContacts() {
        lifecycleScope.launch {
            try {
                val schoolData = withContext(Dispatchers.IO) {
                    RetrofitClient.schoolDataApi.getSchoolData()
                }

                allContacts = schoolData.contacts
                contactTypes = schoolData.contactTypes

                setupContactFilter()

            } catch (e: Exception) {
                Toast.makeText(
                    this@EmailActivity,
                    getString(R.string.contacts_load_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun setupContactFilter() {
        val categoryNames = listOf(
            getString(R.string.contact_all)
        ) + contactTypes.map { it.name }

        val spinnerAdapter = ArrayAdapter(
            this,
            android.R.layout.simple_spinner_item,
            categoryNames
        )

        spinnerAdapter.setDropDownViewResource(
            android.R.layout.simple_spinner_dropdown_item
        )

        binding.spinnerContactType.adapter = spinnerAdapter

        binding.spinnerContactType.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    filterContacts(position)
                }

                override fun onNothingSelected(
                    parent: AdapterView<*>?
                ) = Unit
            }

        filterContacts(0)
    }

    private fun filterContacts(position: Int) {
        val selectedType = contactTypes.getOrNull(position - 1)

        val filteredContacts = if (selectedType == null) {
            allContacts
        } else {
            allContacts.filter { contact ->
                contact.role == selectedType.id
            }
        }

        binding.recyclerContacts.adapter = ContactAdapter(
            contacts = filteredContacts,
            contactTypes = contactTypes
        ) { contact ->
            sendEmail(contact)
        }
    }

    private fun sendEmail(contact: Contact) {
        val intent = Intent(
            Intent.ACTION_SENDTO,
            Uri.parse("mailto:${Uri.encode(contact.email)}")
        ).apply {
            putExtra(
                Intent.EXTRA_SUBJECT,
                getString(R.string.contact_email_subject)
            )
        }

        try {
            startActivity(intent)
        } catch (e: ActivityNotFoundException) {
            Toast.makeText(
                this,
                getString(R.string.email_app_not_found),
                Toast.LENGTH_LONG
            ).show()
        }
    }
}

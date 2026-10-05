package com.example.skolanapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.skolanapp.databinding.ActivityIdentificationBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityIdentificationBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityIdentificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonBankId.setOnClickListener {
            val intent = Intent(this, UserSelectionActivity::class.java)
            startActivity(intent)
        }
    }
}
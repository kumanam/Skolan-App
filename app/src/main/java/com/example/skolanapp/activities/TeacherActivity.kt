package com.example.skolanapp.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.skolanapp.databinding.ActivityTeacherBinding

class TeacherActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTeacherBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTeacherBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}

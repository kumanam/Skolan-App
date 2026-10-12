package com.example.skolanapp.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.example.skolanapp.databinding.ActivityChildSelectionBinding
import com.example.skolanapp.api.RetrofitClient
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.widget.Toast

class ChildSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityChildSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityChildSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)


        CoroutineScope(Dispatchers.Main).launch {
            try {
                val children = withContext(Dispatchers.IO) {
                    RetrofitClient.childApi.getChildren()
                }

                binding.childrenContainer.removeAllViews()

                for (child in children) {
                    val button = Button(this@ChildSelectionActivity)

                    button.text = "${child.name}\n${child.className} – ${child.school}"

                    button.setOnClickListener {
                        val intent = Intent(
                            this@ChildSelectionActivity,
                            HomeActivity::class.java
                        )
                        intent.putExtra("child_id", child.id)
                        intent.putExtra("child_name", child.name)
                        startActivity(intent)
                    }

                    binding.childrenContainer.addView(button)
                }
            } catch (e: Exception) {
                Toast.makeText(
                    this@ChildSelectionActivity,
                    "Child's name cannot be found: ${e.message}",
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        }
        }
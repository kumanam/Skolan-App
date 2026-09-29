package com.example.skolanapp

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isGone
import com.example.skolanapp.databinding.ActivityUserSelectionBinding

class UserSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        binding.menuNews.setOnClickListener {

            binding.sectionNews.post {
                binding.mainScroll.smoothScrollTo(
                    0,
                    binding.sectionNews.top
                )
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.menuBirthdays.setOnClickListener {

            binding.sectionBirthdays.post {
                binding.mainScroll.smoothScrollTo(
                    0,
                    binding.sectionBirthdays.top
                )
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.menuHome.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.menuCalendar.setOnClickListener {
            binding.sectionCalendar.post {
                binding.mainScroll.smoothScrollTo(
                    0,
                    binding.sectionCalendar.top
                )
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.buttonReadNews.setOnClickListener {
            if (binding.fullNews.isGone) {
                binding.fullNews.visibility = View.VISIBLE
                binding.buttonReadNews.text = getString(R.string.visa_mindre)
            } else {
                binding.fullNews.visibility = View.GONE
                binding.buttonReadNews.text = getString(R.string.las_mer)
            }
        }


        binding.buttonReadBirthdays.setOnClickListener {
            if (binding.fullBirthdays.isGone) {
                binding.fullBirthdays.visibility = View.VISIBLE
                binding.buttonReadBirthdays.text = getString(R.string.visa_mindre)
            } else {
                binding.fullBirthdays.visibility = View.GONE
                binding.buttonReadBirthdays.text = getString(R.string.las_mer)
            }
        }
        binding.buttonReadCalendar.setOnClickListener {
            if (binding.fullCalendar.isGone) {
                binding.fullCalendar.visibility = View.VISIBLE
                binding.buttonReadCalendar.text = getString(R.string.visa_mindre)

                binding.sectionCalendar.post {
                    binding.mainScroll.smoothScrollTo(
                        0,
                        binding.sectionCalendar.top
                    )
                }

            } else {
                binding.fullCalendar.visibility = View.GONE
                binding.buttonReadCalendar.text = getString(R.string.las_mer)
            }
        }
    }
}
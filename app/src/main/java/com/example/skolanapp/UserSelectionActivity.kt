package com.example.skolanapp

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.core.view.isGone
import com.example.skolanapp.databinding.ActivityNewsBinding
import com.example.skolanapp.databinding.ActivityUserSelectionBinding
import com.example.skolanapp.databinding.DrawerMenuSideBinding
import androidx.recyclerview.widget.LinearLayoutManager

class UserSelectionActivity : AppCompatActivity() {

    private lateinit var binding: ActivityUserSelectionBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityUserSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val newsBinding = ActivityNewsBinding.bind(binding.newsLayout.root)
        val drawerBinding = DrawerMenuSideBinding.bind(binding.drawerMenu.root)

        val news = listOf(
            News(
                "Föräldramöte",
                "Nästa föräldramöte är den 15 oktober.",
                "Den 15 oktober kommer vi att ha ett föräldramöte på skolan. Mer information kommer snart."
            ),
            News(
                "Utflykt",
                "Eleverna har en utflykt den 20 oktober.",
                "Den 20 oktober kommer eleverna att åka på utflykt. Mer information om tid och plats kommer senare."
            ),
            News(
                "Viktig information",
                "Skolan kommer att vara stängd den 1 november.",
                "Skolan kommer att vara stängd den 1 november. Elever och föräldrar behöver planera denna dag."
            )
        )
        newsBinding.recyclerNews.layoutManager = LinearLayoutManager(this)
        newsBinding.recyclerNews.adapter = NewsAdapter(news)

        newsBinding.recyclerNews.adapter = NewsAdapter(news)


        binding.buttonMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }


        drawerBinding.menuNews.setOnClickListener {
            newsBinding.sectionNews.post {
                binding.mainScroll.smoothScrollTo(
                    0,
                    newsBinding.sectionNews.top)
            }

            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }


        drawerBinding.menuBirthdays.setOnClickListener {
            val intent = Intent(this, BirthdayActivity::class.java)
            startActivity(intent)

            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.buttonBirthdays.setOnClickListener {
            val intent = Intent(this, BirthdayActivity::class.java)
            startActivity(intent)
        }

        binding.buttonViewBirthdays.setOnClickListener {
            val intent = Intent(this, BirthdayActivity::class.java)
            startActivity(intent)
        }

        drawerBinding.menuHome.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        drawerBinding.menuCalendar.setOnClickListener {
            binding.sectionCalendar.post {
                binding.mainScroll.smoothScrollTo(
                    0,
                    binding.sectionCalendar.top)
            }
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.buttonReadCalendar.setOnClickListener {
            if (binding.fullCalendar.isGone) {
                binding.fullCalendar.visibility = View.VISIBLE
                binding.buttonReadCalendar.text =
                    getString(R.string.visa_mindre)

                binding.sectionCalendar.post {
                    binding.mainScroll.smoothScrollTo(
                        0,
                        binding.sectionCalendar.top)
                }
            } else {
                binding.fullCalendar.visibility = View.GONE
                binding.buttonReadCalendar.text =
                    getString(R.string.las_mer)
            }
        }
    }
}
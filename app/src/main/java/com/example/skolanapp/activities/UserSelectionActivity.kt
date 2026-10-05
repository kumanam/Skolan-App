package com.example.skolanapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.skolanapp.databinding.ActivityNewsBinding
import com.example.skolanapp.databinding.ActivityUserSelectionBinding
import com.example.skolanapp.databinding.DrawerMenuSideBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skolanapp.models.News
import com.example.skolanapp.adapters.NewsAdapter
import com.example.skolanapp.models.CalendarEvent

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

        val calendarEvents = listOf(
            CalendarEvent("Måndag", "08:00", "Svenska"),
            CalendarEvent("Måndag", "10:00", "Matematik"),

            CalendarEvent("Tisdag", "09:00", "Matematik"),
            CalendarEvent("Tisdag", "11:00", "Svenska"),

            CalendarEvent("Onsdag", "08:00", "Svenska"),
            CalendarEvent("Onsdag", "15:30", "Möte med läraren"),

            CalendarEvent("Torsdag", "09:00", "Engelska"),
            CalendarEvent("Torsdag", "13:00", "Matematik"),

            CalendarEvent("Fredag", "10:00", "Idrott"),
            CalendarEvent("Fredag", "12:00", "Musik")
        )

        binding.textNextActivity.text =
            "Nästa aktivitet: ${calendarEvents[0].title} – ${calendarEvents[0].day} ${calendarEvents[0].time}"
        
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
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)

            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
        binding.buttonCalendar.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }

        binding.buttonReadCalendar.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }
        }
    }

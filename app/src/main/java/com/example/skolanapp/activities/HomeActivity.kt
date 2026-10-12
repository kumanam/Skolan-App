package com.example.skolanapp.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import com.example.skolanapp.databinding.ActivityNewsBinding
import com.example.skolanapp.databinding.ActivityHomeBinding
import com.example.skolanapp.databinding.DrawerMenuSideBinding
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.skolanapp.adapters.NewsAdapter
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import android.widget.Toast
import com.example.skolanapp.R
import com.example.skolanapp.api.RetrofitClient

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val newsBinding = ActivityNewsBinding.bind(binding.newsLayout.root)
        val drawerBinding = DrawerMenuSideBinding.bind(binding.drawerMenu.root)
        val childId = intent.getIntExtra("child_id", -1)

        val childName = intent.getStringExtra("child_name")
        binding.textSelectedChild.text = childName?.let {
            "Barn: $it"
        } ?: ""


        CoroutineScope(Dispatchers.Main).launch {
            try {
                val news = withContext(Dispatchers.IO) {
                    RetrofitClient.newsApi.getNews()
                }

                newsBinding.recyclerNews.adapter = NewsAdapter(news)

            } catch (e: Exception) {
                    Toast.makeText(
                        this@HomeActivity,
                        getString(R.string.news_load_error, e.message ?: ""),
                        Toast.LENGTH_LONG
                    ).show()
            }
        }

        CoroutineScope(Dispatchers.Main).launch {
            try {
                val schoolData = withContext(Dispatchers.IO) {
                    RetrofitClient.schoolDataApi.getSchoolData()
                }

                val nextActivity = schoolData.calendar.firstOrNull()

                binding.textNextActivity.text = if (nextActivity != null) {
                    getString(
                        R.string.next_activity,
                        nextActivity.title,
                        nextActivity.day,
                        nextActivity.time
                    )
                } else {
                    getString(R.string.no_planned_activity)
                }

            } catch (e: Exception) {
                Toast.makeText(
                    this@HomeActivity,
                    getString(
                        R.string.next_activity_load_error,
                        e.message ?: ""
                    ),
                    Toast.LENGTH_LONG
                ).show()
            }
        }

        newsBinding.recyclerNews.layoutManager = LinearLayoutManager(this)


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

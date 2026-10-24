package com.example.skolanapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.GravityCompat
import androidx.lifecycle.lifecycleScope
import com.example.skolanapp.R
import com.example.skolanapp.api.RetrofitClient
import com.example.skolanapp.databinding.ActivityHomeBinding
import com.example.skolanapp.databinding.DrawerMenuSideBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private var childClass: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val drawerBinding =
            DrawerMenuSideBinding.bind(binding.drawerMenu.root)

        val childId = intent.getIntExtra("child_id", -1)
        val childName = intent.getStringExtra("child_name")
        childClass = intent.getStringExtra("child_class") ?: ""


        binding.textSelectedChild.text =
            childName?.let { "Barn: $it" } ?: ""

        binding.newsLayout.root.visibility = View.GONE
        drawerBinding.menuNews.visibility = View.GONE

        loadSchoolData(childId)

        binding.buttonMenu.setOnClickListener {
            binding.drawerLayout.openDrawer(GravityCompat.START)
        }

        drawerBinding.menuHome.setOnClickListener {
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.buttonEmail.setOnClickListener {
            startActivity(Intent(this, EmailActivity::class.java))
        }

        binding.buttonBirthdays.setOnClickListener {
            val intent = Intent(this, BirthdayContactsActivity::class.java)
            intent.putExtra("child_id", childId)
            intent.putExtra("child_name", childName)
            intent.putExtra("child_class", childClass)
            startActivity(intent)
        }

        binding.buttonViewBirthdays.setOnClickListener {
            startActivity(Intent(this, BirthdayActivity::class.java))
        }

        drawerBinding.menuBirthdays.setOnClickListener {
            val intent = Intent(this, BirthdayActivity::class.java)
            intent.putExtra("child_id", childId)
            intent.putExtra("child_name", childName)
            intent.putExtra("child_class", childClass)
            startActivity(intent)

            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }

        binding.buttonCalendar.setOnClickListener {
            startActivity(Intent(this, MonthActivity::class.java))
        }

        drawerBinding.menuCalendar.setOnClickListener {
            startActivity(Intent(this, CalendarActivity::class.java))
            binding.drawerLayout.closeDrawer(GravityCompat.START)
        }
    }

    private fun loadSchoolData(childId: Int) {
        lifecycleScope.launch {
            try {
                val schoolData = withContext(Dispatchers.IO) {
                    RetrofitClient.schoolDataApi.getSchoolData()
                }

                val today = SimpleDateFormat(
                    "yyyy-MM-dd",
                    Locale.ROOT
                ).format(Calendar.getInstance().time)

                val nextActivity = schoolData.calendar
                    .filter { it.date >= today }
                    .sortedWith(
                        compareBy(
                            { it.date },
                            { it.time }
                        )
                    )
                    .firstOrNull()

                binding.textNextActivity.text =
                    if (nextActivity != null) {
                        getString(
                            R.string.next_activity,
                            nextActivity.title,
                            nextActivity.day,
                            nextActivity.time
                        )
                    } else {
                        getString(R.string.no_planned_activity)
                    }

                val nextBirthday = schoolData.birthdays
                    .mapNotNull { birthday ->
                        val nextDate = getNextBirthdayDate(birthday.date)

                        if (nextDate != null) {
                            Triple(
                                birthday,
                                nextDate,
                                nextDate.timeInMillis
                            )
                        } else {
                            null
                        }
                    }
                    .minByOrNull { it.third }

                if (nextBirthday != null) {
                    val birthday = nextBirthday.first
                    val date = nextBirthday.second

                    val formattedDate = SimpleDateFormat(
                        "d MMMM",
                        Locale("sv", "SE")
                    ).format(date.time)

                    binding.textNextBirthday.text = getString(
                        R.string.next_birthday,
                        birthday.name,
                        formattedDate
                    )
                } else {
                    binding.textNextBirthday.text =
                        getString(R.string.no_upcoming_birthday)
                }

            } catch (e: Exception) {
                Toast.makeText(
                    this@HomeActivity,
                    getString(R.string.schooldata_load_error),
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    private fun getNextBirthdayDate(
        date: String
    ): Calendar? {
        return try {
            val formatter = SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.ROOT
            ).apply {
                isLenient = false
            }

            val birthDate = formatter.parse(date)
                ?: return null

            val birthCalendar = Calendar.getInstance().apply {
                time = birthDate
            }

            val today = Calendar.getInstance()

            val nextBirthday = Calendar.getInstance().apply {
                set(
                    today.get(Calendar.YEAR),
                    birthCalendar.get(Calendar.MONTH),
                    birthCalendar.get(Calendar.DAY_OF_MONTH),
                    0, 0, 0
                )
                set(Calendar.MILLISECOND, 0)
            }

            val todayStart = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            if (nextBirthday.before(todayStart)) {
                nextBirthday.add(Calendar.YEAR, 1)
            }

            nextBirthday

        } catch (e: Exception) {
            null
        }
    }
}

package com.example.skolanapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skolanapp.databinding.ItemBirthdayBinding
import com.example.skolanapp.models.Birthday
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

class BirthdayAdapter(
    birthdays: List<Birthday>
) : RecyclerView.Adapter<BirthdayAdapter.BirthdayViewHolder>() {

    private val sortedBirthdays = birthdays.sortedBy {
        getNextBirthday(it.date)?.timeInMillis ?: Long.MAX_VALUE
    }

    class BirthdayViewHolder(
        val binding: ItemBirthdayBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BirthdayViewHolder {
        val binding = ItemBirthdayBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return BirthdayViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: BirthdayViewHolder,
        position: Int
    ) {
        val birthday = sortedBirthdays[position]

        holder.binding.textBirthdayName.text = birthday.name

        val nextBirthday = getNextBirthday(birthday.date)

        holder.binding.textBirthdayDate.text =
            nextBirthday?.let {
                SimpleDateFormat("d MMMM", Locale("sv", "SE"))
                    .format(it.time)
            } ?: birthday.date
    }

    override fun getItemCount(): Int = sortedBirthdays.size



    private fun getNextBirthday(date: String): Calendar? {
        return try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.ROOT).apply {
                isLenient = false
            }

            val parsedDate = inputFormat.parse(date) ?: return null

            val original = Calendar.getInstance().apply {
                time = parsedDate
            }

            val today = Calendar.getInstance()

            val next = Calendar.getInstance().apply {
                set(Calendar.YEAR, today.get(Calendar.YEAR))
                set(Calendar.MONTH, original.get(Calendar.MONTH))
                set(Calendar.DAY_OF_MONTH, original.get(Calendar.DAY_OF_MONTH))
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            val todayStart = Calendar.getInstance().apply {
                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }

            if (next.before(todayStart)) {
                next.add(Calendar.YEAR, 1)
            }

            next
        } catch (e: Exception) {
            null
        }
    }

}



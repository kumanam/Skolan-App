package com.example.skolanapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skolanapp.databinding.ItemCalenderBinding
import com.example.skolanapp.models.CalendarEvent

class CalendarAdapter(
    private val events: List<CalendarEvent>
) : RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {

    class CalendarViewHolder(
        val binding: ItemCalenderBinding
    ) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CalendarViewHolder {

        val binding = ItemCalenderBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return CalendarViewHolder(binding)
    }

    override fun onBindViewHolder(
        holder: CalendarViewHolder,
        position: Int
    ) {
        val event = events[position]

        holder.binding.textCalendarTime.text = event.time
        holder.binding.textCalendarTitle.text = event.title
    }

    override fun getItemCount(): Int {
        return events.size
    }
}
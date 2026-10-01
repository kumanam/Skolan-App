package com.example.skolanapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.skolanapp.databinding.ItemBirthdayBinding

class BirthdayAdapter(
    private val birthdays: List<Birthday>
) : RecyclerView.Adapter<BirthdayAdapter.BirthdayViewHolder>() {

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
        val birthday = birthdays[position]

        holder.binding.textBirthdayName.text = birthday.name
        holder.binding.textBirthdayDate.text = birthday.date
    }

    override fun getItemCount(): Int {
        return birthdays.size
    }
}
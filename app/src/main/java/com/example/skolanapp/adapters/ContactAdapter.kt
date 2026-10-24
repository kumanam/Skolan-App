package com.example.skolanapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.skolanapp.R
import com.example.skolanapp.models.Contact
import com.example.skolanapp.models.ContactType

class ContactAdapter(
    private val contacts: List<Contact>,
    private val contactTypes: List<ContactType>,
    private val onEmailClick: (Contact) -> Unit
) : RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {

    class ContactViewHolder(view: View) :
        RecyclerView.ViewHolder(view) {

        val name: TextView =
            view.findViewById(R.id.textContactName)

        val role: TextView =
            view.findViewById(R.id.textContactRole)

        val email: TextView =
            view.findViewById(R.id.textContactEmail)

        val button: Button =
            view.findViewById(R.id.buttonContactEmail)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ContactViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_contact, parent, false)

        return ContactViewHolder(view)
    }

    override fun onBindViewHolder(
        holder: ContactViewHolder,
        position: Int
    ) {
        val contact = contacts[position]

        holder.name.text = contact.name

        holder.role.text = contactTypes
            .firstOrNull { it.id == contact.role }
            ?.name
            ?: contact.role

        holder.email.text = contact.email

        holder.button.setOnClickListener {
            onEmailClick(contact)
        }
    }

    override fun getItemCount(): Int = contacts.size
}

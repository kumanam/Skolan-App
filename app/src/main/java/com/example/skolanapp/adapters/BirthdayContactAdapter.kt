
package com.example.skolanapp.adapters

import android.content.Intent
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.skolanapp.R
import com.example.skolanapp.models.Birthday
import com.example.skolanapp.models.Contact

class BirthdayContactAdapter(
    private val items: List<Pair<Birthday, Contact>>
) : RecyclerView.Adapter<BirthdayContactAdapter.ViewHolder>() {

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val childName: TextView =
            view.findViewById(R.id.textBirthdayChildName)

        val guardianName: TextView =
            view.findViewById(R.id.textBirthdayGuardianName)

        val guardianEmail: TextView =
            view.findViewById(R.id.textBirthdayGuardianEmail)

        val emailButton: ImageButton =
            view.findViewById(R.id.buttonBirthdayContactEmail)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_birthday_contacts, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val (birthday, contact) = items[position]

        holder.childName.text = birthday.name
        holder.guardianName.text = contact.name
        holder.guardianEmail.text = contact.email

        holder.emailButton.setOnClickListener {
            val context = holder.itemView.context
            val emailIntent = Intent(
                Intent.ACTION_SENDTO,
                Uri.parse("mailto:${Uri.encode(contact.email)}")
            )

            if (emailIntent.resolveActivity(context.packageManager) != null) {
                context.startActivity(emailIntent)
            } else {
                Toast.makeText(
                    context,
                    R.string.no_email_app,
                    Toast.LENGTH_LONG
                ).show()
            }
        }
    }

    override fun getItemCount(): Int = items.size
}

package com.example.tareafinal081224.ui.adapters

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import androidx.recyclerview.widget.RecyclerView
import com.example.tareafinal081224.R
import com.example.tareafinal081224.databinding.ChatLayoutBinding
import com.example.tareafinal081224.domain.models.GroupChatModel
import java.text.SimpleDateFormat
import java.util.Date

class GroupChatAdapter(
    var messagesList: MutableList<GroupChatModel>,
    private val emailUserLoged: String
) : RecyclerView.Adapter<GroupChatAdapter.GroupChatViewHolder>() {
    class GroupChatViewHolder(v: View) : RecyclerView.ViewHolder(v) {
        private var binding = ChatLayoutBinding.bind(v)
        fun render(item: GroupChatModel, emailUserLoged: String) {
            val params = binding.cvChat.layoutParams as FrameLayout.LayoutParams

            if (emailUserLoged == item.email) {
                // Obtener el contexto de la vista con cualquier elemento de la vista
                binding.clChat.setBackgroundColor(binding.tvDate.context.getColor(R.color.login))
                params.gravity = Gravity.END
            } else {
                binding.clChat.setBackgroundColor(binding.tvDate.context.getColor(R.color.user))
                params.gravity = Gravity.START
            }

            binding.tvMessage.text = item.message
            binding.tvEmail.text = item.email
            binding.tvDate.text = formatDate(item.date)

        }

        private fun formatDate(date: Long): String {
            val date = Date(date)
            val format = SimpleDateFormat("dd/MM/yyyy HH:mm")
            return format.format(date)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GroupChatViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.chat_layout, parent, false)
        return GroupChatViewHolder(v)
    }

    override fun getItemCount() = messagesList.size

    override fun onBindViewHolder(holder: GroupChatViewHolder, position: Int) {
        holder.render(messagesList[position], emailUserLoged)
    }
}

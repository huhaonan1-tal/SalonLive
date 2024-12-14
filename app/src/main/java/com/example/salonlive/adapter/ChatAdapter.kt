package com.example.salonlive.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.TextUtils
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.salonlive.R
import com.example.salonlive.UserBLL
import com.example.salonlive.data.ChatMessage

class ChatAdapter : RecyclerView.Adapter<ChatAdapter.ChatViewHolder>() {

    private val messages = mutableListOf<ChatMessage>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ChatViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_chat_message, parent, false)
        return ChatViewHolder(view)
    }

    override fun onBindViewHolder(holder: ChatViewHolder, position: Int) {
        holder.bind(messages[position],position)
    }

    override fun getItemCount(): Int {
        return messages.size
    }

    fun addMessage(message: ChatMessage) {
        messages.add(message)
        notifyItemInserted(messages.size - 1)
    }

    class ChatViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val textViewMessage: TextView = itemView.findViewById(R.id.text_view_message)
        private val textViewUserName: TextView = itemView.findViewById(R.id.text_view_user_name)

        fun bind(message: ChatMessage, position: Int) {
            val chatMessage = message.message
            if(position == 0){
                val spannableString = SpannableString(message.message)
                spannableString.setSpan(
                    ForegroundColorSpan(Color.parseColor("#ff9933")),
                    0,
                    chatMessage.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                spannableString.setSpan(
                    StyleSpan(Typeface.BOLD), // 设置加粗样式
                    0,
                    chatMessage.length,
                    Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                )
                textViewMessage.text = spannableString
                val parentLayout = textViewMessage.parent as View
                //居中显示
                if (parentLayout is LinearLayout) {
                    parentLayout.gravity = Gravity.CENTER
                }
            }else{
                textViewMessage.text = message.message
                val spannableStringBuilder = SpannableStringBuilder(message.name)
                if(!TextUtils.isEmpty(message.name) && UserBLL.userName != message.name) {
                    spannableStringBuilder.append(": ")
                    spannableStringBuilder.setSpan(
                        ForegroundColorSpan(Color.parseColor("#8B4513")),
                        0,
                        message.name.length+1,
                        Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
                    )
                }


                textViewUserName.text = spannableStringBuilder
            }
        }
    }
}

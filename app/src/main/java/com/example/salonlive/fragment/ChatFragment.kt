package com.example.salonlive.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.salonlive.ChatAdapter
import com.example.salonlive.databinding.FragmentChatBinding

class ChatFragment : Fragment() {

    private var _binding: FragmentChatBinding? = null
    private val binding get() = _binding!!
    private lateinit var chatAdapter: ChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentChatBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chatAdapter = ChatAdapter()
        binding.recyclerViewChat.layoutManager = LinearLayoutManager(context)
        binding.recyclerViewChat.adapter = chatAdapter

        // Handle send button click
        binding.buttonSend.setOnClickListener {
            val message = binding.editTextMessage.text.toString()
            if (message.isNotEmpty()) {
                chatAdapter.addMessage(message)
                binding.editTextMessage.text.clear()
                binding.recyclerViewChat.scrollToPosition(chatAdapter.itemCount - 1)
            }
        }
    }

    fun addMessage(message: String) {
        chatAdapter.addMessage(message)
        binding.recyclerViewChat.scrollToPosition(chatAdapter.itemCount - 1)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
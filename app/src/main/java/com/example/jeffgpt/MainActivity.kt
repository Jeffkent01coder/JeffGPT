package com.example.jeffgpt

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.jeffgpt.databinding.ActivityMainBinding
import com.example.jeffgpt.model.Message
import com.example.jeffgpt.viewModel.ChatGptViewModel

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var chatGptViewModel: ChatGptViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        binding = ActivityMainBinding.inflate(layoutInflater)
        supportActionBar?.hide()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        chatGptViewModel = ViewModelProvider(this)[ChatGptViewModel::class.java]

        val llm = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = llm

        chatGptViewModel.messageList.observe(this) { messages ->
            val adapter = MessageAdapter(messages)
            binding.recyclerView.adapter = adapter
        }

        binding.sendBtn.setOnClickListener {
            val question = binding.messageEditText.text.toString()
            chatGptViewModel.addToChat(
                question,
                Message.SENT_BY_ME,
                chatGptViewModel.getCurrentTimestamp()
            )
            binding.messageEditText.setText("")
            chatGptViewModel.callApi(question)
        }


    }


}



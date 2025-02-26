package com.example.tareafinal081224.ui

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.tareafinal081224.BaseActivity
import com.example.tareafinal081224.R
import com.example.tareafinal081224.databinding.ActivityGroupChatBinding
import com.example.tareafinal081224.domain.models.GroupChatModel
import com.example.tareafinal081224.ui.adapters.GroupChatAdapter
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class GroupChatActivity : BaseActivity() {
    private lateinit var binding: ActivityGroupChatBinding

    // Email del usuario logeado
    var emailUserLogin = ""
    private lateinit var auth: FirebaseAuth

    private lateinit var database: DatabaseReference
    private var chatsList = mutableListOf<GroupChatModel>()
    private lateinit var adapter: GroupChatAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityGroupChatBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        auth = Firebase.auth
        emailUserLogin = auth.currentUser?.email.toString()
        database = FirebaseDatabase.getInstance().getReference("chat")
        setRecycler()
        setListeners()
    }

    private fun setRecycler() {
        val layoutManager = LinearLayoutManager(this)
        binding.rvChats.layoutManager = layoutManager
        adapter = GroupChatAdapter(chatsList, emailUserLogin)
        binding.rvChats.adapter = adapter
    }

    private fun setListeners() {
        binding.iv.setOnClickListener {
            send()
        }

        // Listener a la DB para obtener los mensajes
        val dbListener = object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                chatsList.clear()
                // Recorrer los nodos de la DB buscando los mensajes
                for (nodo in snapshot.children) {
                    val chatNodo = nodo.getValue(GroupChatModel::class.java)
                    if (chatNodo != null) {
                        chatsList.add(chatNodo)
                    }
                }
                chatsList.sortBy { it.date }
                adapter.messagesList = chatsList
                adapter.notifyDataSetChanged()
            }

            override fun onCancelled(error: DatabaseError) {
                Toast.makeText(
                    this@GroupChatActivity,
                    "ERROR: can't read database",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

        // Agregar el listener a la DB para obtener los mensajes
        database.addValueEventListener(dbListener)

        binding.nv.setNavigationItemSelectedListener {
            checkMenuItem(it)
        }

    }

    private fun send() {
        val text = binding.etSendMessage.text.toString().trim()
        if (text.isEmpty()) return
        val date = System.currentTimeMillis()
        val message = GroupChatModel(emailUserLogin, text, date)
        // Se usa la fecha como clave
        val key = date.toString()
        database.child(key).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                database.child(key).setValue(message)
                    .addOnSuccessListener {
                        binding.etSendMessage.setText("")
                    }
                    .addOnFailureListener {
                        Toast.makeText(
                            this@GroupChatActivity,
                            "ERROR: can't send message",
                            Toast.LENGTH_SHORT
                        ).show(
                        )
                    }
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}

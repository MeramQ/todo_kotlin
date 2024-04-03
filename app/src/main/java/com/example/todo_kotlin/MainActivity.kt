package com.example.todo_kotlin

import ItemAdapter
import SwipeToDeleteCallback
import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter: ItemAdapter
    private var itemList: ArrayList<String> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val input: EditText = findViewById(R.id.editText)
        val addButton: Button = findViewById(R.id.addButton)
        val clearButton: Button = findViewById(R.id.removeButton)
        val recyclerView: RecyclerView = findViewById(R.id.listView)

        sharedPreferences = getSharedPreferences("MyPreferences", Context.MODE_PRIVATE)
        loadList()

        adapter = ItemAdapter(itemList, this)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = adapter

        val itemTouchHelper = ItemTouchHelper(SwipeToDeleteCallback(adapter))
        itemTouchHelper.attachToRecyclerView(recyclerView)

        addButton.setOnClickListener {
            val newItem = input.text.toString()
            if (newItem.isNotEmpty()) {
                itemList.add(newItem)
                saveList()
                adapter.notifyDataSetChanged()
                input.text.clear()
            }
        }

        clearButton.setOnClickListener {
            itemList.clear()
            saveList()
            adapter.notifyDataSetChanged()
        }
    }

    private fun saveList() {
        val editor = sharedPreferences.edit()
        val set = HashSet<String>()
        set.addAll(itemList)
        editor.putStringSet("list", set)
        editor.apply()
    }

    private fun loadList() {
        val set = sharedPreferences.getStringSet("list", HashSet())
        itemList.addAll(set ?: emptySet())
    }
}
